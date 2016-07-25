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
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.scheme.SchemeFactoryException;
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
		System.out.println(id);
		if (!getDAO().remove(mappingId)) {
			throw new MappingCouldNotDelete();
		}
	}

	@Override
	public void mappingPattern2Pattern(
		URL srcSchemeURL, 
		URL trgSchemeURL, 
		String srcPattern, 
		String trgPattern
	) throws MappingNotExists, Pattern2PatternNotSameVariables, VariableException, Pattern2PatternAlreadyExistsException {
		MappingId mappingId;
		try {
			mappingId = new MappingId(srcSchemeURL, trgSchemeURL);
			mapPattern2Pattern( 
				mappingId,
				srcPattern,
				trgPattern
			);
		} catch (MalformedURLException | SchemeFactoryException e) {
			throw new MappingNotExists();
		} catch (MapEntity2EntityException e) {
			throw new MappingNotExists();
		}
		
	}

}
