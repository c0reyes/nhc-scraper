package nhc.scraper.entity.noaa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Hurricane {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String zone;
	private String atcf;
	private String name;
	private String movement;
	private String pressure;
	private String type;
	private String wind;
	private String datetime;
	private String headline;
	private String center;
	private String img5day;
	private String imgWind;
	
	@CreatedDate
    private Date createdDate;
	
	private double lat;
	private double lon;
	
	public void setCenter(String center) {
		this.center = center;
		
		String[] split = center.split(",");
		this.lat = Double.parseDouble(split[0]);
		this.lon = Double.parseDouble(split[1]);
	}
}
