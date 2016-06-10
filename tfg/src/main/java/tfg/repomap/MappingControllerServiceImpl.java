package tfg.repomap;

import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;

@WebService(endpointInterface = "tfg.repomap.MappingControllerService")
public class MappingControllerServiceImpl 
	extends MappingControllerImpl 
	implements MappingControllerService {

	@WebMethod
	public Mapping createMapping(
			URL srcSchemeURL,
			URL trgSchemeURL
	) throws MappingAlreadyExistsException, MappingControllerException {
		return super.createMapping(srcSchemeURL, trgSchemeURL);
	}

}
