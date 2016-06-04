package tfg.repomap.mapping.pattern2pattern;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.scheme.pattern.Pattern;

@XmlRootElement
public class Pattern2Pattern {
	
	@XmlElement
	private Pattern source;
	
	@XmlElement
	private Pattern target;
	
	protected Pattern2Pattern() {}
	
	public Pattern2Pattern(Pattern source, Pattern target) 
			throws Pattern2PatternNotSameVariables {
		this.validatePattern2Pattern(source, target);
		this.source = source;
		this.target = target;
	}
	
	protected void validatePattern2Pattern(Pattern source, Pattern target) 
			throws Pattern2PatternNotSameVariables {
		Set<String> variablesSource = source.getVariables();
		Set<String> variablesTarget = target.getVariables();
		if (!variablesSource.equals(variablesTarget)) {
			throw new Pattern2PatternNotSameVariables();
		}
	}
	
	public Pattern getSource() {
		return (Pattern)source.clone();
	}
	
	public Pattern getTarget() {
		return (Pattern)target.clone();
	}
}
