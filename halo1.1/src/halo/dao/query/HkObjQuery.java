package halo.dao.query;

import halo.util.NumberUtil;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * 对象化处理sql
 * 
 * @author akwei
 */
public class HkObjQuery extends HkQuery {

	public HkObjQuery() {
	}

	private ObjectSqlInfoCreater objectSqlInfoCreater = new ObjectSqlInfoCreater();

	public void setObjectSqlInfoCreater(
			ObjectSqlInfoCreater objectSqlInfoCreater) {
		this.objectSqlInfoCreater = objectSqlInfoCreater;
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
		return this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz)
				.getDbPartitionHelper()
				.parse(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
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
	 * 从class中获得与数据库对应的字段名称
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
	public <T> Object insertObj(String key, Object keyValue, T t) {
		InsertParam insertParam = new InsertParam(key, keyValue);
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		PartitionTableInfo partitionTableInfo = this.parse(t.getClass(),
				insertParam.getCtxMap());
		return this.insertBySQL(partitionTableInfo.getDsKey(),
				SqlBuilder.createInsertSQL(partitionTableInfo,
						objectSqlInfo.getColumns()), objectSqlInfo
						.getSqlUpdateMapper().getParamsForInsert(t));
	}

	public <T> Object insertObjForNumber(String key, Object keyValue, T t) {
		return NumberUtil.getNumber(this.insertObj(key, keyValue, t));
	}

	@SuppressWarnings("unchecked")
	public <T> int updateObj(String key, Object keyValue, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		UpdateParam updateParam = new UpdateParam(key, keyValue);
		updateParam.init(objectSqlInfo.getColumnsForUpdate(),
				objectSqlInfo.getIdColumn() + "=?", objectSqlInfo
						.getSqlUpdateMapper().getParamsForUpdate(t));
		return this.update(updateParam, t.getClass());
	}

	public <T> int update(UpdateParam updateParam, Class<T> clazz) {
		PartitionTableInfo partitionTableInfo = this.parse(clazz,
				updateParam.getCtxMap());
		return this
				.updateBySQL(partitionTableInfo.getDsKey(), SqlBuilder
						.createUpdateSQL(partitionTableInfo,
								updateParam.getUpdateColumns(),
								updateParam.getWhere()), updateParam
						.getParams());
	}

	public <T> int delete(DeleteParam deleteParam, Class<T> clazz) {
		PartitionTableInfo partitionTableInfo = this.parse(clazz,
				deleteParam.getCtxMap());
		return this.updateBySQL(
				partitionTableInfo.getDsKey(),
				SqlBuilder.createDeleteSQL(partitionTableInfo,
						deleteParam.getWhere()), deleteParam.getParams());
	}

	/**
	 * 根据id删除对象
	 * 
	 * @param <T>
	 * @param param
	 * @param clazz
	 *            要删除的数据表的类信息
	 * @param idValue
	 * @return
	 */
	public <T> int deleteById(String key, Object keyValue, Class<T> clazz,
			Object idValue) {
		DeleteParam deleteParam = new DeleteParam(key, keyValue);
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		deleteParam.setWhereAndParams(objectSqlInfo.getIdColumn() + "=?",
				new Object[] { idValue });
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
		PartitionTableInfo[] partitionTableInfos = this.parse(
				queryParam.getClasses(), queryParam.getCtxMap());
		return this.getListBySQL(partitionTableInfos[0].getDsKey(), SqlBuilder
				.createListSQL(partitionTableInfos, queryParam.getColumns(),
						queryParam.getWhere(), queryParam.getOrder()),
				queryParam.getParams(), queryParam.getBegin(), queryParam
						.getSize(), mapper);
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

	public <T> int count(CountParam countParam) {
		PartitionTableInfo[] partitionTableInfos = this.parse(
				countParam.getClasses(), countParam.getCtxMap());
		return this.countBySQL(
				partitionTableInfos[0].getDsKey(),
				SqlBuilder.createCountSQL(partitionTableInfos,
						countParam.getWhere()), countParam.getParams());
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
		PartitionTableInfo[] partitionTableInfos = this.parse(
				queryParam.getClasses(), queryParam.getCtxMap());
		return this.getObjectBySQL(partitionTableInfos[0].getDsKey(),
				SqlBuilder.createObjectSQL(partitionTableInfos,
						queryParam.getColumns(), queryParam.getWhere(),
						queryParam.getOrder()), queryParam.getParams(), mapper);
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
		queryParam.setWhereAndParams(this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz).getIdColumn() + "=?",
				new Object[] { idValue });
		queryParam.setOrder(null);
		queryParam.setRange(0, 1);
		if (queryParam.getClassCount() == 0) {
			queryParam.addClass(clazz);
		}
		return this.getObject(queryParam, clazz);
	}
}