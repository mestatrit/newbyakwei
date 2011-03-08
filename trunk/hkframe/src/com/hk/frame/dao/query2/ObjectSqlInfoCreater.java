package com.hk.frame.dao.query2;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

/**
 * 初始化配置的sql类信息
 * 
 * @author fire9
 */
public class ObjectSqlInfoCreater implements InitializingBean {

	private Map<String, ObjectSqlInfo<?>> objectSqlInfoMap;

	/**
	 * 存储类名称信息集合
	 */
	private List<String> clazzNameList;

	public void setClazzNameList(List<String> clazzNameList) {
		this.clazzNameList = clazzNameList;
	}

	public List<String> getClazzNameList() {
		return clazzNameList;
	}

	public ObjectSqlInfo<?> getObjectSqlInfo(String key) {
		return this.objectSqlInfoMap.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		Class<? extends Object> clazz = null;
		ObjectSqlInfo<?> objectSqlInfo = null;
		for (String className : clazzNameList) {
			clazz = classLoader.loadClass(className);
			objectSqlInfo = new ObjectSqlInfo(clazz);
			objectSqlInfoMap.put(className, objectSqlInfo);
		}
	}
}