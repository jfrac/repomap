package tfg.repomap.mapping;

import tfg.repomap.scheme.Scheme;

public class MappingId {
	
	private String id;
	
	public MappingId() {
		
	}
	
	public MappingId(String id) {
		this();
		this.id = id;
	}
	
	public MappingId(Scheme source, Scheme target) {
		this();
		this.id = generateId(source, target)+"";
	}
	
	/*@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MappingId)) {
			return false;
		}
		MappingId mappingId = (MappingId) obj;
		return id.equals(mappingId.getId());
	}*/
	
	
	
	public String getId() {
		return id;
	}

	public int generateId(Scheme source, Scheme target) {
		final int prime = 31;
		int result = 1;
		result = prime * result + source.hashCode();
		result = prime * result + target.hashCode();
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
		MappingId other = (MappingId) obj;
		String oId = other.getId();
		String tId = this.getId();
		boolean e = oId.equals(tId);
		return e;
	}
}
