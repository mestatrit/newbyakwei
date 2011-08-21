package tuxiazi.svr.exception;

public class ImageSizeOutOfLimitException extends TuxiaziException {

	private static final long serialVersionUID = 3106236274485934877L;

	public ImageSizeOutOfLimitException() {
	}

	public ImageSizeOutOfLimitException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ImageSizeOutOfLimitException(String arg0) {
		super(arg0);
	}

	public ImageSizeOutOfLimitException(Throwable arg0) {
		super(arg0);
	}
}
