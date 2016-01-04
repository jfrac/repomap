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
	}

	public OWLScheme() {
		super();
	}
	
	@Override
	public boolean hasEntity(Entity entity) throws OWLSchemeException {
		for (OWLClass cls : getScheme().getClassesInSignature()) {
			if (cls.getIRI().getFragment().equals(entity.getName())) {
				return true;
			}
		}
        return false;
	}
	
	protected OWLOntology getScheme() throws OWLSchemeException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			owlScheme = manager.loadOntologyFromOntologyDocument(IRI.create(this.getURL()));
			return owlScheme;
		} catch (OWLOntologyCreationException e) {
			throw new OWLSchemeException();
		} catch (URISyntaxException e) {
			throw new OWLSchemeException();
		}
	}
	
	public String getType() {
		return "owl";
	}
}
