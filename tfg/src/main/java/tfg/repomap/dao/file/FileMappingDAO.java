package tfg.repomap.dao.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import tfg.repomap.dao.AbstractMappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.XMLPattern;
import tfg.repomap.scheme.xml.XMLScheme;

public class FileMappingDAO extends AbstractMappingDAO
{
	protected static String BASE_PATH = "mappings/"; 
	
	@Override
	public Mapping create(Scheme sourceScheme, Scheme targetScheme) 
			throws MappingDAOException {
		Mapping mapping = super.create(sourceScheme, targetScheme);
		save(mapping);
		return mapping;
	}
	
	@Override
	public Mapping findById(MappingId mappingId) 
		throws MappingDAOException {
		File mapping = this.getMappingFile(mappingId);
		
		if (!mapping.exists()) {
			return null;
		} 
		
		try {
			return this.loadFromFile(mapping);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new MappingDAOException();
		}
	}

	@Override
	public Collection<Mapping> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Mapping mapping) throws MappingDAOException {
		save(mapping);
	}

	@Override
	public boolean remove(MappingId mappingId) {
		File mapping = this.getMappingFile(mappingId);
		if (mapping.exists()) {
			boolean deleted = mapping.delete();
			return deleted;
		}
		return false;
	}
	
	protected void save(Mapping mapping) throws MappingDAOException {
		File file = this.getMappingFile(mapping.getId());
		JAXBContext jaxbContext;
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			jaxbContext = JAXBContext.newInstance(Mapping.class, XMLScheme.class, OWLScheme.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(mapping, out);
			//jaxbMarshaller.marshal(mapping, System.out);
			out.close();
		} catch (JAXBException e) {
			throw new MappingDAOException(e);
		} catch (FileNotFoundException e) {
			throw new MappingDAOException(e);
		} catch (IOException e) {
			throw new MappingDAOException(e);
		}
	}
	
	protected File getMappingFile(MappingId mappingId) {
		String filePath = BASE_PATH + mappingId.getId() + ".xml";
		return new File(filePath);
	}
	
	protected Mapping loadFromFile(File file) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(
			Mapping.class, 
			OWLScheme.class, 
			XMLScheme.class,
			XMLPattern.class,
			OWLPattern.class
		);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Mapping mapping = (Mapping) jaxbUnmarshaller.unmarshal(file);
		return mapping;
	}

	@Override
	public Mapping create(Mapping mapping) throws MappingDAOException {
		save(mapping);
		return mapping;
	}

	@Override
	public void removeAll() {
		File folder = new File(BASE_PATH);
		
		for(File file: folder.listFiles()) 
			file.delete();
	}
}
