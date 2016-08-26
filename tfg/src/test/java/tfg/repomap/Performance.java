package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.dao.mongodb.MongoDBMappingDAO;
import tfg.repomap.dao.mongodb.MongoDBMappingDAOStub;
import tfg.repomap.mapping.MappingStub;
import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.xml.XMLScheme;

public class Performance {
	
	public static void main(String[] args) 
		throws MalformedURLException, UnknownHostException, MappingDAOException, Entity2EntityExistsException, EntityNotFoundException, SchemeException {
		
		URL xmlSchema;
		URL owlSchema;
		
		xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
		owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
		
		Scheme sourceScheme = new XMLScheme(xmlSchema);
		Scheme targetScheme = new OWLScheme(owlSchema);

		MappingStub mapping = new MappingStub(sourceScheme, targetScheme);
		
		MongoDBMappingDAOStub mongoDAO = new MongoDBMappingDAOStub();
		mongoDAO.save(mapping);
		
		Entity2Entity e2e;
		
		for (int i = 0; i < 100; i++) {
			
		    e2e = new Entity2Entity(
				new Entity(UUID.randomUUID().toString()), 
				new Entity(UUID.randomUUID().toString())
			);
			
			mapping.addEntity2Entity(e2e);
			mongoDAO.save(mapping);
		}
		
	}
}
