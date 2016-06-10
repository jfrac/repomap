package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;
import tfg.repomap.dao.DAOFactory;
import tfg.repomap.dao.MappingDAO;
import tfg.repomap.dao.MappingDAOException;
import tfg.repomap.mapping.Mapping;
import tfg.repomap.mapping.MappingAlreadyExistsException;
import tfg.repomap.mapping.MappingCouldNotDelete;
import tfg.repomap.mapping.MappingId;
import tfg.repomap.mapping.MappingNotExists;
import tfg.repomap.mapping.entity2entity.Entity2Entity;
import tfg.repomap.mapping.entity2entity.Entity2EntityExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2Pattern;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternAlreadyExistsException;
import tfg.repomap.mapping.pattern2pattern.Pattern2PatternNotSameVariables;
import tfg.repomap.mapping.property2property.Property2Property;
import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.mapping.property2property.Property2PropertyNotValid;
import tfg.repomap.mapping.relation2relation.Relation2Relation;
import tfg.repomap.mapping.relation2relation.Relation2RelationAlreadyExists;
import tfg.repomap.scheme.Relation;
import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.SchemeFactory;
import tfg.repomap.scheme.SchemeFactoryException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.owl.OWLScheme;
import tfg.repomap.scheme.owl.OWLSchemeException;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;
import tfg.repomap.scheme.xml.XMLScheme;

public class MappingControllerImpl {
	
	public void mapRelations(
			URL srcScheme, 
			String srcEntity, 
			String srcEntity2, 
			URL trgScheme, 
			String trgEntity, 
			String trgEntity2
		) throws MappingControllerException, Relation2RelationAlreadyExists, EntityNotFoundException {
			try {
				MappingId mappingId = new MappingId(srcScheme, trgScheme);
				Mapping mapping = this.findOrCreateMapping(mappingId);
				Relation relationSource = new Relation(srcEntity, srcEntity2);
				Relation relationTarget = new Relation(trgEntity, trgEntity2);
				Relation2Relation r2r = new Relation2Relation(
					relationSource,
					relationTarget
				);
				mapping.addRelation2Relation(r2r);
			} catch (MappingDAOException e) {
				throw new MappingControllerException(e);
			} catch (MalformedURLException e) {
				throw new MappingControllerException(e);
			} catch (SchemeException | SchemeFactoryException e) {
				throw new MappingControllerException(e);
			}
		}
	
	public void mapProperties(
		URL srcScheme, 
		String srcEntity, 
		String srcAttribute, 
		URL trgScheme, 
		String trgEntity, 
		String trgAttribute
	) throws MappingControllerException, Property2PropertyAlreadyExists, Property2PropertyNotValid {
		try {
			MappingId mappingId = new MappingId(srcScheme, trgScheme);
			this.mapProperties(mappingId, srcEntity, srcAttribute, trgEntity, trgAttribute);
		} catch (MalformedURLException e) {
			throw new MappingControllerException(e);
		} catch (SchemeFactoryException e) {
			throw new MappingControllerException(e);
		}
	}
	
	public void mapProperties(
		MappingId mappingId, 
		String sourceEntity, 
		String sourceAttribute, 
		String targetEntity,
		String targetAttribute
	) throws Property2PropertyAlreadyExists, Property2PropertyNotValid, MappingControllerException {
		try {
			Mapping mapping;
			mapping = this.findOrCreateMapping(mappingId);
			Property2Property p2p = new Property2Property(
				new Entity(sourceEntity),
				sourceAttribute,
				new Entity(targetEntity),
				targetAttribute
			);
			mapping.addProperty2Property(p2p);
		
			saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MappingControllerException(e);
		}
	}
	
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
	
