package tfg.repomap.dao;

import tfg.repomap.dao.xml.XMLMappingDAO;

public abstract class DAOFactory {
	
	public static MappingDAO getDAO() {
		// TODO use JVM properties
		return new XMLMappingDAO();
	}
}
