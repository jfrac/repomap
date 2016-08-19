package tfg.repomap.dao.mongodb;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class MongoDBMappingDAOStub
	extends MongoDBMappingDAO
{
	public MongoDBMappingDAOStub() throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("repomap_tests");
		
	}
}
