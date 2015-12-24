package tfg.repomap.scheme.mapping;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.scheme.Entity;

@XmlRootElement
public class Entity2Entity {
	@XmlElement
	private Entity source;
	@XmlElement
	private Entity target;
	
	public Entity2Entity(Entity source, Entity target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	public Entity2Entity() {
		
	}
}
