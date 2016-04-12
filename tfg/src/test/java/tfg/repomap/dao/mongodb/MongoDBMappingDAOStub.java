package tfg.repomap.dao.mongodb;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class MongoDBMappingDAOStub
	extends MongoDBMappingDAO
{
	public MongoDBMappingDAOStub() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("repomap_tests");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
