package tfg.repomap.dao.file;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tfg.repomap.dao.MappingDAOTest;

@RunWith(JUnit4.class)
public class FileMappingDAOTest 
extends MappingDAOTest
{	

	@Before
	public void setup() {
		dao = new FileMappingDAOStub();
		dao.removeAll();
	}
	
}
