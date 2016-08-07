package tfg.repomap.api.rest;

import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import tfg.repomap.MapEntity2EntityException;
import tfg.repomap.MappingControllerImpl;
import tfg.repomap.MappingControllerException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingCouldNotDelete;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.MappingNotExists;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.mapping.relation2relation.Relation2RelationAlreadyExists;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.VariableException;

@Path("/mappings")
public class MappingService {
	
	private MappingControllerImpl controller = new MappingControllerImpl();
	
	@Context
	private UriInfo uriInfo;
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public Response createMapping(
			@FormParam("url_scheme_source") String sourceSchemeURL,
			@FormParam("url_scheme_target") String targetSchemeURL
	) throws MappingControllerException {
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
		} catch (MalformedURLException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		}
	}
	
	@POST
	@Path("{id}/entity2entity")
	public Response mapEntity2Entity(
		@PathParam("id") String id,
		@FormParam("entity_source") String entitySource,
		@FormParam("entity_target") String entityTarget
	) throws MappingNotExists {
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
	@Path("{id}/pattern2pattern")
	public Response mapPattern2Pattern(
		@PathParam("id") String id,
		@FormParam("pattern_source") String patternSource,
		@FormParam("pattern_target") String patternTarget
	) throws NotFoundException {
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
		} catch (Pattern2PatternAlreadyExistsException e) {
			return Response
					.status(Response.Status.CONFLICT)
					.entity(e.getMessage())
					.build();
		} catch (Pattern2PatternNotSameVariables e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (VariableException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (MapEntity2EntityException e) {
			return Response
					.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage())
					.build();
		} catch (MappingNotExists e) {
			throw new NotFoundException(e);
		} 
	}

	@GET	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getMapping(@PathParam("id") String id) 
		throws MappingControllerException, MappingNotExists
	{
		MappingId mappingId = new MappingId(id);
		Mapping mapping = controller.getMapping(mappingId);
		if (mapping == null) {
			throw new MappingNotExists();
		}
		return Response.status(Response.Status.OK)
				.entity(mapping)
				.build();
	}
	
	@PUT	
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateMapping(
		Mapping mapping
	) 
		throws MappingControllerException
	{	
		try {
			controller.updateMapping(mapping);
		} catch (MappingNotExists e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT)
				.build();
	}
	
	@DELETE	
	@Path("/{id}")
	public Response deleteMapping(@PathParam("id") String id) {
		MappingId mappingId = new MappingId(id);
		try {
			controller.deleteMapping(mappingId);
			return Response.
					status(Response.Status.NO_CONTENT).
					build();
		} catch (MappingCouldNotDelete e) {
			return Response.
					status(Response.Status.INTERNAL_SERVER_ERROR).
					build();
		}
	}
	
	@POST
	@Path("{id}/property2property")
	public Response mapProperties(
		@PathParam("id") String id,
		@FormParam("source_entity") String sourceEntity,
		@FormParam("source_attribute") String sourceAttribute,
		@FormParam("target_entity") String targetEntity,
		@FormParam("target_attribute") String targetAttribute
	) throws MappingNotExists {
		MappingId mappingId = new MappingId(id);
		try { 
			controller.mapProperties(
				mappingId, 
				sourceEntity, 
				sourceAttribute, 
				targetEntity, 
				targetAttribute
			);
			
			return Response
					.status(201)
					.header("Location", "/mappings/" + id)
					.build();
		} catch (Property2PropertyAlreadyExists e) {
			return Response
					.status(Response.Status.CONFLICT)
					.entity(e.getMessage())
					.build();
		} catch (Exception e) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(e.getMessage())
					.build();
		}
	}
	
	@POST
	@Path("{id}/relation2relation")
	@Produces(MediaType.TEXT_PLAIN)
	public Response mapRelations(
		@PathParam("id") String id,
		@FormParam("source_entity1") String sourceEntity1,
		@FormParam("source_entity2") String sourceEntity2,
		@FormParam("target_entity1") String targetEntity1,
		@FormParam("target_entity2") String targetEntity2
	) throws MappingNotExists {
		MappingId mappingId = new MappingId(id);
		
			
			try {
				controller.mapRelations(
					mappingId, 
					sourceEntity1, 
					sourceEntity2,
					targetEntity1, 
					targetEntity2);
				return Response
						.status(201)
						.header("Location", "/mappings/" + id)
						.build();
			} catch (Relation2RelationAlreadyExists e) {
				return Response
						.status(Response.Status.CONFLICT)
						.entity(e.getMessage())
						.build();
			} catch (MappingControllerException | EntityNotFoundException e) {
				return Response
						.status(Response.Status.BAD_REQUEST)
						.entity(e.getMessage())
						.build();
			}
			
			
		
	}
}
