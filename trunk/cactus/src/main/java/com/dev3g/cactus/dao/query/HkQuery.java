package com.dev3g.cactus.dao.query;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.dev3g.cactus.dao.sql.DataSourceStatus;

/**
 * 操作数据的工具类。调用daosupport进行数据库处理
 * 
 * @author akwei
 */
public class HkQuery {

	private DaoSupport daoSupport;

	public void setDaoSupport(DaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	protected DaoSupport getDaoSupport() {
		return daoSupport;
	}

	/**
	 * 根据条件获得统计数字
	 * 
	 * @param partitionTableInfos
	 *            表信息
	 * @param where
	 *            where sql表达式,没有可为null
	 * @param params
	 *            没有可为null
	 * @return
	 */
	public int count(PartitionTableInfo[] partitionTableInfos, String where,
			Object[] params) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this
				.getDaoSupport()
				.getNumberBySQL(
						SqlBuilder.createCountSQL(partitionTableInfos, where),
						params).intValue();
	}

	/**
	 * 返回符合条件的数据集合
	 * 
	 * @param <T>
	 *            返回类型
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            返回的表的字段是全名，例如: user.userid
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @param order
	 *            order 表达式(没有order关键字)，没有可为null
	 * @param begin
	 *            记录开始位置，当begin<0时，获取所有数据，不受size限制
	 * @param size
	 *            记录获取的最多数量，当size<=0时，获取所有数据，不受size限制
	 * @param mapper
	 *            返回值的组装对象
	 * @return
	 */
	public <T> List<T> getList(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		return this.getListBySQL(partitionTableInfos, SqlBuilder.createListSQL(
				partitionTableInfos, columns, where, order), params, begin,
				size, mapper);
	}

	/**
	 * 返回符合条件的单个对象
	 * 
	 * @param <T>
	 * @param partitionTableInfos
	 *            表信息
	 * @param columns
	 *            要获取的字段
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @param order
	 *            order 表达式(没有order关键字)，没有可为null
	 * @param mapper
	 *            返回值的组装对象
	 * @return
	 */
	public <T> T getObject(PartitionTableInfo[] partitionTableInfos,
			String[][] columns, String where, Object[] params, String order,
			RowMapper<T> mapper) {
		return this.getObjectBySQL(partitionTableInfos, SqlBuilder
				.createObjectSQL(partitionTableInfos, columns, where, order),
				params, mapper);
	}

	/**
	 * 创建数据
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @param columns
	 *            插入的字段
	 * @param params
	 *            字段对应的参数
	 * @return
	 */
	public Object insert(PartitionTableInfo partitionTableInfo,
			String[] columns, Object[] params) {
		return this
				.insertBySQL(
						partitionTableInfo,
						SqlBuilder.createInsertSQL(partitionTableInfo, columns),
						params);
	}

	/**
	 * 根据条件更新数据
	 * 
	 * @param partitionTableInfo
	 * @param columns
	 *            需要更新的字段
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @return
	 */
	public int update(PartitionTableInfo partitionTableInfo, String[] columns,
			String where, Object[] params) {
		return this.updateBySQL(partitionTableInfo,
				SqlBuilder.createUpdateSQL(partitionTableInfo, columns, where),
				params);
	}

	/**
	 * 根据条件删除数据
	 * 
	 * @param partitionTableInfo
	 *            表信息
	 * @param where
	 *            where表达式(没有where关键字),没有可为null
	 * @param params
	 *            表达式的值，没有可为null
	 * @return
	 */
	public int delete(PartitionTableInfo partitionTableInfo, String where,
			Object[] params) {
		return this.updateBySQL(partitionTableInfo,
				SqlBuilder.createDeleteSQL(partitionTableInfo, where), params);
	}

	/**
	 * sql执行 insert
	 * 
	 * @param partitionTableInfo
	 *            分区信息
	 * @param sql
	 *            需要执行的sql
	 * @param values
	 *            参数
	 * @return
	 */
	public Object insertBySQL(PartitionTableInfo partitionTableInfo,
			String sql, Object[] values) {
		DataSourceStatus.setCurrentDsName(partitionTableInfo.getDatabaseName());
		return this.getDaoSupport().insertBySQL(sql, values);
	}

	/**
	 * sql 执行 select
	 * 
	 * @param <T>
	 * @param partitionTableInfos
	 *            分区信息
	 * @param sql
	 *            需要执行的sql
	 * @param values
	 *            参数
	 * @param rm
	 *            返回值封装对象
	 * @return
	 */
	public <T> List<T> getListBySQL(PartitionTableInfo[] partitionTableInfos,
			String sql, Object[] values, int begin, int size, RowMapper<T> rm) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().getListBySQL(sql, values, begin, size, rm);
	}

	/**
	 * 执行sql并返回数值类型
	 * 
	 * @param partitionTableInfos
	 *            分区信息
	 * @param sql
	 *            需要执行的sql
	 * @param values
	 *            参数
	 * @return
	 */
	public Number getNumberBySQL(PartitionTableInfo[] partitionTableInfos,
			String sql, Object[] values) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().getNumberBySQL(sql, values);
	}

	/**
	 * 执行sql，返回对象
	 * 
	 * @param <T>
	 * @param partitionTableInfos
	 *            分区信息
	 * @param sql
	 *            需要执行的sql
	 * @param values
	 *            参数
	 * @param rm
	 *            返回值封装对象
	 * @return
	 */
	public <T> T getObjectBySQL(PartitionTableInfo[] partitionTableInfos,
			String sql, Object[] values, RowMapper<T> rm) {
		DataSourceStatus.setCurrentDsName(partitionTableInfos[0]
				.getDatabaseName());
		return this.getDaoSupport().getObjectBySQL(sql, values, rm);
	}

	/**
	 * sql update
	 * 
	 * @param partitionTableInfo
	 *            分区信息
	 * @param sql
	 *            需要执行的sql
	 * @param values
	 *            参数
	 * @return
	 */
	public int updateBySQL(PartitionTableInfo partitionTableInfo, String sql,
			Object[] values) {
		DataSourceStatus.setCurrentDsName(partitionTableInfo.getDatabaseName());
		return this.getDaoSupport().updateBySQL(sql, values);
	}
}