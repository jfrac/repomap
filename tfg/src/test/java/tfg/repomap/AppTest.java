package tfg.repomap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tfg.repomap.dao.file.FileMappingDAOTest;
import tfg.repomap.dao.mongodb.MongoDBMappingDAOTest;
import tfg.repomap.mapping.MappingTest;


@RunWith(Suite.class)
@SuiteClasses({
	MappingTest.class, 
	MongoDBMappingDAOTest.class,
	FileMappingDAOTest.class})
public class AppTest 
{
    
}
