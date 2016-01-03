package tfg.repomap.scheme;

import java.net.URL;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class Scheme {
	
	@XmlElement
	private URL url;
	@XmlAttribute
	private String type;
	
	public Scheme() {
		
	}
	
	public Scheme(URL schemeURL) {
		this.url = schemeURL;
	}
		
	public abstract boolean hasEntity(Entity entity);
	
	public URL getURL() {
		return this.url;
	}
	
	public String getType() {
		return type;
	}
}
