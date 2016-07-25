package tfg.repomap.api.rest;

import java.io.Serializable;

public class NotFoundException 
	extends Exception
	implements Serializable {

	public NotFoundException(Throwable e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
