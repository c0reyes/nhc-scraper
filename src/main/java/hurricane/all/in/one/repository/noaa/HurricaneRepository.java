package hurricane.all.in.one.repository.noaa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hurricane.all.in.one.entity.noaa.Hurricane;

@Repository
public interface HurricaneRepository extends CrudRepository<Hurricane, Long> {
	List<Hurricane> findAll();
	
	List<Hurricane> findAllByZone(String zone);
}
