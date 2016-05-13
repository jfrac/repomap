package tfg.repomap.api.rest;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tfg.repomap.MappingController;
import tfg.repomap.MappingControllerException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;

@Path("/")
public class MappingService {
	
	@POST
	@Path("/mappings")
	@Produces(MediaType.TEXT_PLAIN)
	public Response createMapping(
			@FormParam("url_scheme_source") String sourceSchemeURL,
			@FormParam("url_scheme_target") String targetSchemeURL
	) {
		MappingController controller = new MappingController();
		Mapping mapping;
		try {
			
			URL sourceURL = new URL(sourceSchemeURL);
			URL targetURL = new URL(targetSchemeURL);
			
			mapping = controller.createMapping(
					sourceURL, 
					targetURL
			);
			
			return Response
					.status(201)
					.header("Location", "/mappings/" + mapping.getId().getId())
					.build();
		} catch (MappingAlreadyExistsException e) {
			return Response
					.status(409)
					.entity("Mapping already exists")
					.build();
		} catch (MappingControllerException e) {
			return Response
					.status(500)
					.entity(e.getMessage())
					.build();
		} catch (MalformedURLException e) {
			return Response
					.status(500)
					.entity(e.getMessage())
					.build();
		}
	}
	
}
