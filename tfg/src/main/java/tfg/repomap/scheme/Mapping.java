package tfg.repomap.scheme;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import tfg.repomap.scheme.mapping.Entity2Entity;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.xml.XMLScheme;

@XmlRootElement(name = "mapping")
public class Mapping extends XMLScheme {

	@XmlElement
	private Scheme source;

	@XmlElement
	private Scheme target;
	
	@XmlElements(@XmlElement(name="entity2entity", type=Entity2Entity.class))
	@XmlElementWrapper
	private List<Entity2Entity> entity2EntityMappings;

	public static void createMapping(
		XMLScheme xmlScheme, 
		Entity source, 
		OWLScheme owlScheme, 
		Entity target
	) throws TransformerException, ParserConfigurationException, MalformedURLException {
		
		try {
			Mapping mapping = new Mapping(xmlScheme, owlScheme);
			mapping.addMappingEntity2Entity(new Entity2Entity(source, target));
			
			File file = new File("mappings.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Mapping.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//jaxbMarshaller.marshal(mapping, (Result) file);
			jaxbMarshaller.marshal(mapping, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	public Mapping() throws MalformedURLException {
		super(new URL("hola"));
	}

	public Mapping(URL schemeURL) {
		super(schemeURL);
	}

	public Mapping(Document d) {
		super(d);
	}
	
	public Mapping(Scheme source, Scheme target) throws MalformedURLException {
		super(new URL("http://um.es"));
		this.source = source;
		this.target = target;
	}

	@Override
	public boolean hasEntity(Entity entity) {
		return false;
	}
	
	private void addMappingEntity2Entity(Entity2Entity e2e) {
		if (this.entity2EntityMappings == null) {
			entity2EntityMappings = new LinkedList<Entity2Entity>();
		}
		entity2EntityMappings.add(e2e);
	}
	
	public void save(File f) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource dom = new DOMSource(this.getXml());
		StreamResult result = new StreamResult(f);
		//StreamResult result = new StreamResult(System.out);

		transformer.transform(dom, result);
	}
}
