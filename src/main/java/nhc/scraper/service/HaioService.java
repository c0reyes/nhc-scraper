package nhc.scraper.service;

import java.util.List;

import nhc.scraper.entity.ncar.Model;
import nhc.scraper.entity.noaa.Hurricane;
import nhc.scraper.entity.noaa.Meso;

public interface HaioService {
	public void clean();
	
	public void setZone(String zone);
	
	public void setNoaa();
	
	public void setModel();
	
	public void setMeso();
	
	public String getImg();
	
	public List<Hurricane> getNoaa();
	
	public List<Model> getModel();
	
	public List<Meso> getMeso();
}
