package cactus.tools.oauth.v1_0.exception;

public class RequestTokenException extends Exception {

	private static final long serialVersionUID = -895888374193179398L;

	public RequestTokenException() {
		super();
	}

	public RequestTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTokenException(String message) {
		super(message);
	}

	public RequestTokenException(Throwable cause) {
		super(cause);
	}
}