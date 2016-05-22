package tfg.repomap.api.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import tfg.repomap.MappingControllerException;

@Provider
public class MappingControlerException
	implements ExceptionMapper<MappingControllerException>
{

	@Override
	public Response toResponse(MappingControllerException arg0) {
		return Response.
				status(Response.Status.INTERNAL_SERVER_ERROR).
				entity(arg0.getMessage()).
				build();
	}

}
