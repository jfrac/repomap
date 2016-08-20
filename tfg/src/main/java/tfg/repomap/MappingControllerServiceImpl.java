package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingCouldNotDelete;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.MappingNotExists;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.VariableException;

@WebService(endpointInterface = "tfg.repomap.MappingControllerService")
public class MappingControllerServiceImpl 
	extends MappingControllerImpl 
	implements MappingControllerService {

	@WebMethod
	public Mapping createMapping(
		String srcSchemeURL,
		String trgSchemeURL
	) throws MappingControllerException {
		try {
			return super.createMapping(new URL(srcSchemeURL), new URL(trgSchemeURL));
		} catch (MalformedURLException | MappingAlreadyExistsException | MappingControllerException e) {
			throw new MappingControllerException();
		}
	}
	
	public void deleteMapping(String id)
		throws MappingCouldNotDelete {
		MappingId mappingId = new MappingId(id);
		if (!getDAO().remove(mappingId)) {
			throw new MappingCouldNotDelete();
		}
	}

	@Override
	public void mappingPattern2Pattern(
		String mappingId, 
		String srcPattern, 
		String trgPattern
	) throws MappingNotExists, Pattern2PatternNotSameVariables, VariableException, Pattern2PatternAlreadyExistsException, MapEntity2EntityException {
		
	
			
			mapPattern2Pattern( 
				new MappingId(mappingId),
				srcPattern,
				trgPattern
			);
	
		
	}
	
	public void mapEntity2Entity(
		String mappingId,
		String srcEntity,
		String trgEntity
	) throws MapEntity2EntityException, 
			 MappingNotExists, 
			 EntityNotFoundException, 
			 Entity2EntityExistsException {
		super.mapEntity2Entity(new MappingId(mappingId), srcEntity, trgEntity);
	}

}
