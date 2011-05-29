package com.dev3g.cactus.dao.query;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.dev3g.cactus.dao.partition.DbPartitionHelper;
import com.dev3g.cactus.dao.query.param.DeleteParam;
import com.dev3g.cactus.dao.query.param.InsertParam;
import com.dev3g.cactus.dao.query.param.Param;
import com.dev3g.cactus.dao.query.param.QueryParam;
import com.dev3g.cactus.dao.query.param.UpdateParam;

/**
 * 对象化处理sql
 * 
 * @author akwei
 */
public class HkObjQuery extends HkQuery {

	private ObjectSqlInfoCreater objectSqlInfoCreater;

	/**
	 * 获rowmapper，先从表映射的对象开始匹配，如果没有，就到结果集resultsetdata中进行查找
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		ResultSetDataInfo<T> resultSetDataInfo = this.resultSetDataInfoCreater
				.getResultSetDataInfo(clazz);
		if (resultSetDataInfo != null) {
			return resultSetDataInfo.getRowMapper();
		}
		throw new RuntimeException("no rowmapper for " + clazz.getName());
	}

	public void setObjectSqlInfoCreater(
			ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
	}

	/**
	 * 获得表信息分析器
	 * 
	 * @param <T>
	 * @param clazz
	 *            表信息分析器与class对应，传递class参数即可获得
	 * @return
	 */
	public <T> DbPartitionHelper getDbPartitionHelper(Class<T> clazz) {
		return this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getDbPartitionHelper();
	}

	/**
	 * 获得真实的操作的数据表信息
	 * 
	 * @param <T>
	 * @param clazz
	 * @param ctxMap
	 * @return
	 */
	public <T> PartitionTableInfo parse(Class<T> clazz,
			Map<String, Object> ctxMap) {
		return this.getDbPartitionHelper(clazz).parse(
				this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
						.getTableName(), ctxMap);
	}

	/**
	 * 获得真实的操作的数据表信息
	 * 
	 * @param <T>
	 * @param classes
	 * @param ctxMap
	 * @return
	 */
	public <T> PartitionTableInfo[] parse(Class<?>[] classes,
			Map<String, Object> ctxMap) {
		PartitionTableInfo[] partitionTableInfos = new PartitionTableInfo[classes.length];
		for (int i = 0; i < classes.length; i++) {
			partitionTableInfos[i] = this.parse(classes[i], ctxMap);
		}
		return partitionTableInfos;
	}

	/**
	 * 根据参数获得所需要的类中与数据库对应的所有列字段
	 * 
	 * @param <T>
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> String[][] getColumns(Class<?>[] classes) {
		ObjectSqlInfo<T> objectSqlInfo = null;
		String[][] columns = new String[classes.length][];
		for (int i = 0; i < classes.length; i++) {
			objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
					.getObjectSqlInfo(classes[i]);
			columns[i] = objectSqlInfo.getColumns();
		}
		return columns;
	}

	@SuppressWarnings("unchecked")
	public <T> Object insertObj(InsertParam insertParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		PartitionTableInfo partitionTableInfo = this.parse(t.getClass(),
				insertParam.getCtxMap());
		return this.insertBySQL(partitionTableInfo.getDatabaseName(),
				SqlBuilder.createInsertSQL(partitionTableInfo, objectSqlInfo
						.getColumns()), objectSqlInfo.getSqlUpdateMapper()
						.getParamsForInsert(t));
	}

	@SuppressWarnings("unchecked")
	public <T> int updateObj(UpdateParam updateParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		updateParam.setWhere(objectSqlInfo.getIdColumn() + "=?");
		updateParam.setUpdateColumns(objectSqlInfo.getColumnsForUpdate());
		updateParam.setParams(objectSqlInfo.getSqlUpdateMapper()
				.getParamsForUpdate(t));
		return this.update(updateParam, t.getClass());
	}

	public <T> int update(UpdateParam updateParam, Class<T> clazz) {
		PartitionTableInfo partitionTableInfo = this.parse(clazz, updateParam
				.getCtxMap());
		return this.updateBySQL(partitionTableInfo.getDatabaseName(),
				SqlBuilder.createUpdateSQL(partitionTableInfo, updateParam
						.getUpdateColumns(), updateParam.getWhere()),
				updateParam.getParams());
	}

	public <T> int delete(DeleteParam deleteParam, Class<T> clazz) {
		PartitionTableInfo partitionTableInfo = this.parse(clazz, deleteParam
				.getCtxMap());
		return this.updateBySQL(partitionTableInfo.getDatabaseName(),
				SqlBuilder.createDeleteSQL(partitionTableInfo, deleteParam
						.getWhere()), deleteParam.getParams());
	}

	/**
	 * 删除对象
	 * 
	 * @param <T>
	 * @param baseParam
	 * @param t
	 *            要删除的对象，只能是与表映射对应的对象，不能是结果集映射对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> int deleteObj(Param baseParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		return this.deleteById(baseParam, t.getClass(), objectSqlInfo
				.getSqlUpdateMapper().getIdParam(t));
	}

	/**
	 * 根据id删除对象
	 * 
	 * @param <T>
	 * @param baseParam
	 * @param clazz
	 *            要删除的数据表的类信息
	 * @param idValue
	 * @return
	 */
	public <T> int deleteById(Param baseParam, Class<T> clazz, Object idValue) {
		DeleteParam deleteParam = new DeleteParam();
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		deleteParam.setWhere(objectSqlInfo.getIdColumn() + "=?");
		deleteParam.setParams(new Object[] { idValue });
		return this.delete(deleteParam, clazz);
	}

