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
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scheme other = (Scheme) obj;
		return other.equals(this);
	}

	public abstract boolean hasEntity(Entity entity) throws SchemeException;
	
	public URL getURL() {
		return this.url;
	}
	
	public String getType() {
		return type;
	}
}
