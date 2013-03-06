package exceptions;

public class UnrecognizedTagException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public String name;

	public UnrecognizedTagException(String name) {
		this.name = name;
	}
}
