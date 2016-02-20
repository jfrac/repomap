package tfg.repomap.dao;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.scheme.Scheme;

public abstract class AbstractMappingDAO implements MappingDAO 
{
	@Override
	public Mapping create(Scheme sourceScheme, Scheme targetScheme) 
			throws MappingDAOException {
		Mapping mapping = new Mapping(sourceScheme, targetScheme);
		
		return mapping;
	}

}
