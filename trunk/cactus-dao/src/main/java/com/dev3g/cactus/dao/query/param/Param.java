package com.dev3g.cactus.dao.query.param;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数对象，由于方法传递参数过多，所以封装此对象使用
 * 
 * @author akwei
 */
public abstract class Param {

	private final Map<String, Object> ctxMap = new HashMap<String, Object>(2);

	public Param() {
	}

	public Param(String key, Object value) {
		this.addKeyAndValue(key, value);
	}

	/**
	 * 返回存储的context信息(保存的分区相关信息)
	 * 
	 * @return
	 */
	public Map<String, Object> getCtxMap() {
		return ctxMap;
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
		if (key == null || value == null) {
			return;
		}
		this.ctxMap.put(key, value);
	}
}
