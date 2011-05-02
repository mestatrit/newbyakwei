package cactus.web.taglib;

import java.util.List;

public class PropertyParam {

	private Object name;

	private Object value;

	private boolean notEmpty;

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public PropertyParam(Object name, Object value) {
		this.setName(name);
		this.setValue(value);
	}

	public void setValue(Object value) {
		this.value = value;
		if (value != null && !value.toString().equals("")) {
			this.notEmpty = true;
		}
	}

	public void setNotEmpty(boolean notEmpty) {
		this.notEmpty = notEmpty;
	}

	public boolean isNotEmpty() {
		return notEmpty;
	}

	public static String renderProperty(List<PropertyParam> list) {
		StringBuilder sb = new StringBuilder();
		for (PropertyParam param : list) {
			if (param.isNotEmpty()) {
				sb.append(" ");
				sb.append(param.getName().toString());
				sb.append("=\"").append(param.getValue().toString()).append(
						"\"");
			}
			else {
				sb.append(" ");
				sb.append(param.getName().toString());
				sb.append("=\"\"");
			}
		}
		return sb.toString();
	}
}