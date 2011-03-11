package com.hk.frame.dao.query2;

import java.util.Map;

/**
 * 进行数据库选择与表选择的工具类
 * 
 * @author akwei
 */
public abstract class DbPartitionHelper {

	/**
	 * 返回经过计算后，生成的真是数据库与表信息<br/>
	 * 
	 * @param logicDatabaseName
	 *            要进行计算的逻辑表名
	 * @param ctxMap
	 *            logicDatabaseName 上下文数据对象
	 * @return
	 */
	public abstract <T> PartitionTableInfo createPartitionTableInfoForInsert(
			Class<T> clazz, Map<String, Object> ctxMap);

	public abstract <T> PartitionTableInfo createPartitionTableInfoForUpdate(
			Class<T> clazz, Map<String, Object> ctxMap);

	public abstract <T> PartitionTableInfo createPartitionTableInfoForDelete(
			Class<T> clazz, Map<String, Object> ctxMap);

	public abstract <T> PartitionTableInfo createPartitionTableInfoForSelect(
			Class<T> clazz, Map<String, Object> ctxMap);
}
