package tfg.repomap.scheme.pattern;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tfg.repomap.scheme.Scheme;

public class XMLPattern extends Pattern {
	
	public XMLPattern(String pattern) throws VariableException {
		super(pattern);
	}
	
	protected XMLPattern() {}
	
	protected void extractVariables(Scheme scheme) throws VariableException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			String pattern = getPattern();
			Document doc = dBuilder.parse(
					new ByteArrayInputStream(
							pattern.getBytes(StandardCharsets.UTF_8)
			));
		
			printChildenRecursive(doc);
		} catch (Exception e) {
			throw new VariableException(e);
		}
		
	}
	
	protected void printChildenRecursive(Node doc) 
	{
		NodeList nodelist = doc.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			this.findAndSaveVariable(node);
			if (node.hasAttributes()) {
				NamedNodeMap attrs = node.getAttributes();
				for (int j = 0; j < attrs.getLength(); j++) {
					this.findAndSaveVariable(attrs.item(j));
				}
			}
			if (node.hasChildNodes()) {
				printChildenRecursive(nodelist.item(i));
			}
		}
	}
	
	protected void findAndSaveVariable(Node n) {
		if (n.getNodeValue() != null
			&& n.getNodeValue().startsWith("?")
		) {
			addVariable(n.getNodeValue());
		}
	}
	
	public Set<String> getVariables() {
		return new HashSet<String>(variables);
	}

	@Override
	public void validate(Scheme scheme) throws VariableException {
		extractVariables(scheme);
	}
}
