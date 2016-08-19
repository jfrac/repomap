package tfg.repomap.dao;

import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import tfg.repomap.mapping.pattern2pattern.Pattern2Pattern;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.mapping.property2property.Property2Property;
import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.mapping.property2property.Property2PropertyNotValid;
import tfg.repomap.mapping.relation2relation.Relation2Relation;
import tfg.repomap.mapping.relation2relation.Relation2RelationAlreadyExists;
import tfg.repomap.scheme.Relation;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;

@RunWith(JUnit4.class)
abstract public class MappingDAOTest 
extends TestCase
{
	protected static MappingDAO dao;
	protected static Mapping mapping;

	@BeforeClass
	public static void setup() throws UnknownHostException, MappingDAOException {
		mapping = MappingBuilder.newMapping();
		dao.create(mapping);
	}
	
	@AfterClass
	public static void endTest() throws MappingDAOException {
		
		// Delete mapping

		assertTrue(dao.remove(mapping.getId()));
		mapping = dao.findById(mapping.getId());
		assertNull(mapping);
	}

	@Test
	public void crudMappingTest() 
		throws MappingDAOException, 
		Property2PropertyAlreadyExists, 
		Property2PropertyNotValid, 
		Relation2RelationAlreadyExists, 
		SchemeException, 
		EntityNotFoundException, 
		Entity2EntityExistsException, 
		Pattern2PatternNotSameVariables, 
		VariableException, 
		Pattern2PatternAlreadyExistsException
	{
		MappingId mappingId = mapping.getId();
		mapping = dao.findById(mappingId);

		assertTrue(
			mappingId.equals(mapping.getId())
		);

		// Entity 2 Entity

		Entity2Entity e2e = Entity2EntityBuilder.newE2E();
		mapping.addEntity2Entity(e2e);
		dao.update(mapping);
		mapping = dao.findById(mappingId);
		assertTrue(mapping.contains(e2e));

		// Pattern 2 Pattern

		String opplScriptString = "";
		opplScriptString = "?x:CLASS"+"\n";
		opplScriptString += "SELECT "+"\n";
		opplScriptString += "?x SubClassOf molecular_function "+"\n";
		opplScriptString += "WHERE "+"\n";
		opplScriptString += "?x Match(\"hormone receptor binding\")"+"\n";
		opplScriptString += "BEGIN "+"\n";
		opplScriptString += "ADD ?x SubClassOf binding "+"\n";
		opplScriptString += "END;";

		Pattern2Pattern pat2Pat = new Pattern2Pattern(
		new XMLPattern(
			"<schema>"
					+ "<atribute ></atribute>"
					+ "<simpleType>?x</simpleType>"
					+ "</schema>"
			),
			new OWLPattern(opplScriptString)
		);

		mapping.addPattern2Pattern(pat2Pat);

		// Property 2 Property

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

		// Relation 2 Relation

		Relation2Relation relation2Relation = new Relation2Relation(
			new Relation("complexType", "simpleType"),
			new Relation("Researcher", "Student")
		);
		mapping.addRelation2Relation(relation2Relation);
		dao.update(mapping);
		mapping = dao.findById(mappingId);
		assertTrue(mapping.contains(relation2Relation));
	}
}
