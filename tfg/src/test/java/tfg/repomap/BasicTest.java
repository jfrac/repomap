package tfg.repomap;

import java.net.MalformedURLException;
import java.net.URL;

import tfg.repomap.mapping.property2property.Property2PropertyAlreadyExists;
import tfg.repomap.mapping.property2property.Property2PropertyNotValid;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.pattern.OWLPattern;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;

public class BasicTest {
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
			System.out.println("Error al obtener las variables del patrón: " 
					+ e.getMessage());
		} catch (MappingControllerException e) {
			System.out.println("Error al mapear: " + e.getMessage());
		} catch (Property2PropertyAlreadyExists e) {
			System.out.println("El mapeo de propiedades ya existe: " + e.getMessage());
		} catch (Property2PropertyNotValid e) {
			System.out.println("Fallo al validar mapeo de propiedades: " + e.getMessage());
		} catch (MapPattern2PatternException e) {
			System.out.println("Fallo al crear mapeo de patrones: " + e.getMessage());
		}
	}
}
