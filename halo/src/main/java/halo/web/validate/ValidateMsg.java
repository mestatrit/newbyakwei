package halo.web.validate;

public class ValidateMsg {

	private String name;

	private Object message;

	public ValidateMsg(String name, Object message) {
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