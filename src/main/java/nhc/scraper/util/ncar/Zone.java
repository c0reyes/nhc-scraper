package nhc.scraper.util.ncar;

import java.util.HashMap;
import java.util.Map;

public enum Zone {
	AL("al","http://hurricanes.ral.ucar.edu/realtime/plots/northatlantic/"),
	EP("ep","http://hurricanes.ral.ucar.edu/realtime/plots/northeastpacific/"),
	CP("cp","http://hurricanes.ral.ucar.edu/realtime/plots/centralpacific/");
	
	private String name;
	private String url;

	Zone(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	static Map<String, Zone> map = new HashMap<>();

    static {
        for (Zone catalog : Zone.values()) {
            map.put(catalog.name, catalog);
        }
    }
    
    public static Zone getZone(String name) {
    	return map.get(name);
    }
}
