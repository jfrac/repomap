package tfg.repomap.scheme.entity;

@SuppressWarnings("serial")
public class EntityNotFoundException extends Exception {

	public EntityNotFoundException(String msg) {
		super(msg);
	}

	public EntityNotFoundException() {
		super();
	}

}
