package com.dev3g.cactus.dao.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev3g.cactus.dao.query.param.DeleteParam;
import com.dev3g.cactus.dao.query.param.InsertParam;
import com.dev3g.cactus.dao.query.param.QueryParam;
import com.dev3g.cactus.dao.query.param.UpdateParam;

public abstract class BaseDao<T> implements IDao<T> {

	private static final String empty_key = "";

	@Autowired
	public HkObjQuery hkObjQuery;

	@Override
	public int count(Object keyValue, String where, Object[] params) {
		QueryParam queryParam = new QueryParam();
		queryParam.addKeyAndValue(getKey(), keyValue);
		queryParam.addClass(getClazz());
		queryParam.setWhereAndParams(where, params);
		return this.hkObjQuery.count(queryParam);
	}

	@Override
	public int count(String where, Object[] params) {
		return this.count(null, where, params);
	}

	private QueryParam createQueryParam(Object value) {
		return new QueryParam(getKey(), value);
	}

	private UpdateParam createUpdateParam(Object value) {
		return new UpdateParam(getKey(), value);
	}

	@Override
	public int delete(Object keyValue, String where, Object[] params) {
		DeleteParam deleteParam = new DeleteParam();
		deleteParam.addKeyAndValue(getKey(), keyValue);
		deleteParam.setWhereAndParams(where, params);
		return this.hkObjQuery.delete(deleteParam, getClazz());
	}

	/**
	 * 删除对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	@Override
	public int delete(Object keyValue, T t) {
		DeleteParam deleteParam = new DeleteParam(getKey(), keyValue);
		return this.hkObjQuery.deleteObj(deleteParam, t);
	}

	@Override
	public int delete(String where, Object[] params) {
		return this.delete(null, where, params);
	}

	@Override
	public int delete(T t) {
		return this.delete(null, t);
	}

	@Override
	public int deleteById(Object idValue) {
		return this.deleteById(null, idValue);
	}

	@Override
	public int deleteById(Object keyValue, Object idValue) {
		DeleteParam deleteParam = new DeleteParam(getKey(), keyValue);
		return this.hkObjQuery.deleteById(deleteParam, getClazz(), idValue);
	}

	@Override
	public T getById(Object idValue) {
		return this.getById(null, idValue);
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param idValue
	 * @return
	 */
	@Override
	public T getById(Object keyValue, Object idValue) {
		return this.hkObjQuery.getObjectById(this.createQueryParam(keyValue),
				getClazz(), idValue);
	}

	public abstract Class<T> getClazz();

	public String getKey() {
		return empty_key;
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
	@Override
	public List<T> getList(Object keyValue, String where, Object[] params,
			String order, int begin, int size) {
		QueryParam queryParam = this.createQueryParam(keyValue);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		queryParam.setRange(begin, size);
		return this.hkObjQuery.getList(queryParam, getClazz());
	}

	@Override
	public List<T> getList(String where, Object[] params, String order,
			int begin, int size) {
		return this.getList(null, where, params, order, begin, size);
	}

	@Override
	public <E> List<T> getListInField(Object keyValue, String field,
			List<E> fieldValueList) {
		return this.getListInField(keyValue, null, null, field, fieldValueList);
	}

	@Override
	public <E> List<T> getListInField(Object keyValue, String where,
			Object[] params, String field, List<E> fieldValueList) {
		if (fieldValueList.isEmpty()) {
			return new ArrayList<T>();
		}
		QueryParam queryParam = this.createQueryParam(keyValue);
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
	public T getObject(Object keyValue, String where, Object[] params) {
		return this.getObject(keyValue, where, params, null);
	}

	@Override
	public T getObject(Object keyValue, String where, Object[] params,
			String order) {
		QueryParam queryParam = this.createQueryParam(keyValue);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		return this.hkObjQuery.getObject(queryParam, getClazz());
	}

	@Override
	public T getObject(String where, Object[] params) {
		return this.getObject(null, where, params);
	}

	@Override
	public T getObject(String where, Object[] params, String order) {
		return this.getObject(null, where, params, order);
	}

	/**
	 * 创建对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	@Override
	public Object save(Object keyValue, T t) {
		InsertParam insertParam = new InsertParam(this.getKey(), keyValue);
		return this.hkObjQuery.insertObj(insertParam, t);
	}

	@Override
	public Object save(T t) {
		return this.save(null, t);
	}

	public void setHkObjQuery(HkObjQuery hkObjQuery) {
		this.hkObjQuery = hkObjQuery;
	}

	/**
	 * 更新对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	@Override
	public int update(Object keyValue, T t) {
		return this.hkObjQuery.updateObj(this.createUpdateParam(keyValue), t);
	}

	@Override
	public int update(T t) {
		return this.update(null, t);
	}

	@Override
	public int updateBySQL(Object keyValue, String updateSqlSegment,
			String where, Object[] params) {
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put(getKey(), keyValue);
		PartitionTableInfo partitionTableInfo = this.hkObjQuery.parse(
				getClazz(), ctxMap);
		StringBuilder sb = new StringBuilder("update ");
		sb.append(partitionTableInfo.getTableName());
		sb.append(" set ").append(updateSqlSegment);
		sb.append(" where ").append(where);
		return this.hkObjQuery.updateBySQL(
				partitionTableInfo.getDsKey(), sb.toString(), params);
	}

	@Override
	public int updateBySQL(String updateSqlSegment, String where,
			Object[] params) {
		return this.updateBySQL(null, updateSqlSegment, where, params);
	}
}