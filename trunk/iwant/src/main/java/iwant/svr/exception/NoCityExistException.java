package iwant.svr.exception;

public class NoCityExistException extends BaseSvrRunTimeException {

	private static final long serialVersionUID = -8060126228122887017L;

	public NoCityExistException() {
		super();
	}

	public NoCityExistException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoCityExistException(String message) {
		super(message);
	}

	public NoCityExistException(Throwable throwable) {
		super(throwable);
	}
}
