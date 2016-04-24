package tfg.repomap.scheme;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
abstract public class Pattern implements Cloneable {
	
	@XmlElement
	private String pattern;
	
	protected Pattern() {}
	
	public Pattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
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
