package tfg.repomap.dao.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import tfg.repomap.dao.MappingDAO;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.xml.XMLScheme;

public class XMLMappingDAO implements MappingDAO {

	private static final String BASE_PATH = "."; 

	public Mapping create(Scheme sourceScheme, Scheme targetScheme) 
			throws XMLMappingDAOException {
		Mapping mapping = new Mapping(sourceScheme, targetScheme);
		save(mapping);
		return mapping;
	}

	public Mapping findById(MappingId mappingId) throws XMLMappingDAOException {
		String filePath = this.getFilePath(mappingId);
		File mapping = new File(filePath);
		
		if (!mapping.exists()) {
			return null;
		} 
		
		try {
			return this.loadFromFile(new File(filePath));
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new XMLMappingDAOException();
		}
	}

	public Collection<Mapping> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(Mapping mapping) throws XMLMappingDAOException {
		save(mapping);
	}

	public boolean remove(MappingId mappingId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void save(Mapping mapping) throws XMLMappingDAOException {
		File file = new File(this.getFilePath(mapping.getId()));
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Mapping.class, XMLScheme.class, OWLScheme.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(mapping, new FileOutputStream(file));
			//jaxbMarshaller.marshal(mapping, System.out);
		} catch (JAXBException e) {
			throw new XMLMappingDAOException();
		} catch (FileNotFoundException e) {
			throw new XMLMappingDAOException();
		}
	}
	
	protected String getFilePath(MappingId mappingId) {
		return BASE_PATH + mappingId.getId() + ".xml";
	}
	
	protected Mapping loadFromFile(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Mapping.class, OWLScheme.class, XMLScheme.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Mapping mapping = (Mapping) jaxbUnmarshaller.unmarshal(file);
		return mapping;
	}
}
