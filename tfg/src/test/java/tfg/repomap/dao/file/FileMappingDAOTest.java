package tfg.repomap.dao.file;

import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.dao.MappingDAOTest;
import tfg.repomap.scheme.owl.OWLSchemeException;

@RunWith(JUnit4.class)
public class FileMappingDAOTest 
extends MappingDAOTest
{	

	@BeforeClass
	public static void setup() throws UnknownHostException, MappingDAOException, MalformedURLException, OWLSchemeException {
		dao = new FileMappingDAOStub();
		MappingDAOTest.setup();
	}
	
}
