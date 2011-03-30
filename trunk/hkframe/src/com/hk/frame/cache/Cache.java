package com.hk.frame.cache;

/**
 * 定义缓存使用接口
 * 
 * @author akwei
 */
public interface Cache {

	/**
	 * 根据key获取对象
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 存储对象到缓存
	 * 
	 * @param key
	 *            对象key
	 * @param object
	 *            存储的对象
	 * @return true:成功存储;false:存储失败
	 */
	boolean put(String key, Object object);

	/**
	 * 从缓存中删除
	 * 
	 * @param key
	 *            对象key
	 * @return true:删除成功;false:删除失败
	 */
	boolean remove(String key);
}
