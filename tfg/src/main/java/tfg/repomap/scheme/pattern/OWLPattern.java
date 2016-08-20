package tfg.repomap.scheme.pattern;

import java.util.Arrays;
import java.util.List;

import org.coode.oppl.AnnotationBasedSymbolTableFactory;
import org.coode.oppl.OPPLParser;
import org.coode.oppl.OPPLScript;
import org.coode.oppl.ParserFactory;
import org.coode.oppl.Variable;
import org.coode.parsers.common.SystemErrorEcho;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import tfg.repomap.scheme.Scheme;

public class OWLPattern extends Pattern {

	protected OWLPattern() {}
	
	public OWLPattern(String pattern) throws VariableException {
		super(pattern);
	}	
	
	@Override
	protected void extractVariables(Scheme scheme) throws VariableException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology;
		try {
			ontology = manager.loadOntology(IRI.create(scheme.getURL().toString()));
			// Create an annotation based symbol table factory for making reference in the OPPL script to 
			// object rdf:labels
			OWLAnnotationProperty rdfLabel = ontology.getOWLOntologyManager().getOWLDataFactory().getRDFSLabel();
			AnnotationBasedSymbolTableFactory abstf = new AnnotationBasedSymbolTableFactory(ontology.getOWLOntologyManager(), Arrays.asList(rdfLabel.getIRI()));
			        
			// Create a parser factory that use rdf:labels
			ParserFactory parserFactory = new ParserFactory(ontology.getOWLOntologyManager(), ontology, null);
			
			OPPLParser parser;
			/*if(errorListener != null ) {
				parser = parserFactory.build(errorListener, abstf);
			}
			else {*/
				parser = parserFactory.build(new SystemErrorEcho(), abstf);
			//}
			
			// Parse the script
			OPPLScript parsed = parser.parse(getPattern());
			List<Variable<?>> variables = parsed.getVariables();
			for ( Variable<?> v : variables ) {
				//System.out.println("Variable "+v.getName());
				addVariable(v.getName());
			}
		} catch (OWLOntologyCreationException e) {
			throw new VariableException(e);
		}
	}
	
	public void validate(Scheme scheme) throws VariableException {
		extractVariables(scheme);
	}
}
