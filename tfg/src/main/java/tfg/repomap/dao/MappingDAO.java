package tfg.repomap.dao;

import java.util.Collection;

import tfg.repomap.Mapping;
import tfg.repomap.dao.xml.XMLMappingDAOException;
import tfg.repomap.mapping.MappingId;

public interface MappingDAO {
	
	Mapping create(Mapping mapping) throws XMLMappingDAOException;
	
	Mapping findById(MappingId mappingId) throws XMLMappingDAOException;
	
	Collection<Mapping> findAll();
	
	void update(Mapping mapping) throws XMLMappingDAOException;
	
	boolean remove(MappingId mappingId);
	
}
