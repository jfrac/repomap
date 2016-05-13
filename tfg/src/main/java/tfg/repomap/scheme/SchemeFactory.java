package tfg.repomap.scheme;

import java.net.URL;

import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.xml.XMLScheme;

public class SchemeFactory {
	public static Scheme create(URL schemeURL) 
			throws SchemeFactoryException {
		String stringURL = schemeURL.toString();
		
		if (stringURL.endsWith("xsd")) {
			return new XMLScheme(schemeURL);
		} else if (stringURL.endsWith("owl")) {
			try {
				return new OWLScheme(schemeURL);
			} catch (OWLSchemeException e) {
				throw new SchemeFactoryException(e);
			} 
		} else {
			throw new SchemeFactoryException("Scheme url not found");
		}
	}
}
