package tfg.repomap.scheme.owl;

import java.net.URISyntaxException;
import java.net.URL;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Scheme;

public class OWLScheme extends Scheme {

	private OWLOntology owlScheme;
	
	public OWLScheme(URL schemeURL) throws OWLSchemeException {
		super(schemeURL);
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			this.owlScheme = manager.loadOntologyFromOntologyDocument(IRI.create(schemeURL));
		} catch (OWLOntologyCreationException e) {
			throw new OWLSchemeException();
		} catch (URISyntaxException e) {
			throw new OWLSchemeException();
		}
	}

	public OWLScheme() {
		
	}
	
	@Override
	public boolean hasEntity(Entity entity) {
		for (OWLClass cls : this.owlScheme.getClassesInSignature()) {
			if (cls.getIRI().getFragment().equals(entity.getName())) {
				return true;
			}
		}
        return false;
	}
	
	public String getType() {
		return "owl";
	}
}
