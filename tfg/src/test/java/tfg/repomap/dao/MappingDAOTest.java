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
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.property2property.Property2Property;
import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.mapping.property2property.Property2PropertyNotValid;
import tfg.repomap.mapping.relation2relation.Relation2Relation;
import tfg.repomap.mapping.relation2relation.Relation2RelationAlreadyExists;
import tfg.repomap.scheme.Relation;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@RunWith(JUnit4.class)
abstract public class MappingDAOTest 
extends TestCase
{	
	protected MappingDAO dao;

	@Test
	public void crudMappingTest() 
		throws MappingDAOException, 
		       Property2PropertyAlreadyExists, 
		       Property2PropertyNotValid, 
		       Relation2RelationAlreadyExists, 
		       SchemeException, 
		       EntityNotFoundException, 
		       Entity2EntityExistsException {
		
		Mapping mapping = MappingBuilder.newMapping();
		MappingId mappingId = mapping.getId();
		
		
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
			
			
			Relation2Relation relation2Relation = new Relation2Relation(
				new Relation("complexType", "simpleType"),
				new Relation("Researcher", "Student")
			);
			mapping.addRelation2Relation(relation2Relation);
			dao.update(mapping);
			mapping = dao.findById(mappingId);
			assertTrue(mapping.contains(relation2Relation));
			
			
			assertTrue(dao.remove(mappingId));
			mapping = dao.findById(mappingId);
			assertNull(mapping);
			
	}
}
