package tfg.repomap.dao.mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

public class MongoDBMappingDAOStub
	extends MongoDBMappingDAO
{
	// Sobreescribimos para cargar una base de datos diferente para tests
	public MongoDBMappingDAOStub() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("repomap_tests");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Limpia el collection
	public void clear() {
		BasicDBObject document = new BasicDBObject();
		getCollection().remove(document);
	}
}
