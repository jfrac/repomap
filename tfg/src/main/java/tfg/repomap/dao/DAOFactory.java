package tfg.repomap.dao;

import tfg.repomap.dao.mongodb.MongoDBMappingDAO;

public abstract class DAOFactory {
	
	private static final Class<?> DEFAULT_FACTORY = MongoDBMappingDAO.class;
	public static final String DAO_FACTORY = "dao.factory";
	
	public static MappingDAO getDAO() {
		Class<?> daoClass = getDAOClass();
		
		AbstractMappingDAO mappingDAO = null;
		
		try {
			mappingDAO = (AbstractMappingDAO) daoClass.newInstance();
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}
		
		return mappingDAO;
	}
	
	protected static Class<?> getDAOClass() {
		String daoClassName = System.getProperty(DAO_FACTORY);
		
		if (daoClassName == null)
			return DEFAULT_FACTORY;
		
		Class<?> daoClass = null;
		
		try {
			daoClass = Class.forName(daoClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		if (AbstractMappingDAO.class.isAssignableFrom(daoClass)) {
			return daoClass;
		}
		
		return null;
	}
}
