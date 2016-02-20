package tfg.repomap.mapping;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import junit.framework.TestCase;
import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.SchemeStub;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@RunWith(JUnit4.class)
public class MappingTest extends TestCase
{	
	@Test (expected = Entity2EntityExistsException.class)
	public void addEntity2EntityRepeatedTest()
		throws Entity2EntityExistsException, 
			   EntityNotFoundException, 
			   SchemeException, MalformedURLException
	{
		Entity xmlE = new Entity("xmlE");
		Entity owlE = new Entity("owlE");
		Entity2Entity e2e = new Entity2Entity(xmlE, owlE);
		
		Scheme scheme = new SchemeStub(new URL("http://google.com"));
		
		
		Mapping mapping = new Mapping(scheme, scheme);
		mapping.addEntity2Entity(e2e);
		mapping.addEntity2Entity(e2e);
	}
}
