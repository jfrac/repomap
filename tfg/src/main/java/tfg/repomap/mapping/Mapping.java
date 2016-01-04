package tfg.repomap.mapping;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@XmlRootElement(name = "mapping")
public class Mapping {

	@XmlElement
	private Scheme source;

	@XmlElement
	private Scheme target;
	
	private Document xml;
	
	private MappingId id;
	
	@XmlElements(@XmlElement(name="entity2entity", type=Entity2Entity.class))
	@XmlElementWrapper
	private List<Entity2Entity> entity2EntityMappings;
	
	@XmlElements(@XmlElement(name="pattern2pattern", type=Pattern2Pattern.class))
	@XmlElementWrapper
	private List<Pattern2Pattern> pattern2PatternMappings;

	public Mapping(Scheme source, Scheme target) {
		this();
		this.source = source;
		this.target = target;
		this.id = new MappingId(source, target);
	}
	
	public Mapping() {
		super();
		entity2EntityMappings = new LinkedList<Entity2Entity>();
		pattern2PatternMappings = new LinkedList<Pattern2Pattern>();
	}
	
	public boolean containsEntity2entity(Entity2Entity e2e) {
		return entity2EntityMappings.contains(e2e);
	}
	
	public void addEntity2Entity(Entity2Entity e2e) 
			throws Entity2EntityExistsException, EntityNotFoundException, SchemeException {
		if (this.containsEntity2entity(e2e)) {
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
	
	protected Document getXML() {
		return this.xml;
	}
	
	public void save(File f) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource dom = new DOMSource(this.getXML());
		StreamResult result = new StreamResult(f);
		//StreamResult result = new StreamResult(System.out);
		transformer.transform(dom, result);
	}

	public MappingId getId() {
		if (id == null) {
			id = new MappingId(source, target);
		}
		return id;
	}

	public void addPattern2Pattern(Pattern2Pattern p2p) throws Pattern2PatternNotExistsException {
		if (this.containsPattern2Pattern(p2p)) {
			throw new Pattern2PatternNotExistsException();
		}
		
		pattern2PatternMappings.add(p2p);
	}

	private boolean containsPattern2Pattern(Pattern2Pattern p2p) {
		return pattern2PatternMappings.contains(p2p);
	}
}
