package nhc.scraper.repository.noaa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nhc.scraper.entity.noaa.Meso;

@Repository
public interface MesoRepository extends CrudRepository<Meso, Long> {
	List<Meso> findAllByZone(String zone);
}

