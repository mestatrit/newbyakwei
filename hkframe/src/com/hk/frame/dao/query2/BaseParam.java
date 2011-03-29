package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数对象，insert时需要
 * 
 * @author akwei
 */
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

	/**
	 * 添加分区关键字
	 * 
	 * @param <T>
	 * @param clazz
	 *            需要分区的类
	 * @param key
	 *            关键字，不需要写别名
	 * @param value
	 *            关键字的值
	 */
	public <T> void addKeyAndValue(Class<T> clazz, String key, Object value) {
		this.ctxMap.put(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getTableName()
				+ "." + key, value);
	}

	/**
	 * 添加分区关键字
	 * 
	 * @param <T>
	 * @param key
	 *            关键字，可以包括别名，必须保证唯一
	 * @param value
	 *            关键字值
	 */
	public <T> void addKeyAndValue(String key, Object value) {
		this.ctxMap.put(key, value);
	}
}
