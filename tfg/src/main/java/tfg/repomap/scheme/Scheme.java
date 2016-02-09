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
		// because JAXB
	}
	
	public Scheme(URL schemeURL) {
		this.url = schemeURL;
	}
		
	public abstract boolean hasEntity(Entity entity) throws SchemeException;
	
	public URL getURL() {
		return this.url;
	}
	
	public String getType() {
		return type;
	}
}
