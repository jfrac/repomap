package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.mapping.MappingAlreadyExistsException;

public class CreateMapping {
	public static void main(String[] args) throws MalformedURLException, MappingAlreadyExistsException, MappingControllerException {
		URL xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
		URL owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
		
		MappingControllerImpl controller = new MappingControllerImpl();
		controller.createMapping(xmlSchema, owlSchema);
	}
}
