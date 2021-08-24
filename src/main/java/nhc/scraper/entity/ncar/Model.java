package nhc.scraper.entity.ncar;

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
public class Model {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String zone;
	private String name;
	private String imgTrack;
	private String imgIntensity;
}
