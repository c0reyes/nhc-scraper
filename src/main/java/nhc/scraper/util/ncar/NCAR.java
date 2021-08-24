package nhc.scraper.util.ncar;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import nhc.scraper.entity.ncar.Model;

public class NCAR {
	private String atcf;
	private String year;
	private String zone;
	private String name;
	
	private Document doc;
	
	public NCAR() {
		
	}
	
	public NCAR(String atcf, String name) {
		this.atcf = atcf.toLowerCase();
		this.year = this.atcf.substring(this.atcf.length()-4, this.atcf.length());
		this.zone = this.atcf.substring(0,2);
		this.name = name;
	}
	
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Model getNcar() throws IOException {
		Model model = new Model();
		model.setName(name);
		
		String url = String.format("%s%s/%s", Zone.getZone(zone).getUrl(), year, atcf);
		
		doc = Jsoup.connect(url).get();
		
		Elements links = doc.select("a");
		for(Element link: links) {
			if(link.text().contains("Early cycle track guidance")) 
				model.setImgTrack(link.absUrl("href"));
			if(link.text().contains("Early cycle intensity guidance")) 
				model.setImgIntensity(link.absUrl("href"));
		}
				
		return model;
	}
}
