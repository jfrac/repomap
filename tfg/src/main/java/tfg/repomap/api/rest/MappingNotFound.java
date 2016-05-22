package tfg.repomap.api.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import tfg.repomap.mapping.MappingNotExists;

public class MappingNotFound
	implements ExceptionMapper<MappingNotExists>
{

	@Override
	public Response toResponse(MappingNotExists arg0) {
		return Response
				.status(Response.Status.NOT_FOUND)
				.build();
	}

}
