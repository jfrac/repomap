package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.dao.DAOFactory;
import tfg.repomap.dao.MappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.mapping.Entity2EntityExistsException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.Pattern2Pattern;
import tfg.repomap.mapping.Pattern2PatternNotExistsException;
import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Pattern;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingController {
	
	public void mapPattern2Pattern( 
		URL srcScheme,
		Pattern srcPattern,
		URL trgScheme,
		OWLPattern trgPattern
	) throws MapPattern2PatternException {
		try {
			XMLScheme xmlScheme = new XMLScheme(srcScheme);
			xmlScheme.validate(srcPattern);
			
			OWLScheme owlScheme = new OWLScheme(trgScheme);
			owlScheme.validate(trgPattern);
			
			Mapping mapping = this.getMapping(xmlScheme, owlScheme);
			Pattern2Pattern p2p = new Pattern2Pattern(srcPattern, trgPattern);
			
			mapping.addPattern2Pattern(p2p);
			this.saveMapping(mapping);
			
		} catch (MappingDAOException e) {
			throw new MapPattern2PatternException(e);
		} catch (OWLSchemeException e) {
			throw new MapPattern2PatternException(e);
		} catch (Pattern2PatternNotExistsException e) {
			throw new MapPattern2PatternException(e);
		}
	}
	
	public void mapEntity2Entity(
			URL srcScheme,
			Entity srcEntity,
			URL trgScheme,
			Entity trgEntity
	) throws MapEntity2EntityException {
		try {
			Scheme xmlScheme = new XMLScheme(srcScheme);
			OWLScheme owlScheme = new OWLScheme(trgScheme);
			Mapping mapping = this.getMapping(xmlScheme, owlScheme);
			Entity2Entity e2e = new Entity2Entity(srcEntity, trgEntity);
			mapping.addEntity2Entity(e2e);
			this.saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MapEntity2EntityException(e);
		}  catch (Entity2EntityExistsException e) {
			throw new MapEntity2EntityException(e);
		} catch (EntityNotFoundException e) {
			throw new MapEntity2EntityException(e);
		} catch (SchemeException e) {
			throw new MapEntity2EntityException(e);
		}
	}
	
	protected void saveMapping(Mapping mapping) 
			throws MappingDAOException {
		MappingDAO mappingDAO = DAOFactory.getDAO();
		mappingDAO.update(mapping);
	}
	
	protected Mapping getMapping(
			Scheme srcScheme, 
			OWLScheme trgScheme
	) throws MappingDAOException, OWLSchemeException {
		
		
		MappingDAO mappingDAO = DAOFactory.getDAO();
		MappingId mappingId = new MappingId(srcScheme, trgScheme);
		Mapping mapping = mappingDAO.findById(mappingId);
		if (mapping == null) {
			mapping = mappingDAO.create(srcScheme, trgScheme);
		}
		return mapping;
	}
	
	public static void main(String[] args) {
		
		try {
			URL xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
			URL owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
			
			MappingController controller = new MappingController();
			
			Entity xmlElement = new Entity("schema");
			Entity owlClass = new Entity("ModelingLanguage");
			controller.mapEntity2Entity(xmlSchema, xmlElement, owlSchema, owlClass);
			
			Pattern xmlPattern = new XMLPattern(
					"<schema>"
					+ "<atribute use=\"?use\"></atribute>"
					+ "<simpleType>?type</simpleType>"
					+ "</schema>"
			);
			
			String opplScriptString = "";
	        opplScriptString = "?x:CLASS"+"\n";
			opplScriptString += "SELECT "+"\n";
			opplScriptString += "?x SubClassOf molecular_function "+"\n";
			opplScriptString += "WHERE "+"\n";
			opplScriptString += "?x Match(\"hormone receptor binding\")"+"\n";
			opplScriptString += "BEGIN "+"\n";
			opplScriptString += "ADD ?x SubClassOf binding "+"\n";
			opplScriptString += "END;";
			
			OWLPattern oppl2Pattern = new OWLPattern(opplScriptString);
			controller.mapPattern2Pattern(xmlSchema, xmlPattern, owlSchema, oppl2Pattern);
			
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			System.out.println("URL mal formada");
		} catch (MapEntity2EntityException e) {
			System.out.println("Error al añadir el mapeo e2e: " + e.getMessage());
		} catch (MapPattern2PatternException e) {
			System.out.println("Error al añadir el mapeo p2p: " + e.getMessage());
		} catch (VariableException e) {
			System.out.println("Error al obtener las variables del patrón: " + e.getMessage());
		}
	}

}
