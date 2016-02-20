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
	
	protected Entity() {
		
	}
	
	public Entity(Entity source) {
		this.name = source.name;
	}

	public String getName() {
		return this.name;
	}
}
