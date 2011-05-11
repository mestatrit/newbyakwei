package iwant.svr.exception;

public class PptNotFoundException extends BaseSvrException {

	private static final long serialVersionUID = 8092995589287068149L;

	public PptNotFoundException() {
		super();
	}

	public PptNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PptNotFoundException(String arg0) {
		super(arg0);
	}

	public PptNotFoundException(Throwable arg0) {
		super(arg0);
	}
}