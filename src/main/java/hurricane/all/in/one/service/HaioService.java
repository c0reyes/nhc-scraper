package hurricane.all.in.one.service;

import java.util.List;

import hurricane.all.in.one.entity.ncar.Model;
import hurricane.all.in.one.entity.noaa.Hurricane;
import hurricane.all.in.one.entity.noaa.Meso;

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
