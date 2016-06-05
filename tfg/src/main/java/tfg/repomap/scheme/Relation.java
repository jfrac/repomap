package tfg.repomap.scheme;

import javax.xml.bind.annotation.XmlElement;

import tfg.repomap.scheme.entity.Entity;


public class Relation {
	@XmlElement
	private Entity entity1;
	@XmlElement
	private Entity entity2;
	
	protected Relation() {
		super();
	}
	
	public Relation(String entity1, String entity2) {
		this(new Entity(entity1),  new Entity(entity2));
	}
	
	public Relation(Entity entity1, Entity entity2) {
		this();
		this.entity1 = entity1;
		this.entity2 = entity2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity1 == null) ? 0 : entity1.hashCode());
		result = prime * result + ((entity2 == null) ? 0 : entity2.hashCode());
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
		Relation other = (Relation) obj;
		if (entity1 == null) {
			if (other.entity1 != null)
				return false;
		} else if (!entity1.equals(other.entity1))
			return false;
		if (entity2 == null) {
			if (other.entity2 != null)
				return false;
		} else if (!entity2.equals(other.entity2))
			return false;
		return true;
	}

	public boolean validate(Scheme scheme) throws SchemeException {
		return scheme.hasEntity(entity1) && scheme.hasEntity(entity2);
	}
}
