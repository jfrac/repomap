package tfg.repomap.dao;

import java.util.Collection;

import tfg.repomap.dao.xml.XMLMappingDAOException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;

public interface MappingDAO {
	
	Mapping create(Scheme sourceScheme, Scheme targetScheme) throws XMLMappingDAOException;
	
	Mapping findById(MappingId mappingId) throws XMLMappingDAOException;
	
	Collection<Mapping> findAll();
	
	void update(Mapping mapping) throws XMLMappingDAOException;
	
	boolean remove(MappingId mappingId);
	
}
