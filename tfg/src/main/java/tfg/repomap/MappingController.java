package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.dao.DAOFactory;
import tfg.repomap.dao.MappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Entity2Entity;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.Pattern2Pattern;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeFactory;
import tfg.repomap.scheme.SchemeFactoryException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.Pattern;
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
			
		} catch (Exception e) {
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
		} catch (Exception e) {
			throw new MapEntity2EntityException(e);
		}
	}
	
	public Mapping createMapping(
			URL srcSchemeURL,
			URL trgSchemeURL
	) throws MappingAlreadyExistsException, MappingControllerException {
		
		try {
			Scheme srcScheme = SchemeFactory.create(srcSchemeURL);
			Scheme trgScheme = SchemeFactory.create(trgSchemeURL);
			
			MappingId mappingId = new MappingId(srcScheme, trgScheme);
			MappingDAO mappingDAO = getDAO();
			if (mappingDAO.findById(mappingId) != null) {
				throw new MappingAlreadyExistsException();
			}
			
			Mapping mapping = new Mapping(srcScheme, trgScheme);
			this.saveMapping(mapping);
			return mapping;
		} catch (SchemeFactoryException e) {
			throw new MappingControllerException(e);
		} catch (MappingDAOException e) {
			throw new MappingControllerException(e);
		}
	}
	
	protected void saveMapping(Mapping mapping) 
			throws MappingDAOException {
		MappingDAO mappingDAO = DAOFactory.getDAO();
		mappingDAO.update(mapping);
	}
	
	protected MappingDAO getDAO()
	{
		return DAOFactory.getDAO();
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
					+ "<atribute ></atribute>"
					+ "<simpleType>?x</simpleType>"
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
			controller.mapPattern2Pattern(
				xmlSchema, 
				xmlPattern, 
				owlSchema, 
				oppl2Pattern
			);
			
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			System.out.println("URL mal formada");
		} catch (MapEntity2EntityException e) {
			System.out.println("Error al a�adir el mapeo e2e: " + e.getMessage());
		} catch (MapPattern2PatternException e) {
			System.out.println("Error al a�adir el mapeo p2p: " + e.getMessage());
		} catch (VariableException e) {
			System.out.println("Error al obtener las variables del patr�n: " 
					+ e.getMessage());
		}
	}

}
