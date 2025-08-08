package exceptions;

public class IllegalAccountAmountException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public IllegalAccountAmountException() {
		super("Los fondos son insuficientes para esta operacion. ");
	}

}
