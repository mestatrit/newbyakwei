package iwant.bean.exception;

public class NoAdminUserException extends Exception {

	private static final long serialVersionUID = -410932908689285282L;

	public NoAdminUserException() {
		super();
	}

	public NoAdminUserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NoAdminUserException(String arg0) {
		super(arg0);
	}

	public NoAdminUserException(Throwable arg0) {
		super(arg0);
	}
}