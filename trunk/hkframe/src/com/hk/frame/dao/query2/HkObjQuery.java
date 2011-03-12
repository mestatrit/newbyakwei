package com.hk.frame.dao.query2;

import java.util.List;
import java.util.Map;

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

	public ObjectSqlInfoCreater getObjectSqlInfoCreater() {
		return objectSqlInfoCreater;
	}

	public <T> DbPartitionHelper getDbPartitionHelper(Class<T> clazz) {
		return this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getDbPartitionHelper();
	}

	public <T> PartitionTableInfo parse(Class<T> clazz,
			Map<String, Object> ctxMap) {
		DbPartitionHelper dbPartitionHelper = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz).getDbPartitionHelper();
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return dbPartitionHelper.parse(objectSqlInfo.getTableName(), ctxMap);
	}

	@SuppressWarnings("unchecked")
	private <T> Number insertObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.insert(partitionTableInfo, objectSqlInfo.getColumns(),
				objectSqlInfo.getSqlUpdateMapper().getParamsForInsert(t));
	}

	@SuppressWarnings("unchecked")
	public <T> Number insertObj(Map<String, Object> ctxMap, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.insertObj(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), t);
	}

	@SuppressWarnings("unchecked")
	private <T> int updateObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.update(partitionTableInfo, objectSqlInfo
				.getColumnsForUpdate(), objectSqlInfo.getIdColumn() + "=?",
				objectSqlInfo.getSqlUpdateMapper().getParamsForUpdate(t));
	}

	@SuppressWarnings("unchecked")
	public <T> int updateObj(Map<String, Object> ctxMap, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.updateObj(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), t);
	}

	@SuppressWarnings("unchecked")
	private <T> int deleteObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { objectSqlInfo.getSqlUpdateMapper()
				.getIdParam(t) });
	}

	@SuppressWarnings("unchecked")
	public <T> int deleteObj(Map<String, Object> ctxMap, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.deleteObj(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), t);
	}

	private <T> int deleteById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { idValue });
	}

	public <T> int deleteById(Map<String, Object> ctxMap, Class<T> clazz,
			Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.deleteById(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), clazz, idValue);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> queryListEx(PartitionTableInfo[] partitionTableInfos,
			Class[] classes, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		String[][] columns = new String[classes.length][];
		int i = 0;
		for (Class<?> clazz : classes) {
			objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
					.getObjectSqlInfo(clazz);
			columns[i] = objectSqlInfo.getColumns();
			i++;
		}
		return this.queryList(partitionTableInfos, columns, where, params,
				order, begin, size, mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> int countEx(Map<String, Object> ctxMap, Class[] classes,
			String where, Object[] params) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		PartitionTableInfo[] partitionTableInfos = new PartitionTableInfo[classes.length];
		int i = 0;
		for (Class<?> clazz : classes) {
			objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
					.getObjectSqlInfo(clazz);
			partitionTableInfos[i] = objectSqlInfo.getDbPartitionHelper()
					.parse(objectSqlInfo.getTableName(), ctxMap);
			i++;
		}
		return this.count(partitionTableInfos, where, params);
	}

	public <T> int countEx(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params) {
		return this.countEx(ctxMap, new Class[] { clazz }, where, params);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> queryListEx(Map<String, Object> ctxMap, Class[] classes,
			String where, Object[] params, String order, int begin, int size,
			RowMapper<T> mapper) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		PartitionTableInfo[] partitionTableInfos = new PartitionTableInfo[classes.length];
		int i = 0;
		for (Class<?> clazz : classes) {
			objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
					.getObjectSqlInfo(clazz);
			partitionTableInfos[i] = objectSqlInfo.getDbPartitionHelper()
					.parse(objectSqlInfo.getTableName(), ctxMap);
			i++;
		}
		return this.queryListEx(partitionTableInfos, classes, where, params,
				order, begin, size, mapper);
	}

	public <T> List<T> queryListEx(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, String where, Object[] params, String order,
			int begin, int size) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryListEx(
				new PartitionTableInfo[] { partitionTableInfo },
				new Class[] { clazz }, where, params, order, begin, size,
				objectSqlInfo.getRowMapper());
	}

	public <T> List<T> queryListEx(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params, String order, int begin, int size) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryListEx(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), clazz, where, params,
				order, begin, size);
	}

	public <T> T queryObjectEx(PartitionTableInfo[] partitionTableInfos,
			Class<T> clazz, String where, Object[] params, String order) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryObject(partitionTableInfos,
				new String[][] { objectSqlInfo.getColumns() }, where, params,
				order, objectSqlInfo.getRowMapper());
	}

	private <T> T queryObjectEx(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, String where, Object[] params, String order) {
		return this.queryObjectEx(
				new PartitionTableInfo[] { partitionTableInfo }, clazz, where,
				params, order);
	}

	public <T> T queryObjectEx(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params, String order) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryObjectEx(objectSqlInfo.getDbPartitionHelper().parse(
				objectSqlInfo.getTableName(), ctxMap), clazz, where, params,
				order);
	}

	private <T> T queryObjectExById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryObjectEx(partitionTableInfo, clazz, objectSqlInfo
				.getIdColumn()
				+ "=?", new Object[] { idValue }, null);
	}

	public <T> T queryObjectExById(Map<String, Object> ctxMap, Class<T> clazz,
			Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.queryObjectExById(objectSqlInfo.getDbPartitionHelper()
				.parse(objectSqlInfo.getTableName(), ctxMap), clazz, idValue);
	}
}