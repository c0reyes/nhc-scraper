package nhc.scraper.repository.ncar;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nhc.scraper.entity.ncar.Model;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {
	List<Model> findAllByZone(String zone);
}
