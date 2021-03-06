package tfg.repomap.mapping;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeFactory;
import tfg.repomap.scheme.SchemeFactoryException;

@XmlAccessorType(XmlAccessType.NONE)
public class MappingId {
	
	@XmlAttribute
	private String id;
	private Scheme source;
	private Scheme target;
	
	protected MappingId() {}
	
	public MappingId(String id) {
		this();
		this.id = id;
	}
	
	public MappingId(Scheme source, Scheme target) {
		this();
		this.source = source;
		this.target = target;
		this.id = generateId(source, target)+"";
	}
	
	public MappingId(
		URL srcURLScheme, 
		URL trgURLScheme
	) throws MalformedURLException, SchemeFactoryException {
		this(SchemeFactory.create(srcURLScheme), 
				SchemeFactory.create(trgURLScheme));
	}
	
	public Scheme getTarget() {
		return target;
	}
	
	public Scheme getSource() {
		return source;
	}

	public String getId() {
		return id;
	}

	public int generateId(Scheme source, Scheme target) {
		final int prime = 31;
		int result = 1;
		result = prime * result + source.hashCode();
		result = prime * result + target.hashCode();
		return Math.abs(result);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MappingId other = (MappingId) obj;
		String oId = other.getId();
		String tId = this.getId();
		boolean e = oId.equals(tId);
		return e;
	}
}
