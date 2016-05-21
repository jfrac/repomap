package tfg.repomap.api.rest;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import tfg.repomap.MapEntity2EntityException;
import tfg.repomap.MappingController;
import tfg.repomap.MappingControllerException;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Entity2EntityExistsException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.MappingNotExists;
import tfg.repomap.mapping.Pattern2PatternAlreadyExistsException;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@Path("/mappings")
public class MappingService {
	
	private MappingController controller = new MappingController();
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	@Path("/")
	public Response createMapping(
			@FormParam("url_scheme_source") String sourceSchemeURL,
			@FormParam("url_scheme_target") String targetSchemeURL
	) {
		Mapping mapping;
		try {
			
			URL sourceURL = new URL(sourceSchemeURL);
			URL targetURL = new URL(targetSchemeURL);
			
			mapping = controller.createMapping(
					sourceURL, 
					targetURL
			);
			
			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(mapping.getId().getId());
			
			return Response
					.created(builder.build())
					.build();
		} catch (MappingAlreadyExistsException e) {
			return Response
					.status(Response.Status.CONFLICT)
					.entity("Mapping already exists")
					.build();
		} catch (MappingControllerException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (MalformedURLException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}
	}
	
	@POST
	@Path("/entity2entity/{id}")
	public Response mapEntity2Entity(
			@PathParam("id") String id,
			@FormParam("entity_source") String entitySource,
			@FormParam("entity_target") String entityTarget
	) {
		MappingId mappingId = new MappingId(id);
		try { 
			controller.mapEntity2Entity(
					mappingId, 
					entitySource, 
					entityTarget);
			
			return Response
					.status(Response.Status.CREATED)
					.header("Location", "/mappings/" + id)
					.build();
		} catch (MapEntity2EntityException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (MappingNotExists e) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.build();
		} catch (EntityNotFoundException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (Entity2EntityExistsException e) {
			return Response
					.status(Response.Status.CONFLICT)
					.entity(e.getMessage())
					.build();
		}
	}
	
	@POST
	@Path("/entity2entity/{id}")
	public Response mapPattern2Pattern(
			@PathParam("id") String id,
			@FormParam("pattern_source") String patternSource,
			@FormParam("pattern_target") String patternTarget
	) {
		MappingId mappingId = new MappingId(id);
		try { 
			controller.mapPattern2Pattern(
					mappingId, 
					patternSource, 
					patternTarget);
			
			return Response
					.status(201)
					.header("Location", "/mappings/" + id)
					.build();
		} catch (MappingNotExists e) {
			return Response
					.status(404)
					.build();
		} catch (Pattern2PatternAlreadyExistsException e) {
			return Response
					.status(409)
					.entity(e.getMessage())
					.build();
		} catch (Exception e) {
			return Response
					.status(500)
					.entity(e.getMessage())
					.build();
		}
	}
	

	@GET	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getMapping(@PathParam("id") String id) {
		MappingId mappingId = new MappingId(id);
		
		try {
			Mapping mapping = controller.getMapping(mappingId);
			if (mapping == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.status(Response.Status.OK).
					entity(mapping).
					build();
		} catch (MappingDAOException e) {
			return Response.
					status(Response.Status.INTERNAL_SERVER_ERROR).
					build();
		}
		
	}
	
}
