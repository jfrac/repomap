package tfg.repomap.mapping.property2property;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonValue;

import tfg.repomap.scheme.Scheme;
import tfg.repomap.scheme.SchemeException;
import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.entity.EntityNotFoundException;

@XmlRootElement
public class Property2Property {
	
	@XmlElement(name="srcEntity")
	private Entity sourceEntity;
	@XmlElement
	private String sourceAttribute;
	@XmlElement(name="trgEntity")
	private Entity targetEntity;
	@XmlElement
	private String targetAttribute;
	
	protected Property2Property() {
		super();
	}
	
	public Property2Property(
		Entity sourceEntity, 
		String sourceAttribute, 
		Entity targetEntity, 
		String targetAttribute
	) {
		this();
		this.sourceEntity = sourceEntity;
		this.sourceAttribute = sourceAttribute;
		this.targetEntity = targetEntity;
		this.targetAttribute = targetAttribute;
	}
	
	@JsonValue
	public String getSourceEntityName() {
		return sourceEntity.getName();
	}
	
	@JsonValue
	public String getTargetEntityName() {
		return sourceEntity.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sourceAttribute == null) ? 0 : sourceAttribute.hashCode());
		result = prime * result + ((sourceEntity == null) ? 0 : sourceEntity.hashCode());
		result = prime * result + ((targetAttribute == null) ? 0 : targetAttribute.hashCode());
		result = prime * result + ((targetEntity == null) ? 0 : targetEntity.hashCode());
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
		Property2Property other = (Property2Property) obj;
		if (sourceAttribute == null) {
			if (other.sourceAttribute != null)
				return false;
		} else if (!sourceAttribute.equals(other.sourceAttribute))
			return false;
		if (sourceEntity == null) {
			if (other.sourceEntity != null)
				return false;
		} else if (!sourceEntity.equals(other.sourceEntity))
			return false;
		if (targetAttribute == null) {
			if (other.targetAttribute != null)
				return false;
		} else if (!targetAttribute.equals(other.targetAttribute))
			return false;
		if (targetEntity == null) {
			if (other.targetEntity != null)
				return false;
		} else if (!targetEntity.equals(other.targetEntity))
			return false;
		return true;
	}

	public boolean validate(Scheme source, Scheme target) 
			throws EntityNotFoundException, SchemeException {
		return source.hasAttribute(sourceEntity, sourceAttribute) 
				&& target.hasAttribute(targetEntity, targetAttribute);
	}
}
