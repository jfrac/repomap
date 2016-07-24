package tfg.repomap.dao.mongodb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException.DuplicateKey;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import tfg.repomap.dao.AbstractMappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.pattern.Pattern;

public class MongoDBMappingDAO extends AbstractMappingDAO
{
	protected DB db;

	public MongoDBMappingDAO() {
		try {
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDB("repomap");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Mapping create(Scheme sourceScheme, Scheme targetScheme) 
			throws MappingDAOException {
		Mapping mapping = super.create(sourceScheme, targetScheme);
		save(mapping);
		return mapping;
	}
	
	public Mapping create(Mapping mapping) 
		throws MappingDAOException
	{
		save(mapping);
		return mapping;
	}

	public void save(Mapping mapping) throws MappingDAOException {
		try {
			String mappingJSON = mappingToJSON(mapping);
			DBObject dbObject = (DBObject)JSON.parse(mappingJSON);
			dbObject.put("_id", mapping.getId().getId());
			db.getCollection("mappings").insert(dbObject);
		} catch (DuplicateKey e) {
			update(mapping);
		} catch (Exception e) {
			throw new MappingDAOException();
		}
	}

	@Override
	public Mapping findById(MappingId mappingId) throws MappingDAOException {
		DBCollection collection = db.getCollection("mappings");
		BasicDBObject query = new BasicDBObject();
		query.append("_id", mappingId.getId());
		DBObject dbObj = collection.findOne(query);
		if (dbObj == null) {
			return null;
		}
		dbObj.removeField("_id");
		
		Gson gson = getGson();
		Mapping mapping;
		try {
			mapping = gson.fromJson(dbObj.toString(), Mapping.class);
			return mapping;
		} catch (Exception e) {
			throw new MappingDAOException(e);
		}
	}

	@Override
	public Collection<Mapping> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Mapping mapping) throws MappingDAOException {
		try {
			BasicDBObject query = new BasicDBObject();
			query.append("_id", mapping.getId().getId());
			String mappingJSON = mappingToJSON(mapping);
			DBObject dbObjectMapping = (DBObject)JSON.parse(mappingJSON);
			getCollection().update(query, dbObjectMapping);
		} catch (IOException e) {
			throw new MappingDAOException();
		}
	}

	@Override
	public boolean remove(MappingId mappingId) {
		BasicDBObject query = new BasicDBObject();
		query.append("_id", mappingId.getId());
		
		WriteResult result = getCollection().remove(query);
		return result.getN() > 0;
	}

	protected DBCollection getCollection() {
		return db.getCollection("mappings");
	}
		
	protected String mappingToJSON(Mapping mapping) 
			throws JsonGenerationException, JsonMappingException, IOException {
		Gson gson = getGson();
		String mappingJSON = gson.toJson(mapping, Mapping.class);
		return mappingJSON; 
	}
	
	protected Gson getGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Scheme.class, new SchemeAdapter());
		gsonBuilder.registerTypeAdapter(Pattern.class, new GsonAdapter<Pattern>());
		Gson gson = gsonBuilder.create();
		return gson;
	}
	
	public void removeAll() {
		BasicDBObject document = new BasicDBObject();
		getCollection().remove(document);
	}
}
