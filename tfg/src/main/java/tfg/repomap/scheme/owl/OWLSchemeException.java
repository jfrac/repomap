package tfg.repomap.scheme.owl;

import javax.xml.ws.WebFault;

import tfg.repomap.scheme.SchemeException;

@SuppressWarnings("serial")
@WebFault
public class OWLSchemeException extends SchemeException {

	public OWLSchemeException(String message) {
		super(message);
	}

	public OWLSchemeException(Throwable cause) {
		super(cause);
	}
	
}
