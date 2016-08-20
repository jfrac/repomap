package tfg.repomap;

import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingCouldNotDelete;
import tfg.repomap.mapping.MappingNotExists;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.VariableException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MappingControllerService extends MappingController {
	
	@WebMethod
	public Mapping createMapping(
			URL srcSchemeURL,
			URL trgSchemeURL
	) throws MappingAlreadyExistsException, MappingControllerException;

	@WebMethod
	public void deleteMapping(String id) throws MappingCouldNotDelete;
	
	@WebMethod
	public void mappingPattern2Pattern(
		String mappingId,
		String srcPattern,
		String trgPattern
	) throws MappingNotExists, 
			 Pattern2PatternNotSameVariables, 
			 VariableException, 
			 Pattern2PatternAlreadyExistsException, 
			 MapEntity2EntityException;
	
	@WebMethod
	public void mapEntity2Entity(
			String mappingId,
			String srcEntity,
			String trgEntity
		) throws MapEntity2EntityException, 
				 MappingNotExists, 
				 EntityNotFoundException, 
				 Entity2EntityExistsException;
}
