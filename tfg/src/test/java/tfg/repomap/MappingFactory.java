package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.mapping.Mapping;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingFactory {
	public static Mapping newMapping()
		throws MalformedURLException, OWLSchemeException {
		
		URL xmlSchema;
		URL owlSchema;
	
		xmlSchema = new URL("http://sele.inf.um.es/swit/zinc/molecule.xsd");
		owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
		
		Scheme sourceScheme = new XMLScheme(xmlSchema);
		Scheme targetScheme = new OWLScheme(owlSchema);

		Mapping mapping = new Mapping(sourceScheme, targetScheme);
		return mapping;
	}
}
