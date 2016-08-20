package tfg.repomap.scheme.pattern;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import tfg.repomap.scheme.Scheme;

@XmlAccessorType(XmlAccessType.NONE)
abstract public class Pattern implements Cloneable {
	
	@XmlElement
	private String pattern;
	protected Set<String> variables;

	public Pattern() {
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
	
	abstract protected void extractVariables(Scheme scheme) throws VariableException;
	
	public Set<String> getVariables() {
		return variables;
	}
	
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

	abstract public void validate(Scheme scheme) throws VariableException;
}
