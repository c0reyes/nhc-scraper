package hurricane.all.in.one.util.noaa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hurricane.all.in.one.entity.noaa.Hurricane;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NHC {
	private XMLInputFactory factory;
	private XMLEventReader eventReader;
	
	private List<Hurricane> listHurricane;
	
	private Hurricane hurricane;
	private String qName;
	
	private String lName = "";
	private boolean bImage;
	
	private URL src;
	
	public NHC() {
		
	}

	public NHC(Zone zone) throws MalformedURLException {
		this.src = new URL(zone.getUrl());
	}
	
	public void setZone(Zone zone) throws MalformedURLException {
		this.src = new URL(zone.getUrl());
	}
	
	public List<Hurricane> getRss() {
		listHurricane = new ArrayList<Hurricane>();
		
		try {
			factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
			eventReader = factory.createXMLEventReader(src.openStream());
			
			while(eventReader.hasNext()) {
	            XMLEvent event = eventReader.nextEvent();
	            
	            switch(event.getEventType()) {	      
	               	case XMLStreamConstants.START_ELEMENT:
	               		StartElement startElement = event.asStartElement();
	                   	qName = startElement.getName().getLocalPart();
	                   	if(Constants.CYCLONE.equalsIgnoreCase(qName))
	                   		hurricane = new Hurricane();         
	                   	break;
	               	case XMLStreamConstants.CHARACTERS:
	               		Characters characters = event.asCharacters();

	                    if(Constants.NAME.equalsIgnoreCase(qName))
	                    	hurricane.setName(characters.getData());
	                    if(Constants.ATCF.equalsIgnoreCase(qName))
	                    	hurricane.setAtcf(characters.getData());
	               		if(Constants.MOVEMENT.equalsIgnoreCase(qName))
		                	hurricane.setMovement(characters.getData());
	               		if(Constants.PRESSURE.equalsIgnoreCase(qName))
		                	hurricane.setPressure(characters.getData());
	               		if(Constants.TYPE.equalsIgnoreCase(qName))
		                	hurricane.setType(characters.getData());
	               		if(Constants.WIND.equalsIgnoreCase(qName))
		                	hurricane.setWind(characters.getData());
	               		if(Constants.DATETIME.equalsIgnoreCase(qName))
		                	hurricane.setDatetime(characters.getData());
	               		if(Constants.HEADLINE.equalsIgnoreCase(qName))
		                	hurricane.setHeadline(characters.getData());
	               		if(Constants.CENTER.equalsIgnoreCase(qName))
		                	hurricane.setCenter(characters.getData());
	               		
	               		if(Constants.TITLE.equalsIgnoreCase(qName) &&
	               				characters.getData().contains(lName) && 
	               				characters.getData().contains(Constants.GRAPHICS)) 
	               			bImage = true;
	               		if(Constants.DESCRIPTION.equalsIgnoreCase(qName) && bImage) 
	               			getImg(characters.getData().toString()); 
	               		
	               		qName = "";
	               		break;
	               	case XMLStreamConstants.END_ELEMENT:
	               		EndElement endElement = event.asEndElement();
	               		if(Constants.CYCLONE.equalsIgnoreCase(endElement.getName().getLocalPart())) {
	               			lName = hurricane.getName();
	               			listHurricane.add(hurricane);
	               		}
	            	   	break;
	            }
			}
		}catch (IOException | XMLStreamException e) {
			log.error(e.toString(), e);
	    }
		
	    return listHurricane;
	}
	
	private void getImg(String html) {
		Hurricane hurricane = null;
		bImage = false;
		for(Hurricane e: listHurricane) {
			if( e.getName().equals(lName) ) {
				hurricane = e;
			}
		}
		
		Document doc = Jsoup.parse(html);
		Elements imgs = doc.select("img");
		for(Element img : imgs) {
			String imgSrc = img.absUrl("src");
			
			if(imgSrc.contains(Constants.IMG_5DAY))
				hurricane.setImg5day(imgSrc);
			
			if(imgSrc.contains(Constants.IMG_WIND))
				hurricane.setImgWind(imgSrc);
		}
	}
}
