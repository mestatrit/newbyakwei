package iwant.svr.exception;

public class FollowProjectAlreadyExistException extends BaseSvrException {

	private static final long serialVersionUID = -2449946043626128755L;

	public FollowProjectAlreadyExistException() {
		super();
	}

	public FollowProjectAlreadyExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FollowProjectAlreadyExistException(String arg0) {
		super(arg0);
	}

	public FollowProjectAlreadyExistException(Throwable arg0) {
		super(arg0);
	}
}