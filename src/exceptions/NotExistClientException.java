package exceptions;

public class NotExistClientException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotExistClientException() {
		super("No existe el cliente en la base de datos. ");
	}
}
