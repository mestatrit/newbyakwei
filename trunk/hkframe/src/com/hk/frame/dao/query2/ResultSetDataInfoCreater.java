package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化配置的sql类信息
 * 
 * @author fire9
 */
public class ResultSetDataInfoCreater {

	private final Map<String, ResultSetDataInfo<?>> infoMap = new HashMap<String, ResultSetDataInfo<?>>();

	@SuppressWarnings("unchecked")
	public <T> ResultSetDataInfo<T> getObjectSqlInfo(Class<T> clazz) {
		ResultSetDataInfo<T> info = (ResultSetDataInfo<T>) this.infoMap
				.get(clazz.getName());
		if (info == null) {
			info = this.createResultSetDataInfo(clazz);
			infoMap.put(clazz.getName(), info);
		}
		return info;
	}

	@SuppressWarnings("unchecked")
	private synchronized <T> ResultSetDataInfo<T> createResultSetDataInfo(
			Class<T> clazz) {
		ResultSetDataInfo<T> info = (ResultSetDataInfo<T>) this.infoMap
				.get(clazz.getName());
		if (info == null) {
			info = new ResultSetDataInfo<T>(clazz);
		}
		return info;
	}
}