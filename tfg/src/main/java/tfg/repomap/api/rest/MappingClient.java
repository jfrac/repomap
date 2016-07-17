package tfg.repomap.api.rest;

import javax.ws.rs.core.MediaType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MappingClient {
	private static final String END_POINT = "http://localhost:9999/mappings/-1830399218";

		
	public static void main(String[] args) {
		String mapping = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mapping>    <source xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xmlScheme\">        <url>https://www.w3.org/2001/XMLSchema.xsd</url>    </source>    <target xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"owlScheme\">        <url>http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl</url>    </target>    <entity2EntityMappings>    </entity2EntityMappings>    <pattern2PatternMappings>    </pattern2PatternMappings></mapping>";
		Client client = new Client();
		WebResource resource = client.resource(END_POINT);		
		
		ClientResponse response = resource
				.type(MediaType.APPLICATION_XML)
			    .put(ClientResponse.class, mapping);

		System.out.println("C�digo de retorno: " + response.getStatus());
		//String actividadURL = response.getLocation().toString();
		//System.out.println("Mapping: " + actividadURL);
		System.out.println("Entity: " + response.getEntity(String.class));
	}
}
