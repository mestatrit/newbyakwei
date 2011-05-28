package com.dev3g.cactus.dao.query.param;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数对象，insert时需要
 * 
 * @author akwei
 */
public class BaseParam {

	private final Map<String, Object> ctxMap = new HashMap<String, Object>(2);

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
		this.ctxMap.put(key, value);
	}
}
