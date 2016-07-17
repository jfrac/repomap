package tfg.repomap.scheme;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault
public class SchemeException extends Exception {
	
	public SchemeException(String message) {
		super(message);
	}

	public SchemeException(Throwable cause) {
		super(cause);
	}
	
}
