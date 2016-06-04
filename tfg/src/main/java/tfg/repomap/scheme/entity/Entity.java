package tfg.repomap.scheme.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entity {
	
	@XmlElement
	private String name;
	
	private List<String> attributes;
	
	protected Entity() {
		this.attributes = new ArrayList<String>();
	}
	
	public Entity(String name) {
		this();
		this.name = name;
	}
	
	public void addAttribute(String attr) {
		this.attributes.add(attr);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Entity other = (Entity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Entity(Entity source) {
		this.name = source.name;
	}

	public String getName() {
		return this.name;
	}
}
