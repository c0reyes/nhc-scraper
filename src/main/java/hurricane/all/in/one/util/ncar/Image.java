package hurricane.all.in.one.util.ncar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.util.StreamUtils;

public class Image {
	private byte[] imageBytes;
	private String imageUrl;
	
	public Image() {
		
	}
	
	public Image(String imageUrl) throws IOException {
		this.imageUrl = imageUrl;
		setImage();
	}
	
	public void setUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public void setImage() throws IOException {
		if(imageUrl.indexOf("http://hurricanes.ral.ucar.edu/") == -1)
			return;
		URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    imageBytes = StreamUtils.copyToByteArray(is);
	}
	
	public byte[] getImage() {
		return this.imageBytes;
	}
}
