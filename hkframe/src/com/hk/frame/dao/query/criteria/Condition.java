package com.hk.frame.dao.query.criteria;

public class Condition implements Expression {
	private String field;

	private String op;

	private Object value;

	public Condition(String field, String op, Object value) {
		this.field = field;
		this.op = op;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}