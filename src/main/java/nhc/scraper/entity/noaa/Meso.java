package nhc.scraper.entity.noaa;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class Meso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String zone;
	private String name;
	
	@ElementCollection
	private Map<String, String> imgUrl;
}
