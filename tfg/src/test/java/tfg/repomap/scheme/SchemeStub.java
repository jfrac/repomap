package tfg.repomap.scheme;

import java.net.URL;

import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.pattern.Pattern;

public class SchemeStub extends Scheme {

	public SchemeStub(URL url) {
		super(url);
	}

	@Override
	public boolean hasEntity(Entity entity) 
			throws SchemeException {
		return true;
	}

	@Override
	public void validate(Pattern pattern) {
		// TODO Auto-generated method stub
		
	}

}
