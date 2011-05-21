package cactus.tools.oauth.v1_0.exception;

public class AccessTokenException extends Exception {

	private static final long serialVersionUID = -895888374193179398L;

	public AccessTokenException() {
		super();
	}

	public AccessTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessTokenException(String message) {
		super(message);
	}

	public AccessTokenException(Throwable cause) {
		super(cause);
	}
}