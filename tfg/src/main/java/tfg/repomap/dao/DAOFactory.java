package tfg.repomap.dao;

import tfg.repomap.dao.mongodb.MongoDBMappingDAO;

public abstract class DAOFactory {
	
	public static MappingDAO getDAO() {
		// TODO use JVM properties
		return new MongoDBMappingDAO();
	}
}
