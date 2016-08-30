package api.rest;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class RESTTest extends TestCase {
	
	protected final static String ENDPOINT_URL = "http://localhost:9999/";
	
	protected static Client client;
	protected static ClientResponse response;
	protected static WebResource resource;
	protected static WebResource resourceWithIdentifier;
	protected static MultivaluedMap<String, String> params;
	
	@BeforeClass
	public static void setup() {
		client = Client.create();
		String path = "mappings/";

		resource = client.resource(ENDPOINT_URL + path);
	}
	
	@Before
	public void before() {
		params = params();
	}
	
	@Test
	public void createMapping() {
		params.add("url_scheme_source", "http://sele.inf.um.es/swit/zinc/molecule.xsd");
		params.add("url_scheme_target", "http://miuras.inf.um.es/ontologies/molecule.owl");

		response = resource.method(
			"POST", 
			ClientResponse.class,
			params
		);
		
		URI newLocation = response.getLocation();
		resource = client.resource(newLocation);
		resourceWithIdentifier = client.resource(newLocation);
		
		assertEquals(
			response.getStatus(), 
			Response.Status.CREATED.getStatusCode()
		);
	}
	
	@Test
	public void createEntity2EntityMapping() {
		params.add("entity_source", "atom");
		params.add("entity_target", "Atom");
		
		changeResourceCollection("entity2entity");
		
		response = resource.method(
			"POST",
			ClientResponse.class,
			toFormParams(params)
		);
		
		assertEquals(
			response.getStatus(),
			Response.Status.CREATED.getStatusCode()
		);
	}
	

	public void deleteMapping() {
		response = resourceWithIdentifier.method(
			"DELETE", 
			ClientResponse.class
		);
		
		assertEquals(
			response.getStatus(),
			Response.Status.NO_CONTENT.getStatusCode()
		);
	}
	
	protected static MultivaluedMap<String, String> params() {
		return new MultivaluedMapImpl();
	}
	
	protected static void changeResourceCollection(String newCollection) {
		resource = client.resource(resourceWithIdentifier.getURI().toString() + "/" + newCollection);
	}
	
	protected static Form toFormParams(MultivaluedMap<String, String> params){
		Form form = new Form();
		form.putAll(params);
		return form;
	}
}
