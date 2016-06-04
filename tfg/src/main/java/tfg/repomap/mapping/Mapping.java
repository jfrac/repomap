package tfg.repomap.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2Pattern;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.mapping.property2property.Property2Property;
import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;

@XmlRootElement(name = "mapping")
public class Mapping {

	@XmlElement
	public Scheme source;

	public Scheme getSource() {
		return source;
	}

	public Scheme getTarget() {
		return target;
	}

	@XmlElement
	private Scheme target;
		
	private MappingId id;
	
	@XmlElements(@XmlElement(name="entity2entity", type=Entity2Entity.class))
	@XmlElementWrapper
	private Set<Entity2Entity> entity2EntityMappings;
	
	@XmlElements(@XmlElement(name="pattern2pattern", type=Pattern2Pattern.class))
	@XmlElementWrapper
	private Set<Pattern2Pattern> pattern2PatternMappings;
	
	@XmlElements(@XmlElement(name="property2property", type=Property2Property.class))
	@XmlElementWrapper
	private Set<Property2Property> property2PropertyMappings;
	
	private Mapping() {
		super();
		entity2EntityMappings = new HashSet<Entity2Entity>();
		pattern2PatternMappings = new HashSet<Pattern2Pattern>();
		property2PropertyMappings = new HashSet<Property2Property>();
	}
	
	public Mapping(Scheme source, Scheme target) {
		this();
		this.source = source;
		this.target = target;
		this.id = new MappingId(source, target);
	}
	
	public Mapping(MappingId mappingId) {
		this();
		this.source = mappingId.getSource();
		this.target = mappingId.getTarget();
		this.id = mappingId;
	}
	
	public void addEntity2Entity(Entity2Entity e2e) 
		throws Entity2EntityExistsException, 
			   EntityNotFoundException, 
			   SchemeException
	{
		if (this.contains(e2e)) {
			throw new Entity2EntityExistsException();
		}
		
		if (!this.source.hasEntity(e2e.getSource())) {
			throw new EntityNotFoundException();
		}
		
		if (!this.target.hasEntity(e2e.getTarget())) {
			throw new EntityNotFoundException();
		}
		
		entity2EntityMappings.add(e2e);
	}
	
	public void addEntity2Entity(String srcEntity, String trgEntity) 
		throws Entity2EntityExistsException, 
			   EntityNotFoundException, 
			   SchemeException
	{
		Entity src = new Entity(srcEntity);
		Entity trg = new Entity(trgEntity);
		Entity2Entity e2e = new Entity2Entity(src, trg);
		this.addEntity2Entity(e2e);
	}
	
	public MappingId getId() {
		if (id == null) {
			id = new MappingId(source, target);
		}
		return id;
	}

	public void addPattern2Pattern(Pattern2Pattern p2p) 
		throws Pattern2PatternAlreadyExistsException {
		if (this.contains(p2p)) {
			throw new Pattern2PatternAlreadyExistsException();
		}
		
		pattern2PatternMappings.add(p2p);
	}
	
	public boolean contains(Entity2Entity e2e) {
		return entity2EntityMappings.contains(e2e);
	}
	
	public boolean contains(Pattern2Pattern p2p) {
		return pattern2PatternMappings.contains(p2p);
	}
	
	public boolean contains(Property2Property p2p) {
		return property2PropertyMappings.contains(p2p);
	}

	public void addPattern2Pattern(
		String patternSourceString, 
		String patternTargetString
	) throws Pattern2PatternNotSameVariables, 
		     VariableException, 
		     Pattern2PatternAlreadyExistsException 
	{
		Pattern patternSource = source.createPattern(patternSourceString);
		Pattern patternTarget = source.createPattern(patternTargetString);
		Pattern2Pattern p2p = new Pattern2Pattern(patternSource, patternTarget);
		this.addPattern2Pattern(p2p);
	}

	public void addProperty2Property(Property2Property p2p) 
		throws Property2PropertyAlreadyExists {
		if (this.property2PropertyMappings.contains(p2p)) {
			throw new Property2PropertyAlreadyExists();
		}
		this.property2PropertyMappings.add(p2p);
	}
}
