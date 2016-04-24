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
	
	protected Pattern2Pattern() {}
	
	public Pattern2Pattern(Pattern source, Pattern target) {
		this.validatePattern2Pattern(source, target);
		this.source = source;
		this.target = target;
	}
	
	protected void validatePattern2Pattern(Pattern source, Pattern target) {
		// TODO validamos que las variables del mapping target 
		// existen en el source.
	}
	
	public Pattern getSource() {
		return (Pattern)source.clone();
	}
	
	public Pattern getTarget() {
		return (Pattern)target.clone();
	}
}
