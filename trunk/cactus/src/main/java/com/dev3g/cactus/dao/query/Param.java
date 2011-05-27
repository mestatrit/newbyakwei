package com.dev3g.cactus.dao.query;

/**
 * delete、update、select时参数的对象表示方式，完全是为了程序易读<br/>
 * deleteById、deleteObj时， 没有参数有效<br/>
 * 
 * @author akwei
 */
public class Param extends BaseParam {

	public Param(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

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
}
