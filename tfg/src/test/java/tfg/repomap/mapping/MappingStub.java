package tfg.repomap.mapping;

import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.EntityNotFoundException;

public class MappingStub extends Mapping {
	
	public MappingStub(Scheme source, Scheme target) {
		super(source, target);
	}
	
	/**
	 * Avoid validations for testing
	 */
	@Override
	public void addEntity2Entity(Entity2Entity e2e)
			throws Entity2EntityExistsException, EntityNotFoundException, SchemeException {
		entity2EntityMappings.add(e2e);
	}
}
