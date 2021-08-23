package hurricane.all.in.one.repository.ncar;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hurricane.all.in.one.entity.ncar.Model;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {
	List<Model> findAllByZone(String zone);
}
