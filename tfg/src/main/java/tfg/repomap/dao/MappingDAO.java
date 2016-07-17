package tfg.repomap.dao;

import java.util.Collection;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.scheme.Scheme;

public interface MappingDAO {
	
	Mapping create(Mapping mapping)
			throws MappingDAOException;
	
	Mapping create(Scheme sourceScheme, Scheme targetScheme)
		throws MappingDAOException;
	
	Mapping findById(MappingId mappingId) 
		throws MappingDAOException;
	
	Collection<Mapping> findAll();
	
	void update(Mapping mapping) throws MappingDAOException;
	
	boolean remove(MappingId mappingId);
	
	void removeAll();

	void save(Mapping mapping) throws MappingDAOException;
}
