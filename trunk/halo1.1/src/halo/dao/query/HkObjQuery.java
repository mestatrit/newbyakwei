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

	/**
	 * insert into sql 操作
	 * 
	 * @param insertParam
	 * @param t
	 *            需要insert的对象
	 * @return mysql中返回自增id，其他数据库暂时返回null
	 */
	@SuppressWarnings("unchecked")
	public <T> Object insertObj(InsertParam insertParam, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		PartitionTableInfo partitionTableInfo = this.parse(t.getClass(),
				insertParam.getCtxMap());
		return this.insertBySQL(partitionTableInfo.getDsKey(),
				SqlBuilder.createInsertSQL(partitionTableInfo,
						objectSqlInfo.getColumns()), objectSqlInfo
						.getSqlUpdateMapper().getParamsForInsert(t));
	}

	/**
	 * @param key
	 *            分区关键字
	 * @param keyValue
	 *            分区关键字对应的值
	 * @param t
	 *            需要insert的对象
	 * @return mysql中返回自增id,其他数据库中返回值暂时为null
	 */
	public <T> Object insertObj(String key, Object keyValue, T t) {
		InsertParam insertParam = new InsertParam(key, keyValue);
		return this.insertObj(insertParam, t);
	}

	/**
	 * @param key
	 *            分区关键字
	 * @param keyValue
	 *            分区关键字对应的值
	 * @param t
	 *            需要insert的对象
	 * @return mysql中返回自增id
	 */
	public <T> Number insertObjForNumber(String key, Object keyValue, T t) {
		return NumberUtil.getNumber(this.insertObj(key, keyValue, t));
	}

	public <T> Number insertObjForNumber(InsertParam insertParam, T t) {
		return NumberUtil.getNumber(this.insertObj(insertParam, t));
	}

	/**
	 * update sql操作，update一条记录的所有字段
	 * 
	 * @param key
	 *            分区关键字
	 * @param keyValue
	 *            分区关键字对应的值
	 * @param t
	 *            update 的对象
	 * @return update sql的返回值
	 */
	@SuppressWarnings("unchecked")
	public <T> int updateObj(String key, Object keyValue, T t) {
		ObjectSqlInfo<T> objectSqlInfo = (ObjectSqlInfo<T>) this.objectSqlInfoCreater
				.getObjectSqlInfo(t.getClass());
		UpdateParam updateParam = new UpdateParam(key, keyValue);
		updateParam.setClazz(t.getClass());
		updateParam.set(objectSqlInfo.getColumnsForUpdate(),
				objectSqlInfo.getIdColumn() + "=?", objectSqlInfo
						.getSqlUpdateMapper().getParamsForUpdate(t));
		return this.update(updateParam);
	}

	/**
	 * update sql 操作
	 * 
	 * @param updateParam
	 * @param clazz
	 *            需要update的对象类型
	 * @return
	 */
	public int update(UpdateParam updateParam) {
		PartitionTableInfo partitionTableInfo = this.parse(
				updateParam.getClazz(), updateParam.getCtxMap());
		return this
				.updateBySQL(partitionTableInfo.getDsKey(), SqlBuilder
						.createUpdateSQL(partitionTableInfo,
								updateParam.getUpdateColumns(),
								updateParam.getWhere()), updateParam
						.getParams());
	}

	public int update(UpdateParamBuilder updateParamBuilder) {
		return this.update(updateParamBuilder.create());
	}

	/**
	 * delete sql操作
	 * 
	 * @param deleteParam
	 * @param clazz
	 *            需要delete的对象类型
	 * @return
	 */
	public int delete(DeleteParam deleteParam) {
		PartitionTableInfo partitionTableInfo = this.parse(
				deleteParam.getClazz(), deleteParam.getCtxMap());
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
	 *            需要delete的对象类型
	 * @param idValue
	 * @return
	 */
	public <T> int deleteById(String key, Object keyValue, Class<T> clazz,
			Object idValue) {
		DeleteParam deleteParam = new DeleteParam(key, keyValue);
		deleteParam.setClazz(clazz);
		ObjectSqlInfo<T> objectSqlInfo = this.objectSqlInfoCreater
				.getObjectSqlInfo(clazz);
		deleteParam.set(objectSqlInfo.getIdColumn() + "=?",
				new Object[] { idValue });
		return this.delete(deleteParam);
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

	/**
	 * count sql 操作
	 * 
	 * @param countParam
	 * @return sql结果
	 */
	public int count(CountParam countParam) {
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
	 * 根据id查询对象,默认查询 clazz 对象数据
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
		queryParam.set(this.objectSqlInfoCreater.getObjectSqlInfo(clazz)
				.getIdColumn() + "=?", new Object[] { idValue });
		queryParam.setOrder(null);
		queryParam.setRange(0, 1);
		if (queryParam.getClassCount() == 0) {
			queryParam.addClass(clazz);
		}
		return this.getObject(queryParam, clazz);
	}

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
}