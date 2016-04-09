package tfg.repomap.mapping;

import tfg.repomap.scheme.Scheme;

public class MappingId {
	
	private String id;
		
	public MappingId() {
		
	}
	
	public MappingId(Scheme source, Scheme target) {
		int prime = 31;
		String sourceURLString = source.getURL().toString();
		String targetURLString = target.getURL().toString();
		this.id = prime + sourceURLString.hashCode() 
			      + prime + targetURLString.hashCode() + "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MappingId)) {
			return false;
		}
		MappingId mappingId = (MappingId) obj;
		return id.equals(mappingId.getId());
	}
	
	public String getId() {
		return id;
	}
}
