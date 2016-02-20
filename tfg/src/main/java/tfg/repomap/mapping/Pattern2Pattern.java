package tfg.repomap.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.scheme.Pattern;

@XmlRootElement
public class Pattern2Pattern {
	
	@XmlElement
	private Pattern source;
	
	@XmlElement
	private Pattern target;
	
	public Pattern2Pattern(Pattern source, Pattern target) {
		this.source = source;
		this.target = target;
	}
	
	protected Pattern2Pattern() {
		
	}
	
	public Pattern getSource() {
		return new Pattern(source);
	}
	
	public Pattern getTarget() {
		return new Pattern(target);
	}
}
