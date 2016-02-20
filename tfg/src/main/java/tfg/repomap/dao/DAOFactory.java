package tfg.repomap.dao;

import tfg.repomap.dao.file.FileMappingDAO;

public abstract class DAOFactory {
	
	public static MappingDAO getDAO() {
		// TODO use JVM properties
		return new FileMappingDAO();
	}
}
