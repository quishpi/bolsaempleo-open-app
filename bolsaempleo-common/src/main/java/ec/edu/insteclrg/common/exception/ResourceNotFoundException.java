package ec.edu.insteclrg.common.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String object) {
		super(String.format("%s no se encuentra registrado(a) o su estatus es inválido para esta acción", object));
	}

}