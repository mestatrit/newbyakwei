package halo.dataserver.input;

public class InputParseException extends Exception {

	private static final long serialVersionUID = -6892847285472869126L;

	public InputParseException() {
		super();
	}

	public InputParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InputParseException(String arg0) {
		super(arg0);
	}

	public InputParseException(Throwable arg0) {
		super(arg0);
	}
}
