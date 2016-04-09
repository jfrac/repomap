package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.dao.DAOFactory;
import tfg.repomap.dao.MappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.mapping.Entity2EntityExistsException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.Pattern2Pattern;
import tfg.repomap.mapping.Pattern2PatternNotExistsException;
import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Pattern;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingController {
	
	public void mapPattern2Pattern( 
		URL srcScheme,
		Pattern srcPattern,
		URL trgScheme,
		Pattern trgPattern
	) {
		try {
			Mapping mapping = this.getMapping(srcScheme, trgScheme);
			Pattern2Pattern p2p = new Pattern2Pattern(srcPattern, trgPattern);
			mapping.addPattern2Pattern(p2p);
			this.saveMapping(mapping);
		} catch (MappingDAOException e) {
			e.printStackTrace();
		} catch (OWLSchemeException e) {
			e.printStackTrace();
		} catch (Pattern2PatternNotExistsException e) {
			e.printStackTrace();
		}
	}
	
	public void mapEntity2Entity(
			URL srcScheme,
			Entity srcEntity,
			URL trgScheme,
			Entity trgEntity
	) throws MapEntity2EntityException {
		try {
			Mapping mapping = this.getMapping(srcScheme, trgScheme);
			Entity2Entity e2e = new Entity2Entity(srcEntity, trgEntity);
			mapping.addEntity2Entity(e2e);
			this.saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MapEntity2EntityException(e);
		}  catch (Entity2EntityExistsException e) {
			throw new MapEntity2EntityException(e);
		} catch (EntityNotFoundException e) {
			throw new MapEntity2EntityException(e);
		} catch (SchemeException e) {
			throw new MapEntity2EntityException(e);
		}
	}
	
	protected void saveMapping(Mapping mapping) 
			throws MappingDAOException {
		MappingDAO mappingDAO = DAOFactory.getDAO();
		mappingDAO.update(mapping);
	}
	
	protected Mapping getMapping(
			URL srcScheme, 
			URL trgScheme
	) throws MappingDAOException, OWLSchemeException {
		XMLScheme xmlScheme = new XMLScheme(srcScheme);
		OWLScheme owlScheme = new OWLScheme(trgScheme);
		
		MappingDAO mappingDAO = DAOFactory.getDAO();
		MappingId mappingId = new MappingId(xmlScheme, owlScheme);
		Mapping mapping = mappingDAO.findById(mappingId);
		if (mapping == null) {
			mapping = mappingDAO.create(xmlScheme, owlScheme);
		}
		return mapping;
	}
	
	public static void main(String[] args) {
		
		try {
			URL xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
			URL owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
			
			MappingController controller = new MappingController();
			
			Entity xmlElement = new Entity("complexContent");
			Entity owlClass = new Entity("Researcher");
			controller.mapEntity2Entity(xmlSchema, xmlElement, owlSchema, owlClass);
			
			Pattern xmlPattern = new Pattern("xml");
			Pattern oppl2Pattern = new Pattern("oppl2");
			controller.mapPattern2Pattern(xmlSchema, xmlPattern, owlSchema, oppl2Pattern);
			
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			System.out.println("URL mal formada");
		} catch (MapEntity2EntityException e) {
			System.out.println("Error al añadir el mapeo e2e: " + e.getMessage());
		}
	}

}
