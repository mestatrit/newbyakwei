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
	 * 类名与分析类的名称组合。格式为：className;helperClassName;
	 */
	private List<String> infos;

	public void setInfos(List<String> infos) {
		this.infos = infos;
	}

	public List<String> getInfos() {
		return infos;
	}

	public ObjectSqlInfo<?> getObjectSqlInfo(String className) {
		return this.objectSqlInfoMap.get(className);
	}

	public <T> ObjectSqlInfo<?> getObjectSqlInfo(Class<T> clazz) {
		return this.objectSqlInfoMap.get(clazz.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		Class<? extends Object> clazz = null;
		Class<? extends Object> dbPartitionHelperClazz = null;
		ObjectSqlInfo<?> objectSqlInfo = null;
		for (String s : infos) {
			String[] tmp = s.split(";");
			String className = tmp[0];
			String dbPartitionHelperClassName = tmp[1];
			clazz = classLoader.loadClass(className);
			dbPartitionHelperClazz = classLoader
					.loadClass(dbPartitionHelperClassName);
			objectSqlInfo = new ObjectSqlInfo(clazz, dbPartitionHelperClazz);
			objectSqlInfoMap.put(className, objectSqlInfo);
		}
	}
}