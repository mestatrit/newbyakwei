package com.dev3g.cactus.dao.query;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 初始化配置的sql类信息
 * 
 * @author akwei
 */
public class ResultSetDataInfoCreater {

	/**
	 * class名称为key
	 */
	private final Map<String, ResultSetDataInfo<?>> infoMap = new HashMap<String, ResultSetDataInfo<?>>();

	/**
	 * 获得结果集信息数据
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> ResultSetDataInfo<T> getResultSetDataInfo(Class<T> clazz) {
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

	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		ResultSetDataInfo<T> info = this.getResultSetDataInfo(clazz);
		if (info != null) {
			return info.getRowMapper();
		}
		return null;
	}
}