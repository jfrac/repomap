package tfg.repomap.scheme.xml;

import java.net.URL;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;

import org.apache.xerces.xs.*;
import org.apache.xerces.impl.xs.XMLSchemaLoader;

public class XMLScheme extends Scheme {

	private Document xml;
	
	public XMLScheme() {
		super();
	}
	
	public XMLScheme(URL schemeURL) {
		super(schemeURL);
	}
	
	public XMLScheme(Document d) {
		super(null);
		this.setXml(d);
	}
	
	@Override
	public void validate(Pattern pattern) {
        /*final Schema SCHEMA_A = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(this.getURL());
        SAXSource source = new SAXSource(new InputSource(new StringReader( pattern.getPattern())));
        Validator validator = SCHEMA_A.newValidator();
        validator.setErrorHandler(new CustomErrorHandler());
        validator.validate(source);
        JAXBContext jc = JAXBContext.newInstance("example.a");
        Unmarshaller u = jc.createUnmarshaller();
        // u.setSchema(SCHEMA_A); // not possible to partially validate using this method
        JAXBElement<SomeType> element = (JAXBElement<SomeType>) u.unmarshal( pattern.getPattern() );
		
		// Comprobamos si existe el elemento 

		// Comprobamos si cumple el esquema
		
		// Extraemos las variables
		 * 
		 */
	}
	
	@Override
	public boolean hasEntity(Entity entity) {
		XMLSchemaLoader loader = new XMLSchemaLoader();
		XSModel model = loader.loadURI(getURL().toString());
		XSNamedMap map = model.getComponents(XSConstants.ELEMENT_DECLARATION);
		for (int j=0; j<map.getLength(); j++) {
		   XSObject o = map.item(j);
		   if (o.getName().equals(entity.getName())) {
			   return true;
		   }
		}

		return false;
	}
	
	protected Document getXml() {
		return xml;
	}
	
	protected void setXml(Document d) {
		this.xml = d;
	}
	
	public String getType() {
		return "xml";
	}

	@Override
	public Pattern createPattern(String pattern) throws VariableException {
		return new XMLPattern(pattern);
	}
}



class CustomErrorHandler implements ErrorHandler {

    public void warning1(SAXParseException exc) throws SAXParseException {
        throw exc;
    }

    public void error1(SAXParseException exc) throws SAXParseException {
        if (exc.getMessage().equals("cvc-complex-type.2.4.c: The matching wildcard is strict, but no declaration can be found for element 'b:person'."))
            ; // suppress
        else
            throw exc;
    }

    public void fatalError1(SAXParseException exc) throws SAXParseException {
        throw exc;
    }

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}
}
