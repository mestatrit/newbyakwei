package com.hk.frame.dao.query.criteria;

public class TableField implements Expression {
	private String field;

	private Object value;

	private String op;

	public TableField(String field, String op, Object value) {
		if (field == null || field.indexOf('=') != -1
				|| field.indexOf('>') != -1 || field.indexOf('<') != -1
				|| field.indexOf('?') != -1) {
			throw new RuntimeException("field can not contain [ = > < ? ] char");
		}
		this.field = field;
		this.value = value;
		this.op = op;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getOp() {
		if (op.equals("add")) {
			return "=" + this.field + "+";
		}
		return op;
	}
}