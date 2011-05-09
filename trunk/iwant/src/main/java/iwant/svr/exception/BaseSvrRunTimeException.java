package iwant.svr.exception;

public class BaseSvrRunTimeException extends RuntimeException {

	private static final long serialVersionUID = 3969621272823083601L;

	public BaseSvrRunTimeException() {
		super();
	}

	public BaseSvrRunTimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public BaseSvrRunTimeException(String message) {
		super(message);
	}

	public BaseSvrRunTimeException(Throwable throwable) {
		super(throwable);
	}
}
