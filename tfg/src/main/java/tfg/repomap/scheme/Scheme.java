package tfg.repomap.scheme;

import java.net.URL;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;

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
		this();
		this.url = schemeURL;
	}
	
	/**
	 * Validate the pattern
	 * @param scheme
	 * @throws VariableException 
	 */
	abstract public void validate(Pattern pattern) throws VariableException;
	
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

	public abstract boolean hasEntity(Entity entity)
		throws SchemeException;
	
	public abstract Pattern createPattern(String pattern) 
			throws VariableException;
	
	public abstract boolean hasAttribute(
		Entity sourceEntity, 
		String sourceAttribute
	) throws EntityNotFoundException, SchemeException;
	
	public URL getURL() {
		return this.url;
	}
	
	public String getType() {
		return type;
	}

}
