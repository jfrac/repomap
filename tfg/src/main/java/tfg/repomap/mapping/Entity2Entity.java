package tfg.repomap.mapping;

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
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	public boolean equals(Entity2Entity obj) {
		return obj.getSource().equals(getSource())
			   && obj.getTarget().equals(getTarget());
	}
	
	public Entity getSource() {
		return new Entity(source);
	}
	
	public Entity getTarget() {
		return new Entity(target);
	}
}
