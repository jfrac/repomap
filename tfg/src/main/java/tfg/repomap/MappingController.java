package tfg.repomap;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Mapping;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingController {
	
	public void mapEntity2Entity(
			URL srcScheme,
			Entity srcEntity,
			URL trgScheme,
			Entity trgEntity
	) throws EntityNotFoundException, OWLOntologyCreationException, URISyntaxException {
		XMLScheme xmlScheme = new XMLScheme(srcScheme);
		OWLScheme owlScheme = new OWLScheme(trgScheme);
		
		if (!xmlScheme.hasEntity(srcEntity)) {
			throw new EntityNotFoundException();
		}
		
		if (!owlScheme.hasEntity(trgEntity)) {
			throw new EntityNotFoundException();
		}
		
		try {
			Mapping.createMapping(xmlScheme, srcEntity, owlScheme, trgEntity);
			
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			URL xmlSchema = new URL("http://miuras.inf.um.es/ontologies/eco_punned.owl");
			Entity xmlElement = new Entity("head");
			URL owlSchema = new URL("http://miuras.inf.um.es/ontologies/eco_punned.owl");
			Entity owlClass = new Entity("ECO_0000105");
			MappingController controller = new MappingController();
			controller.mapEntity2Entity(xmlSchema, xmlElement, owlSchema, owlClass);
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}		
	}

}
