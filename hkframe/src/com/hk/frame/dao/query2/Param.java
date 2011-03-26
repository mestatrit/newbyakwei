package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.Map;

/**
 * insert、delete、update、select时参数的对象表示方式，完全是为了程序易读<br/>
 * insert、deleteById、deleteObj时， 没有参数有效<br/>
 * 
 * @author akwei
 */
public abstract class Param {

	private String where;

	private Object[] params;

	private final Map<String, Object> ctxMap = new HashMap<String, Object>(2);

	private ObjectSqlInfoCreater objectSqlInfoCreater;

	public Param(ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	public ObjectSqlInfoCreater getObjectSqlInfoCreater() {
		return objectSqlInfoCreater;
	}

	public void setObjectSqlInfoCreater(
			ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	public Map<String, Object> getCtxMap() {
		return ctxMap;
	}

	public <T> void addKeyAndValue(Class<T> clazz, String key, Object value) {
		this.ctxMap.put(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getTableName()
				+ "." + key, value);
	}

	public <T> void addKeyAndValue(String key, Object value) {
		this.ctxMap.put(key, value);
	}

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
