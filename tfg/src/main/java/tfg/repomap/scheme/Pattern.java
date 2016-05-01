package tfg.repomap.scheme;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.scheme.pattern.VariableException;

@XmlRootElement
abstract public class Pattern implements Cloneable {
	
	@XmlElement
	private String pattern;
	protected Set<String> variables;
	
	protected Pattern() {
		super();
		variables = new HashSet<String>();
	}
	
	public Pattern(String pattern) {
		this();
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	abstract protected void extractVariables() throws VariableException;
	
	protected void addVariable(String var) {
		variables.add(var);
	}
	
	public Object clone() {
        Object obj = null;
        try{
            obj = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede duplicar");
        }
        return obj;
    }
}
