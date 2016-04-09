package tfg.repomap.dao.mongodb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import junit.framework.TestCase;
import tfg.repomap.MappingBuilder;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;

@RunWith(JUnit4.class)
public class MongoDBMappingDAOTest 
extends TestCase
{	
	private MongoDBMappingDAOStub dao;

	@Before
	public void setup() {
		dao = new MongoDBMappingDAOStub();
		dao.clear();
	}

	@Test
	public void mongoMappingTest() {
		Mapping mapping = MappingBuilder.newMapping();
		MappingId mappingId = mapping.getId();
		
		try {
			dao.create(mapping);
			mapping = dao.findById(mappingId);
			
			assertEquals(
				mappingId.getId(), 
				mapping.getId().getId()
			);
			
			dao.remove(mappingId);
			mapping = dao.findById(mappingId);
			assertNull(mapping);
			
		} catch (MappingDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
