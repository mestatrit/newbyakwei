package cactus.util.oauth.v1_0;

public class Parameter implements Comparable<Parameter> {

	private String name;

	private String value;

	public Parameter(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(Parameter o) {
		int res = this.name.compareTo(o.getName());
		if (res == 0) {
			res = this.value.compareTo(o.getValue());
		}
		return res;
	}
}