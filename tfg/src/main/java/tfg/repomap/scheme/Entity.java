package tfg.repomap.scheme;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entity {
	
	@XmlElement
	private String name;
	
	public Entity(String name) {
		this.name = name;
	}
	
	public Entity() {
		
	}
	
	public String getName() {
		return this.name;
	}
}
