package tfg.repomap.scheme.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;
import tfg.repomap.scheme.pattern.XMLPattern;

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
       
	}
	
	@Override
	public boolean hasEntity(Entity entity) throws SchemeException {
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			InputStream is = getURL().openStream();
			Document doc = dBuilder.parse(is);
			NodeList nodes = doc.getElementsByTagName("xs:element");
	        for (int i = 0; i < nodes.getLength(); i++){
	            Element element = (Element) nodes.item(i);
	            if (element.getAttribute("name").equals(entity.getName())) {
	            	return true;
	            }
	        }
		} catch (Exception e) {
			throw new SchemeException(e);
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

	@Override
	public boolean hasAttribute(Entity entity, String attribute) 
		throws EntityNotFoundException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
			doc = dBuilder.parse(getURL().openStream());
			NodeList nodes = doc.getElementsByTagName("xs:element");
	        for (int i = 0; i < nodes.getLength(); i++){
	            Element element = (Element) nodes.item(i);
	            if (element.getAttribute("name").equals(entity.getName())) {
	            	NodeList childNodes = element.getElementsByTagName("xs:attribute");
	            	for (int j = 0; j < childNodes.getLength(); j++) {
	            		element = (Element) childNodes.item(j);
	            		if (element.getAttribute("name").equals(attribute)) {
	            			return true;
	            		}
					}
	            }
	        }
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new EntityNotFoundException(entity.getName());
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
