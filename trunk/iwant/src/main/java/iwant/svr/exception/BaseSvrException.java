package iwant.svr.exception;

public abstract class BaseSvrException extends Exception {

	private static final long serialVersionUID = -291635526837458546L;

	public BaseSvrException() {
		super();
	}

	public BaseSvrException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BaseSvrException(String arg0) {
		super(arg0);
	}

	public BaseSvrException(Throwable arg0) {
		super(arg0);
	}
}