package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.dao.DAOFactory;
import tfg.repomap.dao.MappingDAO;
import tfg.repomap.dao.xml.XMLMappingDAOException;
import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.mapping.Entity2EntityExistsException;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingController {
	
	public void mapEntity2Entity(
			URL srcScheme,
			Entity srcEntity,
			URL trgScheme,
			Entity trgEntity
	) {
		MappingDAO mappingDAO = DAOFactory.getDAO();
		Mapping mapping;
		try {
			XMLScheme xmlScheme = new XMLScheme(srcScheme);
			OWLScheme owlScheme = new OWLScheme(trgScheme);
			
			MappingId mappingId = new MappingId(xmlScheme, owlScheme);
			mapping = mappingDAO.findById(mappingId);
			if (mapping == null) {
				mapping = mappingDAO.create(new Mapping(xmlScheme, owlScheme));
			}
			
			if (!xmlScheme.hasEntity(srcEntity)) {
				throw new EntityNotFoundException();
			}
			
			if (!owlScheme.hasEntity(trgEntity)) {
				throw new EntityNotFoundException();
			}
			
			Entity2Entity e2e = new Entity2Entity(srcEntity, trgEntity);
			mapping.addEntity2Entity(e2e);
			mappingDAO.update(mapping);
		} catch (XMLMappingDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (Entity2EntityExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLSchemeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		try {
			URL xmlSchema = new URL("http://miuras.inf.um.es/ontologies/eco_punned.owl");
			Entity xmlElement = new Entity("head");
			URL owlSchema = new URL("http://miuras.inf.um.es/ontologies/eco_punned.owl");
			Entity owlClass = new Entity("ECO_0000105");
			MappingController controller = new MappingController();
			controller.mapEntity2Entity(xmlSchema, xmlElement, owlSchema, owlClass);
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
