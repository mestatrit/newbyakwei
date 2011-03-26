package com.hk.frame.dao.query2;

/**
 * insert、delete、update、select时参数的对象表示方式，完全是为了程序易读<br/>
 * insert、deleteById、deleteObj时， 没有参数有效<br/>
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

	public void setWhere(String where) {
		this.where = where;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
