package ec.edu.luisrogerio.common;

public class AppException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AppException(String message) {
		super(message);
	}
}
