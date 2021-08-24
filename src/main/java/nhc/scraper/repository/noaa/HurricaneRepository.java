package nhc.scraper.repository.noaa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nhc.scraper.entity.noaa.Hurricane;

@Repository
public interface HurricaneRepository extends CrudRepository<Hurricane, Long> {
	List<Hurricane> findAll();
	
	List<Hurricane> findAllByZone(String zone);
}
