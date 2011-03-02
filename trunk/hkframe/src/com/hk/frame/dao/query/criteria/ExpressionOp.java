package com.hk.frame.dao.query.criteria;

public class ExpressionOp {
	private ExpressionOp() {//
	}

	/**
	 * =
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression eq(String field, Object value) {
		return new Condition(field, "=", value);
	}

	public static Expression notEq(String field, Object value) {
		return new Condition(field, "!=", value);
	}

	/**
	 * &gt;
	 * 
	 * @return
	 */
	public static Expression gt(String field, Object value) {
		return new Condition(field, ">", value);
	}

	/**
	 * &gt;=
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression ge(String field, Object value) {
		return new Condition(field, ">=", value);
	}

	/**
	 * &lt;
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression lt(String field, Object value) {
		return new Condition(field, "<", value);
	}

	/**
	 * &lt;=
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression le(String field, Object value) {
		return new Condition(field, "<=", value);
	}

	/**
	 * like
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression like(String field, Object value) {
		return new Condition(field, "like", value);
	}

	/**
	 * 用来赋值,表达式为=
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public static Expression addValue(String field, Object value) {
		return new TableField(field, "=", value);
	}
}