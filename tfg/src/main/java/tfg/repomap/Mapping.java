package tfg.repomap;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.mapping.Entity2EntityExistsException;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;

@XmlRootElement(name = "mapping")
public class Mapping {

	@XmlElement
	private Scheme source;

	@XmlElement
	private Scheme target;
	
	private Document xml;
	
	private MappingId id;
	
	@XmlElements(@XmlElement(name="entity2entity", type=Entity2Entity.class))
	//@XmlElementWrapper
	private List<Entity2Entity> entity2EntityMappings;

	public Mapping(Scheme source, Scheme target) {
		this();
		this.source = source;
		this.target = target;
		this.id = new MappingId(source, target);
	}
	
	public Mapping() {
		super();
		entity2EntityMappings = new LinkedList<Entity2Entity>();
	}
	
	public boolean containsEntity2entity(Entity2Entity e2e) {
		return entity2EntityMappings.contains(e2e);
	}
	
	public void addEntity2Entity(Entity2Entity e2e) throws Entity2EntityExistsException {
		if (this.containsEntity2entity(e2e)) {
			throw new Entity2EntityExistsException();
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
}
