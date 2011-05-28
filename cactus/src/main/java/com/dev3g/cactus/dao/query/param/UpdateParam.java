package com.dev3g.cactus.dao.query.param;

/**
 * 更新时参数的对象表示方式，完全是为了程序易读<br/>
 * 
 * @author akwei
 */
public class UpdateParam extends Param {

	public UpdateParam() {
		super();
	}

	public UpdateParam(String key, Object value) {
		super(key, value);
	}

	private String[] updateColumns;

	private String where;

	private Object[] params;

	public String getWhere() {
		return where;
	}

	/**
	 * 设置sql的where条件
	 * 
	 * @param where
	 */
	public void setWhere(String where) {
		this.where = where;
	}

	public Object[] getParams() {
		return params;
	}

	/**
	 * 设置条件对应的参数
	 * 
	 * @param params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	public String[] getUpdateColumns() {
		return updateColumns;
	}

	/**
	 * 设置需要更新的列(与数据库字段相同)
	 * 
	 * @param updateColumns
	 */
	public void setUpdateColumns(String[] updateColumns) {
		this.updateColumns = updateColumns;
	}
}
