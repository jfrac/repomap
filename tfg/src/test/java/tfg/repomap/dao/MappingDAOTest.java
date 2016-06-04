package tfg.repomap.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import junit.framework.TestCase;
import tfg.repomap.Entity2EntityBuilder;
import tfg.repomap.MappingBuilder;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.mapping.property2property.Property2Property;
import tfg.repomap.scheme.entity.Entity;

@RunWith(JUnit4.class)
abstract public class MappingDAOTest 
extends TestCase
{	
	protected MappingDAO dao;

	@Test
	public void crudMappingTest() {
		Mapping mapping = MappingBuilder.newMapping();
		MappingId mappingId = mapping.getId();
		
		try {
			dao.create(mapping);
			mapping = dao.findById(mappingId);
			
			assertTrue(
				mappingId.equals(mapping.getId())
			);
			
			Entity2Entity e2e = Entity2EntityBuilder.newE2E();
			mapping.addEntity2Entity(e2e);
			dao.update(mapping);
			mapping = dao.findById(mappingId);
			assertTrue(mapping.contains(e2e));
			
			Property2Property property2Property = new Property2Property(
				new Entity("complexType"),
				"name",
				new Entity("Researcher"),
				"authorOfOntology"
			);
			mapping.addProperty2Property(property2Property);
			dao.update(mapping);
			mapping = dao.findById(mappingId);
			assertTrue(mapping.contains(property2Property));
			
			
			assertTrue(dao.remove(mappingId));
			mapping = dao.findById(mappingId);
			assertNull(mapping);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
