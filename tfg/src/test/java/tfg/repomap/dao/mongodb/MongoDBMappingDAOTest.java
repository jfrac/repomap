package tfg.repomap.dao.mongodb;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import tfg.repomap.dao.MappingDAOTest;

@RunWith(JUnit4.class)
public class MongoDBMappingDAOTest 
extends MappingDAOTest
{	
	@Before
	public void setup() throws UnknownHostException {
		dao = new MongoDBMappingDAOStub();
		dao.removeAll();
	}
}
