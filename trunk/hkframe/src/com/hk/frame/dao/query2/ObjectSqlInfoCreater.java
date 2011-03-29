package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化类与数据表的对应关系配置信息，存储类与数据表的映射对象的数据
 * 
 * @author akwei
 */
public class ObjectSqlInfoCreater {

	/**
	 * class名称为key
	 */
	private final Map<String, ObjectSqlInfo<?>> objectSqlInfoMap = new HashMap<String, ObjectSqlInfo<?>>();

	/**
	 * 类名与分析类的名称组合。格式为：className;helperClassName;
	 */
	private List<String> infos;

	public void setInfos(List<String> infos) {
		this.infos = infos;
		this.afterPropertiesSet();
	}

	public List<String> getInfos() {
		return infos;
	}

	@SuppressWarnings("unchecked")
	public <T> ObjectSqlInfo<T> getObjectSqlInfo(Class<T> clazz) {
		return (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz.getName());
	}

	@SuppressWarnings("unchecked")
	public <T, D, E> void afterPropertiesSet() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		Class<T> clazz = null;
		Class<D> dbPartitionHelperClazz = null;
		ObjectSqlInfo<E> objectSqlInfo = null;
		for (String s : infos) {
			String[] tmp = s.split(";");
			String className = tmp[0];
			String dbPartitionHelperClassName = tmp[1];
			try {
				clazz = (Class<T>) classLoader.loadClass(className);
				dbPartitionHelperClazz = (Class<D>) classLoader
						.loadClass(dbPartitionHelperClassName);
				objectSqlInfo = new ObjectSqlInfo(clazz, dbPartitionHelperClazz);
				objectSqlInfoMap.put(className, objectSqlInfo);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
}