package cactus.dao.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T> implements IDao<T> {

	private final String empty_key = "";

	public HkObjQuery hkObjQuery;

	public void setHkObjQuery(HkObjQuery hkObjQuery) {
		this.hkObjQuery = hkObjQuery;
	}

	public String getKey() {
		return empty_key;
	}

	public abstract Class<T> getClazz();

	public BaseParam createBaseParam(Object value) {
		return hkObjQuery.createBaseParam(getClazz(), getKey(), value);
	}

	public QueryParam createQueryParam(Object value) {
		return hkObjQuery.createQueryParam(getClazz(), getKey(), value);
	}

	public UpdateParam createUpdateParam(Object value) {
		return hkObjQuery.createUpdateParam(getClazz(), getKey(), value);
	}

	// ********************* 提供公用方法 **************************/
	/**
	 * 创建对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	public Object save(Object keyValue, T t) {
		BaseParam baseParam = this.hkObjQuery.createBaseParam(this.getClazz(),
				this.getKey(), keyValue);
		return this.hkObjQuery.insertObj(baseParam, t);
	}

	@Override
	public Object save(T t) {
		return this.save(null, t);
	}

	/**
	 * 更新对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	public int update(Object keyValue, T t) {
		BaseParam baseParam = this.hkObjQuery.createBaseParam(this.getClazz(),
				this.getKey(), keyValue);
		return this.hkObjQuery.updateObj(baseParam, t);
	}

	@Override
	public int update(T t) {
		return this.update(null, t);
	}

	public int updateBySQL(Object keyValue, String updateSqlSegment,
			String where, Object[] params) {
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put(this.hkObjQuery.getAliasName(getClazz()) + "." + getKey(),
				keyValue);
		PartitionTableInfo partitionTableInfo = this.hkObjQuery.parse(
				getClazz(), ctxMap);
		StringBuilder sb = new StringBuilder("update ");
		sb.append(partitionTableInfo.getTableName());
		sb.append(" set ").append(updateSqlSegment);
		sb.append(" where ").append(where);
		return this.hkObjQuery.updateBySQL(partitionTableInfo, sb.toString(),
				params);
	}

	/**
	 * 删除对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	public int delete(Object keyValue, T t) {
		return this.hkObjQuery.deleteObj(this.hkObjQuery.createBaseParam(this
				.getClazz(), this.getKey(), keyValue), t);
	}

	@Override
	public int delete(T t) {
		return this.delete(null, t);
	}

	@Override
	public int deleteById(Object keyValue, Object idValue) {
		return this.hkObjQuery.deleteById(this.hkObjQuery.createBaseParam(this
				.getClazz(), this.getKey(), keyValue), getClazz(), idValue);
	}

	@Override
	public int deleteById(Object idValue) {
		return this.deleteById(null, idValue);
	}

	public int delete(Object keyValue, String where, Object[] params) {
		DeleteParam deleteParam = this.hkObjQuery.createDeleteParam(this
				.getClazz(), this.getKey(), keyValue);
		deleteParam.setWhereAndParams(where, params);
		return this.hkObjQuery.delete(deleteParam, getClazz());
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param idValue
	 * @return
	 */
	public T getById(Object keyValue, Object idValue) {
		return this.hkObjQuery.getObjectById(this.hkObjQuery.createQueryParam(
				getClazz(), getKey(), keyValue), getClazz(), idValue);
	}

	@Override
	public T getById(Object idValue) {
		return this.getById(null, idValue);
	}

	/**
	 * @param keyValue
	 *            分区关键值
	 * @param where
	 *            条件sql片段
	 * @param params
	 *            sql对应参数
	 * @param order
	 *            排序sql片段
	 * @param begin
	 *            开始记录位置
	 * @param size
	 *            <0时，取所有符合条件数据
	 * @return
	 */
	public List<T> getList(Object keyValue, String where, Object[] params,
			String order, int begin, int size) {
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		queryParam.setRange(begin, size);
		return this.hkObjQuery.getList(queryParam, getClazz());
	}

	public <E> List<T> getListInField(Object keyValue, String field,
			List<E> fieldValueList) {
		return this.getListInField(keyValue, null, null, field, fieldValueList);
	}

	public <E> List<T> getListInField(Object keyValue, String where,
			Object[] params, String field, List<E> fieldValueList) {
		if (fieldValueList.isEmpty()) {
			return new ArrayList<T>();
		}
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		StringBuilder sb = new StringBuilder();
		if (where != null) {
			sb.append(where).append(" and ");
		}
		sb.append(field);
		sb.append(" in (");
		int len = fieldValueList.size();
		for (int i = 0; i < len; i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		queryParam.setWhere(sb.toString());
		List<Object> paramList = new ArrayList<Object>();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				paramList.add(params[i]);
			}
		}
		paramList.addAll(fieldValueList);
		queryParam.setParams(paramList.toArray(new Object[paramList.size()]));
		return this.hkObjQuery.getList(queryParam, getClazz());
	}

	public int count(Object keyValue, String where, Object[] params) {
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		queryParam.addClass(getClazz());
		queryParam.setWhereAndParams(where, params);
		return this.hkObjQuery.count(queryParam);
	}

	public T getObject(Object keyValue, String where, Object[] params,
			String order) {
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		return this.hkObjQuery.getObject(queryParam, getClazz());
	}

	public T getObject(Object keyValue, String where, Object[] params) {
		return this.getObject(keyValue, where, params, null);
	}

	@Override
	public T getObject(String where, Object[] params) {
		return this.getObject(null, where, params);
	}

	@Override
	public T getObject(String where, Object[] params, String order) {
		return this.getObject(null, where, params, order);
	}

	public List<T> getList(String where, Object[] params, String order,
			int begin, int size) {
		return this.getList(null, where, params, order, begin, size);
	}

	@Override
	public <E> List<T> getListInField(String field, List<E> fieldValueList) {
		return this.getListInField(null, field, fieldValueList);
	}

	@Override
	public <E> List<T> getListInField(String where, Object[] params,
			String field, List<E> fieldValueList) {
		return this.getListInField(null, where, params, field, fieldValueList);
	}

	@Override
	public int count(String where, Object[] params) {
		return this.count(null, where, params);
	}

	@Override
	public int updateBySQL(String updateSqlSegment, String where,
			Object[] params) {
		return this.updateBySQL(null, updateSqlSegment, where, params);
	}

	@Override
	public int delete(String where, Object[] params) {
		return this.delete(null, where, params);
	}
}