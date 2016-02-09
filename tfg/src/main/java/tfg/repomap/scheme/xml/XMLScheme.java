package tfg.repomap.scheme.xml;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Scheme;

import org.apache.xerces.util.*;
import org.apache.xerces.xs.*;
import org.apache.xerces.impl.xs.XMLSchemaLoader;

public class XMLScheme extends Scheme {

	private Document xml;
	
	public XMLScheme() {
		
	}
	
	public XMLScheme(URL schemeURL) {
		super(schemeURL);
	}
	
	public XMLScheme(Document d) {
		super(null);
		this.setXml(d);
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
		   //System.out.println("{"+o.getNamespace()+"}"+o.getName());
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
}
