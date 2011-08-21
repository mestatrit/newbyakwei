package tuxiazi.svr.exception;

public class TuxiaziException extends Exception {

	private static final long serialVersionUID = -5892215792802632558L;

	public TuxiaziException() {
		super();
	}

	public TuxiaziException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TuxiaziException(String arg0) {
		super(arg0);
	}

	public TuxiaziException(Throwable arg0) {
		super(arg0);
	}
}
