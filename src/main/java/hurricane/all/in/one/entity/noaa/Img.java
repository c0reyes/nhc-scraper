package hurricane.all.in.one.entity.noaa;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Img {
	private String key;
	private String value;
	
	public Img(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