	public void mapEntity2Entity(
			MappingId mappingId,
			String srcEntity,
			String trgEntity
	) throws MapEntity2EntityException, 
		     MappingNotExists, 
		     EntityNotFoundException, 
		     Entity2EntityExistsException
	{
		try {
			MappingDAO dao = getDAO();
			Mapping mapping;
			mapping = dao.findById(mappingId);
			if (mapping == null) {
				throw new MappingNotExists();
			}
			
			mapping.addEntity2Entity(srcEntity, trgEntity);
			this.saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MapEntity2EntityException(e);
		} catch (SchemeException e) {
			throw new MapEntity2EntityException(e);
		} 
	}
	
	public void mapPattern2Pattern(
		MappingId mappingId, 
		String patternSource, 
		String patternTarget
	) throws MappingNotExists, 
	         Pattern2PatternNotSameVariables, 
	         VariableException, 
	         Pattern2PatternAlreadyExistsException, 
	         MapEntity2EntityException
    {
		try {
			MappingDAO dao = getDAO();
			Mapping mapping;
			mapping = dao.findById(mappingId);
			if (mapping == null) {
				throw new MappingNotExists();
			}
			
			mapping.addPattern2Pattern(patternSource, patternTarget);
			this.saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MapEntity2EntityException(e);
		} 
	}
	
	public Mapping createMapping(
			URL srcSchemeURL,
			URL trgSchemeURL
	) throws MappingAlreadyExistsException, 
			 MappingControllerException 
	{
		
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
	
	public void updateMapping(Mapping mapping) 
		throws MappingControllerException, 
		MappingNotExists
	{
		if (getMapping(mapping.getId()) == null) {
			throw new MappingNotExists();
		}
		try {
			saveMapping(mapping);
		} catch (MappingDAOException e) {
			throw new MappingControllerException(e);
		}
	}
	
	protected void saveMapping(Mapping mapping) 
			throws MappingDAOException {
		MappingDAO mappingDAO = DAOFactory.getDAO();
		mappingDAO.update(mapping);
	}
	
	public Mapping getMapping(MappingId mappingId) 
		throws MappingControllerException 
	{
		MappingDAO mappingDAO = getDAO();
		try {
			return mappingDAO.findById(mappingId);
		} catch (MappingDAOException e) {
			throw new MappingControllerException(e);
		}
	}
	
	protected Mapping findOrCreateMapping(MappingId mappingId) 
			throws MappingControllerException, MappingDAOException {
		Mapping mapping = this.getMapping(mappingId);
		if (mapping == null) {
			mapping = new Mapping(mappingId);
			this.saveMapping(mapping);
		}
		return mapping;
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
	
	public void deleteMapping(MappingId mappingId)
		throws MappingCouldNotDelete {
		if (!getDAO().remove(mappingId)) {
			throw new MappingCouldNotDelete();
		}
	}
	
	protected MappingDAO getDAO()
	{
		return DAOFactory.getDAO();
	}
	
	public static void main(String[] args) {
		
		try {
			URL xmlSchema = new URL("https://www.w3.org/2001/XMLSchema.xsd");
			URL owlSchema = new URL("http://www.cs.toronto.edu/~yuana/research/maponto/ka.owl");
			
			MappingControllerImpl controller = new MappingControllerImpl();
			
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
			
			controller.mapProperties(
				xmlSchema, 
				"complexType", 
				"name", 
				owlSchema, 
				"Researcher", 
				"authorOfOntology"
			);
			
			System.out.println("Mapping generated!");
		} catch (MalformedURLException e) {
			System.out.println("URL mal formada");
		} catch (MapEntity2EntityException e) {
			System.out.println("Error al a�adir el mapeo e2e: " + e.getMessage());
		} catch (VariableException e) {
			System.out.println("Error al obtener las variables del patr�n: " 
					+ e.getMessage());
		} catch (MappingControllerException e) {
			System.out.println("Error al mapear: " + e.getMessage());
		} catch (Property2PropertyAlreadyExists e) {
			System.out.println("El mapeo de propiedades ya existe: " + e.getMessage());
		} catch (Property2PropertyNotValid e) {
			System.out.println("Fallo al validar mapeo de propiedades: " + e.getMessage());
		} catch (MapPattern2PatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