	/**
	 * 查询操作，如果QueryParam 中columns没有赋值,则查询表中的所有列，程序将会自动复制列信息到columns属性
	 * 
	 * @param <T>
	 * @param queryParam
	 * @param mapper
	 * @return
	 */
	public <T> List<T> getList(QueryParam queryParam, RowMapper<T> mapper) {
		if (queryParam.getColumns() == null) {
			queryParam.setColumns(this.getColumns(queryParam.getClasses()));
		}
		PartitionTableInfo[] partitionTableInfos = this.parse(queryParam
				.getClasses(), queryParam.getCtxMap());
		return this.getListBySQL(partitionTableInfos[0].getDatabaseName(),
				SqlBuilder.createListSQL(partitionTableInfos, queryParam
						.getColumns(), queryParam.getWhere(), queryParam
						.getOrder()), queryParam.getParams(), queryParam
						.getBegin(), queryParam.getSize(), mapper);
	}

	/**
	 * 数据查询select
	 * 
	 * @param <T>
	 * @param queryParam
	 * @param clazz
	 *            返回对象类型，如果 queryParam没有设置classes参数，默认使用此参数 根据此类型可以获得匹配的mapper
	 * @return
	 */
	public <T> List<T> getList(QueryParam queryParam, Class<T> clazz) {
		if (queryParam.getClassCount() == 0) {
			queryParam.addClass(clazz);
		}
		return this.getList(queryParam, this.getRowMapper(clazz));
	}

	public <T> int count(QueryParam queryParam) {
		PartitionTableInfo[] partitionTableInfos = this.parse(queryParam
				.getClasses(), queryParam.getCtxMap());
		return this.countBySQL(partitionTableInfos[0].getDatabaseName(),
				SqlBuilder.createCountSQL(partitionTableInfos, queryParam
						.getWhere()), queryParam.getParams());
	}

	/**
	 * 查询操作，如果QueryParam 中columns没有赋值,则查询表中的所有列，程序将会自动复制列信息到columns属性
	 * 
	 * @param <T>
	 * @param queryParam
	 * @param mapper
	 * @return
	 */
	public <T> T getObject(QueryParam queryParam, RowMapper<T> mapper) {
		if (queryParam.getColumns() == null) {
			queryParam.setColumns(this.getColumns(queryParam.getClasses()));
		}
		PartitionTableInfo[] partitionTableInfos = this.parse(queryParam
				.getClasses(), queryParam.getCtxMap());
		return this.getObjectBySQL(partitionTableInfos[0].getDatabaseName(),
				SqlBuilder.createObjectSQL(partitionTableInfos, queryParam
						.getColumns(), queryParam.getWhere(), queryParam
						.getOrder()), queryParam.getParams(), mapper);
	}

	/**
	 * 查询单个对象
	 * 
	 * @param <T>
	 * @param queryParam
	 * @param clazz
	 *            根据此类型可以获得匹配的mapper
	 * @return
	 */
	public <T> T getObject(QueryParam queryParam, Class<T> clazz) {
		if (queryParam.getClassCount() == 0) {
			queryParam.addClass(clazz);
		}
		return this.getObject(queryParam, this.getRowMapper(clazz));
	}

	/**
	 * 根据id查询对象,默认查询 clazz 对象数据，如果设置了
	 * 
	 * @param <T>
	 * @param queryParam
	 * @param clazz
	 *            根据此类型可以获得匹配的mapper
	 * @param idValue
	 * @return
	 */
	public <T> T getObjectById(QueryParam queryParam, Class<T> clazz,
			Object idValue) {
		queryParam.setWhere(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getIdColumn()
				+ "=?");
		queryParam.setParams(new Object[] { idValue });
		queryParam.setOrder(null);
		queryParam.setBegin(0);
		queryParam.setSize(1);
		if (queryParam.getClassCount() == 0) {
			queryParam.addClass(clazz);
		}
		return this.getObject(queryParam, clazz);
	}
}