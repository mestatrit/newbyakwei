package iwant.svr.exception;

public class CityNotFoundException extends BaseSvrRunTimeException {

	private static final long serialVersionUID = -8060126228122887017L;

	public CityNotFoundException() {
		super();
	}

	public CityNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public CityNotFoundException(String message) {
		super(message);
	}

	public CityNotFoundException(Throwable throwable) {
		super(throwable);
	}
}
