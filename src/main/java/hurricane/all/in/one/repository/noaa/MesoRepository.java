package hurricane.all.in.one.repository.noaa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hurricane.all.in.one.entity.noaa.Meso;

@Repository
public interface MesoRepository extends CrudRepository<Meso, Long> {
	List<Meso> findAllByZone(String zone);
}

