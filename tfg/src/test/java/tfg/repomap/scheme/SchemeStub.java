package tfg.repomap.scheme;

import java.net.URL;

import tfg.repomap.scheme.entity.Entity;
import tfg.repomap.scheme.pattern.Pattern;
import tfg.repomap.scheme.pattern.VariableException;

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

	@Override
	public Pattern createPattern(String pattern) throws VariableException {
		// TODO Auto-generated method stub
		return null;
	}

}
