package tfg.repomap.scheme.owl;

import java.net.URISyntaxException;
import java.net.URL;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;

public class OWLScheme extends Scheme {

	private transient OWLOntology owlScheme;
	
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
	
	@Override
	public boolean hasAttribute(Entity entity, String sourceAttribute)
		throws EntityNotFoundException, SchemeException {
		
		if (!hasEntity(entity)) {
			throw new EntityNotFoundException(entity.getName());
		}
		
		return true;
	}
	
	protected OWLOntology getScheme() throws OWLSchemeException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		try {
			owlScheme = manager.loadOntologyFromOntologyDocument(IRI.create(this.getURL()));
			return owlScheme;
		} catch (OWLOntologyCreationException e) {
			throw new OWLSchemeException(e);
		} catch (URISyntaxException e) {
			throw new OWLSchemeException(e);
		}
	}
	
	public String getType() {
		return "owl";
	}

	@Override
	public void validate(Pattern pattern) throws VariableException {
		pattern.validate(this);
	}

	@Override
	public Pattern createPattern(String pattern) throws VariableException {
		return new OWLPattern(pattern);
	}
}
