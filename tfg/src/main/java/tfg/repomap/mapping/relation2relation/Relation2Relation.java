package tfg.repomap.mapping.relation2relation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tfg.repomap.scheme.Relation;

@XmlRootElement
public class Relation2Relation {
	@XmlElement
	private Relation relationSource;
	@XmlElement
	private Relation relationTarget;
	
	protected Relation2Relation() {
		super();
	}
	
	public Relation2Relation(Relation relationSource, Relation relationTarget) {
		this();
		this.relationSource = relationSource;
		this.relationTarget = relationTarget;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relationSource == null) ? 0 : relationSource.hashCode());
		result = prime * result + ((relationTarget == null) ? 0 : relationTarget.hashCode());
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
		Relation2Relation other = (Relation2Relation) obj;
		if (relationSource == null) {
			if (other.relationSource != null)
				return false;
		} else if (!relationSource.equals(other.relationSource))
			return false;
		if (relationTarget == null) {
			if (other.relationTarget != null)
				return false;
		} else if (!relationTarget.equals(other.relationTarget))
			return false;
		return true;
	}
	
}
