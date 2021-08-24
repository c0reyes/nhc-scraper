package nhc.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import nhc.scraper.service.HaioServiceImpl;
import nhc.scraper.util.noaa.Zone;

@Slf4j
@Component
public class HurricaneScheduledTask {
	@Autowired
	HaioServiceImpl haioService;
	
	@Scheduled(fixedRate = 1800000)
	public void restartDb() throws Exception {
		log.info("Clean db");
		haioService.clean();

		for(Zone zone: Zone.values()) {
			log.info("Zone: " + zone.getName());
			
			haioService.setZone(zone.getName());
			haioService.setNoaa();
			haioService.setModel();
			haioService.setMeso();
		}
	}
}
