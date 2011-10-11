package halo.web.validate;

public class ErrorResult {

	private String name;

	private Object message;

	public ErrorResult(String name, Object message) {
		super();
		this.name = name;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public Object getMessage() {
		return message;
	}
}