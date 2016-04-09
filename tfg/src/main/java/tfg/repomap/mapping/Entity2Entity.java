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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity2Entity other = (Entity2Entity) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	public Entity getSource() {
		return new Entity(source);
	}
	
	public Entity getTarget() {
		return new Entity(target);
	}
}
