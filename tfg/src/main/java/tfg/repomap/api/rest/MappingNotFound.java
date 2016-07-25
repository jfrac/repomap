package tfg.repomap.api.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MappingNotFound
	implements ExceptionMapper<NotFoundException>
{

	@Override
	public Response toResponse(NotFoundException arg0) {
		return Response
				.status(Response.Status.NOT_FOUND)
				.build();
	}

}
