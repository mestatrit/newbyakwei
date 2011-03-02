package com.hk.frame.dao.query.criteria;

public interface Expression {
	String getField();

	String getOp();

	Object getValue();

	void setValue(Object value);
}