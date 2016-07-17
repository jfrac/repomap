package tfg.repomap;

import javax.xml.ws.WebFault;

@SuppressWarnings("serial")
@WebFault
public class MappingControllerException extends Exception {

	public MappingControllerException(Throwable e) {
		super(e);
	}

	public MappingControllerException() {
		// TODO Auto-generated constructor stub
	}

}
