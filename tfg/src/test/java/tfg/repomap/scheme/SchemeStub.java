package tfg.repomap.scheme;

import java.net.URL;

public class SchemeStub extends Scheme {

	public SchemeStub(URL url) {
		super(url);
	}

	@Override
	public boolean hasEntity(Entity entity) 
			throws SchemeException {
		return true;
	}

}
