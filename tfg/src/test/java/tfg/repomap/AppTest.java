package tfg.repomap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import api.rest.RESTTest;
import tfg.repomap.dao.file.FileMappingDAOTest;
import tfg.repomap.dao.mongodb.MongoDBMappingDAOTest;
import tfg.repomap.mapping.MappingTest;


@RunWith(Suite.class)
@SuiteClasses({
	MappingTest.class, 
	MongoDBMappingDAOTest.class,
	FileMappingDAOTest.class,
	RESTTest.class})
public class AppTest 
{
    
}
