package tfg.repomap;

import java.net.URL;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingBuilder {
	public static Mapping newMapping() {
		URL xmlSchema;
		URL owlSchema;
		try {
			xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
			owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
			
			Scheme sourceScheme = new XMLScheme(xmlSchema);
			Scheme targetScheme = new OWLScheme(owlSchema);

			Mapping mapping = new Mapping(sourceScheme, targetScheme);
			return mapping;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
}
