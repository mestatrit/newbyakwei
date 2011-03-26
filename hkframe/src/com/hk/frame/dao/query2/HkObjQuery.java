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

	private ResultSetDataInfoCreater resultSetDataInfoCreater;

	public void setResultSetDataInfoCreater(
			ResultSetDataInfoCreater resultSetDataInfoCreater) {
		this.resultSetDataInfoCreater = resultSetDataInfoCreater;
	}

	public <T> RowMapper<T> getResultSetDataInfoMapper(Class<T> clazz) {
		return this.resultSetDataInfoCreater.getObjectSqlInfo(clazz)
				.getRowMapper();
	}

	public void setObjectSqlInfoCreater(
			ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	private ObjectSqlInfoCreater getObjectSqlInfoCreater() {
		return objectSqlInfoCreater;
	}

	public BaseParam createBaseParam() {
		return new BaseParam(this.getObjectSqlInfoCreater());
	}

	public UpdateParam createUpdateParam() {
		return new UpdateParam(this.getObjectSqlInfoCreater());
	}

	public DeleteParam createDeleteParam() {
		return new DeleteParam(this.getObjectSqlInfoCreater());
	}

	public QueryParam createQueryParam() {
		return new QueryParam(this.getObjectSqlInfoCreater());
	}

	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		return this.getObjectSqlInfoCreater().getObjectSqlInfo(clazz)
				.getRowMapper();
	}

	private <T> DbPartitionHelper getDbPartitionHelper(Class<T> clazz) {
		return this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getDbPartitionHelper();
	}

	private <T> PartitionTableInfo parse(Class<T> clazz,
			Map<String, Object> ctxMap) {
		return this.getDbPartitionHelper(clazz).parse(
				this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
						.getTableName(), ctxMap);
	}

	private <T> PartitionTableInfo[] parse(Class<?>[] classes,
			Map<String, Object> ctxMap) {
		PartitionTableInfo[] partitionTableInfos = new PartitionTableInfo[classes.length];
		for (int i = 0; i < classes.length; i++) {
			partitionTableInfos[i] = this.parse(classes[i], ctxMap);
		}
		return partitionTableInfos;
	}

	@SuppressWarnings("unchecked")
	private <T> String[][] getColumns(Class[] classes) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		String[][] columns = new String[classes.length][];
		for (int i = 0; i < classes.length; i++) {
			objectSqlInfo = this.objectSqlInfoCreater
					.getObjectSqlInfo(classes[i]);
			columns[i] = objectSqlInfo.getColumns();
		}
		return columns;
	}

	@SuppressWarnings("unchecked")
	public <T> Object insert(BaseParam baseParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.insert(this.parse(t.getClass(), baseParam.getCtxMap()),
				objectSqlInfo.getColumns(), objectSqlInfo.getSqlUpdateMapper()
						.getParamsForInsert(t));
	}

	@SuppressWarnings("unchecked")
	public <T> int updateObj(BaseParam baseParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		UpdateParam updateParam = new UpdateParam(baseParam
				.getObjectSqlInfoCreater());
		updateParam.setWhere(objectSqlInfo.getIdColumn() + "=?");
		updateParam.setClazz(t.getClass());
		updateParam.setUpdateColumns(objectSqlInfo.getColumnsForUpdate());
		updateParam.setParams(objectSqlInfo.getSqlUpdateMapper()
				.getParamsForUpdate(t));
		return this.update(updateParam);
	}

	public <T> int update(UpdateParam updateParam) {
		return this.update(this.parse(updateParam.getClazz(), updateParam
				.getCtxMap()), updateParam.getUpdateColumns(), updateParam
				.getWhere(), updateParam.getParams());
	}

	public <T> int delete(DeleteParam deleteParam) {
		return this.delete(this.parse(deleteParam.getClazz(), deleteParam
				.getCtxMap()), deleteParam.getWhere(), deleteParam.getParams());
	}

	@SuppressWarnings("unchecked")
	public <T> int deleteObj(BaseParam baseParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.deleteById(baseParam, t.getClass(), objectSqlInfo
				.getSqlUpdateMapper().getIdParam(t));
	}

	public <T> int deleteById(BaseParam baseParam, Class<T> clazz,
			Object idValue) {
		DeleteParam deleteParam = new DeleteParam(baseParam
				.getObjectSqlInfoCreater());
		deleteParam.setClazz(clazz);
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		deleteParam.setWhere(objectSqlInfo.getIdColumn() + "=?");
		deleteParam.setParams(new Object[] { idValue });
		return this.delete(deleteParam);
	}

	public <T> List<T> getList(QueryParam queryParam, RowMapper<T> mapper) {
		if (queryParam.getColumns() == null) {
			queryParam.setColumns(this.getColumns(queryParam.getClasses()));
		}
		return this.getList(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), queryParam
						.getBegin(), queryParam.getSize(), mapper);
	}

	public <T> List<T> getList(QueryParam queryParam, Class<T> clazz) {
		return this.getList(queryParam, this.getRowMapper(clazz));
	}

	public <T> int count(QueryParam queryParam) {
		return this.count(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getWhere(), queryParam.getParams());
	}

	public <T> T getObject(QueryParam queryParam, RowMapper<T> mapper) {
		if (queryParam.getColumns() == null) {
			queryParam.setColumns(this.getColumns(queryParam.getClasses()));
		}
		return this.getObject(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), mapper);
	}

	public <T> T getObject(QueryParam queryParam, Class<T> clazz) {
		return this.getObject(queryParam, this.getRowMapper(clazz));
	}

	public <T> T getObjectById(QueryParam queryParam, Class<T> clazz,
			Object idValue) {
		queryParam.setWhere(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getIdColumn()
				+ "=?");
		queryParam.setParams(new Object[] { idValue });
		queryParam.setOrder(null);
		queryParam.setBegin(0);
		queryParam.setSize(1);
		return this.getObject(queryParam, clazz);
	}
}