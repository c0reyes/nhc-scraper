package hurricane.all.in.one;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hurricane.all.in.one.service.HaioServiceImpl;
import hurricane.all.in.one.util.noaa.Zone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HAIOcheduledTasks {
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
