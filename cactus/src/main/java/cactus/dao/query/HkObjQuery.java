package cactus.dao.query;

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

	private ResultSetDataInfoCreater resultSetDataInfoCreater = new ResultSetDataInfoCreater();

	public <T> String getAliasName(Class<T> clazz) {
		return this.getObjectSqlInfoCreater().getObjectSqlInfo(clazz)
				.getTableName();
	}

	/**
	 * 获rowmapper，先从表映射的对象开始匹配，如果没有，就到结果集resultsetdata中进行查找
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		RowMapper<T> mapper = this.getObjectSqlInfoCreater()
				.getRowMapper(clazz);
		if (mapper != null) {
			return mapper;
		}
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

	private ObjectSqlInfoCreater getObjectSqlInfoCreater() {
		return objectSqlInfoCreater;
	}

	/**
	 * 创建参数对象的快捷方法
	 * 
	 * @return
	 */
	public BaseParam createBaseParam() {
		return new BaseParam(this.getObjectSqlInfoCreater());
	}

	public <T> BaseParam createBaseParam(String key, Object value) {
		BaseParam baseParam = this.createBaseParam();
		baseParam.addKeyAndValue(key, value);
		return baseParam;
	}

	/**
	 * 创建参数对象的快捷方法
	 * 
	 * @return
	 */
	public UpdateParam createUpdateParam() {
		return new UpdateParam(this.getObjectSqlInfoCreater());
	}

	public <T> UpdateParam createUpdateParam(String key, Object value) {
		UpdateParam updateParam = this.createUpdateParam();
		updateParam.addKeyAndValue(key, value);
		return updateParam;
	}

	/**
	 * 创建参数对象的快捷方法
	 * 
	 * @return
	 */
	public DeleteParam createDeleteParam() {
		return new DeleteParam(this.getObjectSqlInfoCreater());
	}

	public <T> DeleteParam createDeleteParam(String key, Object value) {
		DeleteParam deleteParam = this.createDeleteParam();
		deleteParam.addKeyAndValue(key, value);
		return deleteParam;
	}

	/**
	 * 创建参数对象的快捷方法
	 * 
	 * @return
	 */
	public QueryParam createQueryParam() {
		return new QueryParam(this.getObjectSqlInfoCreater());
	}

	public <T> QueryParam createQueryParam(String key, Object value) {
		QueryParam queryParam = this.createQueryParam();
		queryParam.addKeyAndValue(key, value);
		return queryParam;
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
	public <T> Object insertObj(BaseParam baseParam, T t) {
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
		updateParam.setUpdateColumns(objectSqlInfo.getColumnsForUpdate());
		updateParam.setParams(objectSqlInfo.getSqlUpdateMapper()
				.getParamsForUpdate(t));
		return this.update(updateParam, t.getClass());
	}

	public <T> int update(UpdateParam updateParam, Class<T> clazz) {
		return this.update(this.parse(clazz, updateParam.getCtxMap()),
				updateParam.getUpdateColumns(), updateParam.getWhere(),
				updateParam.getParams());
	}

	public <T> int delete(DeleteParam deleteParam, Class<T> clazz) {
		return this.delete(this.parse(clazz, deleteParam.getCtxMap()),
				deleteParam.getWhere(), deleteParam.getParams());
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
	public <T> int deleteObj(BaseParam baseParam, T t) {
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
	public <T> int deleteById(BaseParam baseParam, Class<T> clazz,
			Object idValue) {
		DeleteParam deleteParam = new DeleteParam(baseParam
				.getObjectSqlInfoCreater());
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
		return this.getList(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), queryParam
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
		return this.count(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getWhere(), queryParam.getParams());
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
		return this.getObject(this.parse(queryParam.getClasses(), queryParam
				.getCtxMap()), queryParam.getColumns(), queryParam.getWhere(),
				queryParam.getParams(), queryParam.getOrder(), mapper);
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
		if (queryParam.getClasses().length == 0) {
			queryParam.addClass(clazz);
		}
		return this.getObject(queryParam, clazz);
	}
}