package nhc.scraper.util.noaa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Zone {
	AT("at", "https://www.nhc.noaa.gov/index-at.xml","https://www.nhc.noaa.gov/xgtwo/two_atl_0d0.png","https://www.nhc.noaa.gov/xgtwo/two_atl_2d0.png","https://www.nhc.noaa.gov/?atlc"),
	EP("ep", "https://www.nhc.noaa.gov/index-ep.xml","https://www.nhc.noaa.gov/xgtwo/two_pac_0d0.png","https://www.nhc.noaa.gov/xgtwo/two_pac_2d0.png","https://www.nhc.noaa.gov/?epac"),
	CP("cp","https://www.nhc.noaa.gov/index-cp.xml","https://www.nhc.noaa.gov/xgtwo/two_cpac_0d0.png","https://www.nhc.noaa.gov/xgtwo/two_cpac_2d0.png","https://www.nhc.noaa.gov/?cpac");
	
	private String name;
	private String url;
	private String img;
	private String imgSat;
	private String page;

	Zone(String name, String url, String img, String imgSat, String page) {
		this.name = name;
		this.url = url;
		this.img = img;
		this.imgSat = imgSat;
		this.page = page;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getImg() {
		return this.img;
	}
	
	public String getImgSat() {
		return this.imgSat;
	}
	
	public String getPage() {
		return this.page;
	}
	
	static Map<String, Zone> map = new HashMap<>();

    static {
        for (Zone catalog : Zone.values()) {
            map.put(catalog.name, catalog);
        }
    }
    
    public static Optional<Zone> getZone(String name) {
    	return Optional.ofNullable(map.get(name));
    }
}
