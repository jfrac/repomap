package tfg.repomap.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Document;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@XmlRootElement(name = "mapping")
public class Mapping {

	@XmlElement
	public Scheme source;

	@XmlElement
	private Scheme target;
	
	private Document xml;
	
	private MappingId id;
	
	@XmlElements(@XmlElement(name="entity2entity", type=Entity2Entity.class))
	@XmlElementWrapper
	private Set<Entity2Entity> entity2EntityMappings;
	
	@XmlElements(@XmlElement(name="pattern2pattern", type=Pattern2Pattern.class))
	@XmlElementWrapper
	private Set<Pattern2Pattern> pattern2PatternMappings;

	public Mapping(Scheme source, Scheme target) {
		this();
		this.source = source;
		this.target = target;
		this.id = new MappingId(source, target);
	}
	
	private Mapping() {
		super();
		entity2EntityMappings = new HashSet<Entity2Entity>();
		pattern2PatternMappings = new HashSet<Pattern2Pattern>();
	}
	
	public void addEntity2Entity(Entity2Entity e2e) 
			throws Entity2EntityExistsException, EntityNotFoundException, SchemeException {
		if (this.contains(e2e)) {
			throw new Entity2EntityExistsException();
		}
		
		if (!this.source.hasEntity(e2e.getSource())) {
			throw new EntityNotFoundException();
		}
		
		/*if (!this.target.hasEntity(e2e.getTarget())) {
			throw new EntityNotFoundException();
		}*/
		
		entity2EntityMappings.add(e2e);
	}
	
	protected Document getXML() {
		return this.xml;
	}

	public MappingId getId() {
		if (id == null) {
			id = new MappingId(source, target);
		}
		return id;
	}

	public void addPattern2Pattern(Pattern2Pattern p2p) throws Pattern2PatternNotExistsException {
		if (this.contains(p2p)) {
			throw new Pattern2PatternNotExistsException();
		}
		
		pattern2PatternMappings.add(p2p);
	}
	
	protected boolean contains(Entity2Entity e2e) {
		return entity2EntityMappings.contains(e2e);
	}
	
	protected boolean contains(Pattern2Pattern p2p) {
		return pattern2PatternMappings.contains(p2p);
	}
}
