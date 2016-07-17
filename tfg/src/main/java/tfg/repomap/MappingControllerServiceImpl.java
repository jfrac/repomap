package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingCouldNotDelete;
import tfg.repomap.mapping.MappingId;

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

}
