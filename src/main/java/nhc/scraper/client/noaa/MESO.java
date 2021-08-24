package nhc.scraper.client.noaa;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nhc.scraper.entity.noaa.Meso;

public class MESO {
	private static String URL = "https://www.star.nesdis.noaa.gov/GOES";
	private Document doc;
	
	public MESO() throws IOException, KeyManagementException, NoSuchAlgorithmException {
		enableSSLSocket();
		this.doc = Jsoup.connect(String.format("%s/MESO_index.php", URL)).get();
	}
	
	public Meso getMeso(double lat, double lon, String name) throws URISyntaxException, IOException {
		Map<String, String> imgUrl = new HashMap<String, String>();
		Meso meso = new Meso();
		meso.setName(name);
		meso.setImgUrl(imgUrl);
		Elements elements = doc.getElementsByAttributeValue("class", "mesoItems");
		for(Element element: elements) {
			Elements links = element.select("a");
			for(Element link: links) {
				Map<String, String> queryString = splitQuery(new URI(link.attr("href")));
				if(compareLatLon(lat, lon, 
						Double.parseDouble(queryString.get("lat").substring(0, queryString.get("lat").length()-1)), 
						Double.parseDouble(queryString.get("lon").substring(0, queryString.get("lon").length()-1)))) {
					Document doc = Jsoup.connect(String.format("%s/%s", URL, link.attr("href"))).get();
					Elements tnboxes = doc.getElementsByAttributeValueMatching("title", "Band 13 - .*"); 
					for(Element tnbox: tnboxes) {
						imgUrl.put(tnbox.text(), tnbox.absUrl("href"));
					}
				}
			}
		}
		return meso;
	}
	
	private static boolean compareLatLon(double lat1, double lon1, double lat2, double lon2) {
		int range = 5;
		return (lat2 - range <= Math.abs(lat1) && Math.abs(lat1) <= lat2 + range) &&
			   (lon2 - range <= Math.abs(lon1) && Math.abs(lon1) <= lon2 + range);
	}
	
	private static void enableSSLSocket() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, new X509TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
 
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
    }
	
	private static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
	    Map<String, String> query_pairs = new HashMap<String, String>();
	    String query = uri.getQuery();
	    String[] pairs = query.split("&");
	    for (String pair : pairs) {
	        int idx = pair.indexOf("=");
	        query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
	    }
	    return query_pairs;
	}
}
