package tfg.repomap;

import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MappingControllerService extends MappingController {
	
	@WebMethod
	public Mapping createMapping(
			URL srcSchemeURL,
			URL trgSchemeURL
	) throws MappingAlreadyExistsException, MappingControllerException;

}
