package tfg.repomap.scheme;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pattern {
	
	@XmlElement
	private String pattern;
	
	public Pattern(Pattern pattern) {
		this.pattern = pattern.getPattern();
	}
	
	protected Pattern() {
	}
	
	public Pattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}
}
