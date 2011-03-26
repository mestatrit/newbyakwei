package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.Map;

public class BaseParam {

	private final Map<String, Object> ctxMap = new HashMap<String, Object>(2);

	private ObjectSqlInfoCreater objectSqlInfoCreater;

	public BaseParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	public ObjectSqlInfoCreater getObjectSqlInfoCreater() {
		return objectSqlInfoCreater;
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
}
