package tfg.repomap.scheme.xml;

import java.net.URL;

import org.w3c.dom.Document;

import tfg.repomap.scheme.Entity;
import tfg.repomap.scheme.Scheme;

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
		// TODO Auto-generated method stub
		return true;
	}
	
	protected Document getXml() {
		return xml;
	}
	
	protected void setXml(Document d) {
		this.xml = d;
	}
}
