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

	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		return this.getObjectSqlInfoCreater().getObjectSqlInfo(clazz)
				.getRowMapper();
	}

	public <T> DbPartitionHelper getDbPartitionHelper(Class<T> clazz) {
		return this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getDbPartitionHelper();
	}

	public <T> PartitionTableInfo parse(Class<T> clazz,
			Map<String, Object> ctxMap) {
		return this.getDbPartitionHelper(clazz).parse(
				this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
						.getTableName(), ctxMap);
	}

	public <T> PartitionTableInfo[] parse(Class<?>[] classes,
			Map<String, Object> ctxMap) {
		PartitionTableInfo[] partitionTableInfos = new PartitionTableInfo[classes.length];
		for (int i = 0; i < classes.length; i++) {
			partitionTableInfos[i] = this.parse(classes[i], ctxMap);
		}
		return partitionTableInfos;
	}

	@SuppressWarnings("unchecked")
	protected <T> Object insertObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.insert(partitionTableInfo, objectSqlInfo.getColumns(),
				objectSqlInfo.getSqlUpdateMapper().getParamsForInsert(t));
	}

	public <T> Object insertObj(Map<String, Object> ctxMap, T t) {
		return this.insertObj(this.parse(t.getClass(), ctxMap), t);
	}

	public <T> Object insert(Param param, T t) {
		return this.insertObj(param.getCtxMap(), t);
	}

	@SuppressWarnings("unchecked")
	protected <T> int updateObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.update(partitionTableInfo, objectSqlInfo
				.getColumnsForUpdate(), objectSqlInfo.getIdColumn() + "=?",
				objectSqlInfo.getSqlUpdateMapper().getParamsForUpdate(t));
	}

	public <T> int updateObj(Map<String, Object> ctxMap, T t) {
		return this.updateObj(this.parse(t.getClass(), ctxMap), t);
	}

	public <T> int update(Map<String, Object> ctxMap, Class<T> clazz,
			String[] columns, String where, Object[] params) {
		return this.update(this.parse(clazz, ctxMap), columns, where, params);
	}

	public <T> int update(UpdateParam updateParam) {
		return this.update(updateParam.getCtxMap(), updateParam.getClazz(),
				updateParam.getUpdateColumns(), updateParam.getWhere(),
				updateParam.getParams());
	}

	public <T> int delete(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params) {
		return this.delete(this.parse(clazz, ctxMap), where, params);
	}

	public <T> int delete(DeleteParam deleteParam) {
		return this.delete(deleteParam.getCtxMap(), deleteParam.getClazz(),
				deleteParam.getWhere(), deleteParam.getParams());
	}

	@SuppressWarnings("unchecked")
	protected <T> int deleteObj(PartitionTableInfo partitionTableInfo, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { objectSqlInfo.getSqlUpdateMapper()
				.getIdParam(t) });
	}

	public <T> int deleteObj(Map<String, Object> ctxMap, T t) {
		return this.deleteObj(this.parse(t.getClass(), ctxMap), t);
	}

	public <T> int deleteObj(Param param, T t) {
		return this.deleteObj(param.getCtxMap(), t);
	}

	protected <T> int deleteById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.delete(partitionTableInfo, objectSqlInfo.getIdColumn()
				+ "=?", new Object[] { idValue });
	}

	public <T> int deleteById(Map<String, Object> ctxMap, Class<T> clazz,
			Object idValue) {
		return this.deleteById(this.parse(clazz, ctxMap), clazz, idValue);
	}

	public <T> int deleteById(DeleteParam deleteParam, Object idValue) {
		return this.deleteById(deleteParam.getCtxMap(), deleteParam.getClazz(),
				idValue);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> getList(PartitionTableInfo[] partitionTableInfos,
			Class[] classes, String where, Object[] params, String order,
			int begin, int size, RowMapper<T> mapper) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		String[][] columns = new String[classes.length][];
		for (int i = 0; i < classes.length; i++) {
			objectSqlInfo = this.objectSqlInfoCreater
					.getObjectSqlInfo(classes[i]);
			columns[i] = objectSqlInfo.getColumns();
		}
		return this.getList(partitionTableInfos, columns, where, params, order,
				begin, size, mapper);
	}

	public <T> List<T> getList(Map<String, Object> ctxMap, Class<?>[] classes,
			String where, Object[] params, String order, int begin, int size,
			RowMapper<T> mapper) {
		return this.getList(this.parse(classes, ctxMap), classes, where,
				params, order, begin, size, mapper);
	}

	public <T> List<T> getList(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, String where, Object[] params, String order,
			int begin, int size) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.getList(new PartitionTableInfo[] { partitionTableInfo },
				new Class[] { clazz }, where, params, order, begin, size,
				objectSqlInfo.getRowMapper());
	}

	public <T> List<T> getList(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params, String order, int begin, int size) {
		return this.getList(this.parse(clazz, ctxMap), clazz, where, params,
				order, begin, size);
	}

	public <T> List<T> getList(QueryParam queryParam, RowMapper<T> mapper) {
		return this.getList(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), queryParam
						.getBegin(), queryParam.getSize(), mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> int countEx(Map<String, Object> ctxMap, Class[] classes,
			String where, Object[] params) {
		return this.count(this.parse(classes, ctxMap), where, params);
	}

	public <T> int countEx(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params) {
		return this.countEx(ctxMap, new Class[] { clazz }, where, params);
	}

	public <T> int countEx(QueryParam queryParam) {
		return this.countEx(queryParam.getCtxMap(), queryParam.getClasses(),
				queryParam.getWhere(), queryParam.getParams());
	}

	public <T> T getObject(PartitionTableInfo[] partitionTableInfos,
			Class<T> clazz, String where, Object[] params, String order) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.getObject(partitionTableInfos,
				new String[][] { objectSqlInfo.getColumns() }, where, params,
				order, objectSqlInfo.getRowMapper());
	}

	protected <T> T getObject(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, String where, Object[] params, String order) {
		return this.getObject(new PartitionTableInfo[] { partitionTableInfo },
				clazz, where, params, order);
	}

	public <T> T getObject(Map<String, Object> ctxMap, Class<T> clazz,
			String where, Object[] params, String order) {
		return this.getObject(this.parse(clazz, ctxMap), clazz, where, params,
				order);
	}

	public <T> T getObject(QueryParam queryParam, RowMapper<T> mapper) {
		return this.getObject(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), mapper);
	}

	private <T> T getObjectById(PartitionTableInfo partitionTableInfo,
			Class<T> clazz, Object idValue) {
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		return this.getObject(partitionTableInfo, clazz, objectSqlInfo
				.getIdColumn()
				+ "=?", new Object[] { idValue }, null);
	}

	public <T> T getObjectById(Map<String, Object> ctxMap, Class<T> clazz,
			Object idValue) {
		return this.getObjectById(this.parse(clazz, ctxMap), clazz, idValue);
	}
}