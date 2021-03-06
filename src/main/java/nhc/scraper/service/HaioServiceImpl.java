package nhc.scraper.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import nhc.scraper.client.ncar.Image;
import nhc.scraper.client.ncar.NCAR;
import nhc.scraper.client.noaa.MESO;
import nhc.scraper.client.noaa.NHC;
import nhc.scraper.client.noaa.Zone;
import nhc.scraper.entity.ncar.Model;
import nhc.scraper.entity.noaa.Hurricane;
import nhc.scraper.entity.noaa.Meso;
import nhc.scraper.repository.ncar.ModelRepository;
import nhc.scraper.repository.noaa.HurricaneRepository;
import nhc.scraper.repository.noaa.MesoRepository;

@Slf4j
@Service
public class HaioServiceImpl implements HaioService {
	@Autowired
	ModelRepository modelRepository;
	
	@Autowired
	HurricaneRepository hurricaneRepository;
	
	@Autowired
	MesoRepository mesoRepository;
	
	private List<Hurricane> listNoaa;
	private Zone zone;

	public void clean() {
		hurricaneRepository.deleteAll();
		modelRepository.deleteAll();
		mesoRepository.deleteAll();
		listNoaa = null;
	}
	
	public void setZone(String zone) {
		this.zone = Zone.getZone(zone).orElse(Zone.AT);
	}
	
	public void setNoaa() {
		NHC nhc = null;
		try {
			nhc = new NHC(zone);
			listNoaa = nhc.getRss();
			listNoaa.forEach(e -> {
				e.setZone(this.zone.getName());
				hurricaneRepository.save(e);
			});
		} catch (MalformedURLException e) {
			log.error(e.toString(), e);
		}
	}
	
	public void setModel() {
		listNoaa.forEach(e -> {
			NCAR ncar = new NCAR(e.getAtcf(), e.getName());
			try {
				Model n = ncar.getNcar();
				n.setZone(this.zone.getName());
				modelRepository.save(n); 
			} catch (IOException e1) {
				log.error(e1.toString(), e1);
			}
		});
	}
	
	public void setMeso() {
		try {
			final MESO meso = new MESO();
			listNoaa.forEach(e -> { 
				try {
					Meso m = meso.getMeso(e.getLat(), e.getLon(), e.getName());
					m.setZone(this.zone.getName());
					mesoRepository.save(m); 
				} catch (IOException | URISyntaxException  e1) {
					log.error(e1.toString(), e1);
				}
			});
		} catch (KeyManagementException | NoSuchAlgorithmException | IOException e) {
			log.error(e.toString(), e);
		}
	}
	
	public String getImg() {
		return this.zone.getImg();
	}
	
	public String getImgSat() {
		return this.zone.getImgSat();
	}
	
	public String getPage() {
		return this.zone.getPage();
	}
	
	public List<Hurricane> getNoaa() {
		return hurricaneRepository.findAllByZone(this.zone.getName());
	}
	
	public List<Model> getModel() {
		return modelRepository.findAllByZone(this.zone.getName());
	}
	
	public List<Meso> getMeso() {
		return mesoRepository.findAllByZone(this.zone.getName());
	}
	
	public byte[] getImage(String imgUrl) throws IOException {
		Image image = new Image(imgUrl);
		return image.getImage();
	}
}
