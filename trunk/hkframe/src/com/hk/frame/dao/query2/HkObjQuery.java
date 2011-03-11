package com.hk.frame.dao.query2;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

/**
 * 对象化处理sql
 * 
 * @author akwei
 */
public class HkObjQuery extends HkQuery {

	private ObjectSqlInfoCreater objectSqlInfoCreater;

	public void setObjectSqlInfoCreater(
			ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	@SuppressWarnings("unchecked")
	public <T> Number insertObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass().getName());
		return this.insert(partitionTableInfo, objectSqlInfo.getColumns(),
				objectSqlInfo.getSqlUpdateMapper().getParamsForInsert(t));
	}

	@SuppressWarnings("unchecked")
	public <T> int updateObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass().getName());
		return this.update(partitionTableInfo, objectSqlInfo
				.getColumnsForUpdate(), objectSqlInfo.getIdColumn() + "=?",
				objectSqlInfo.getSqlUpdateMapper().getParamsForUpdate(t));
	}

	@SuppressWarnings("unchecked")
	public <T> int deleteObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass().getName());
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { objectSqlInfo.getSqlUpdateMapper()
				.getIdParam(t) });
	}

	@SuppressWarnings("unchecked")
	public <T> int deleteById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz.getName());
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { idValue });
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryListEx(PartitionTableInfo[] partitionTableInfos,
			Class[] classes, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		String[][] columns = new String[classes.length][];
		int i = 0;
		for (Class<?> clazz : classes) {
			objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
					.getObjectSqlInfo(clazz.getName());
			columns[i] = objectSqlInfo.getColumns();
			i++;
		}
		return this.queryList(partitionTableInfos, columns, where, params,
				order, begin, size, mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryListEx(PartitionTableInfo[] partitionTableInfos,
			Class clazz, String where, Object[] params, String order,
			int begin, int size) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz.getName());
		return this.queryList(partitionTableInfos,
				new String[][] { objectSqlInfo.getColumns() }, where, params,
				order, begin, size, objectSqlInfo.getRowMapper());
	}

	@SuppressWarnings("unchecked")
	public <T> T queryObjectEx(PartitionTableInfo[] partitionTableInfos,
			Class<T> clazz, String where, Object[] params, String order,
			RowMapper<T> mapper) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz.getName());
		return this.queryObject(partitionTableInfos,
				new String[][] { objectSqlInfo.getColumns() }, where, params,
				order, mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> T queryObjectEx(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, String where, Object[] params, String order) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz.getName());
		return this.queryObject(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { objectSqlInfo.getColumns() }, where, params,
				order, objectSqlInfo.getRowMapper());
	}

	@SuppressWarnings("unchecked")
	public <T> T queryObjectExById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz.getName());
		return this.queryObjectEx(partitionTableInfo, clazz, objectSqlInfo
				.getIdColumn()
				+ "=?", new Object[] { idValue }, null);
	}
}