package halo.dao.query;

import halo.util.NumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不进行分表分库时，用此类进行操作
 * 
 * @author akwei
 */
public class SimpleQuery {

	private HkObjQuery hkObjQuery;

	public void setHkObjQuery(HkObjQuery hkObjQuery) {
		this.hkObjQuery = hkObjQuery;
	}

	public <T> int count(Class<T> clazz, String where, Object[] params) {
		CountParam countParam = new CountParam();
		countParam.addClass(clazz);
		countParam.setWhereAndParams(where, params);
		return this.hkObjQuery.count(countParam);
	}

	public <T> int delete(Class<T> clazz, String where, Object[] params) {
		DeleteParam deleteParam = new DeleteParam(null, null);
		deleteParam.setWhereAndParams(where, params);
		return this.hkObjQuery.delete(deleteParam, clazz);
	}

	public <T> int deleteById(Class<T> clazz, Object idValue) {
		return this.hkObjQuery.deleteById(null, null, clazz, idValue);
	}

	public <T> T getById(Class<T> clazz, Object idValue) {
		return this.hkObjQuery.getObjectById(new QueryParam(null, null), clazz,
				idValue);
	}

	public <T> List<T> getList(Class<T> clazz, String where, Object[] params,
			String order, int begin, int size) {
		QueryParam queryParam = new QueryParam(null, null);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		queryParam.setRange(begin, size);
		return this.hkObjQuery.getList(queryParam, clazz);
	}

	public <E, T> List<T> getListInField(Class<T> clazz, String field,
			List<E> fieldValueList) {
		return this.getListInField(clazz, null, null, field, fieldValueList);
	}

	public <E, T> List<T> getListInField(Class<T> clazz, String where,
			Object[] params, String field, List<E> fieldValueList) {
		if (fieldValueList.isEmpty()) {
			return new ArrayList<T>();
		}
		QueryParam queryParam = new QueryParam(null, null);
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
		List<Object> paramList = new ArrayList<Object>();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				paramList.add(params[i]);
			}
		}
		paramList.addAll(fieldValueList);
		queryParam.setWhereAndParams(sb.toString(),
				paramList.toArray(new Object[paramList.size()]));
		return this.hkObjQuery.getList(queryParam, clazz);
	}

	public <T> T getObject(Class<T> clazz, String where, Object[] params) {
		return this.getObject(clazz, where, params, null);
	}

	public <T> T getObject(Class<T> clazz, String where, Object[] params,
			String order) {
		QueryParam queryParam = new QueryParam(null, null);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		return this.hkObjQuery.getObject(queryParam, clazz);
	}

	public <T> Object save(T t) {
		return this.hkObjQuery.insertObj(null, null, t);
	}

	public <T> Number saveForNumber(T t) {
		return NumberUtil.getNumber(this.save(t));
	}

	public <T> int update(T t) {
		return this.hkObjQuery.updateObj(null, null, t);
	}

	public <T> int updateBySQL(Class<T> clazz, String updateSqlSegment,
			String where, Object[] params) {
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put(null, null);
		PartitionTableInfo partitionTableInfo = this.hkObjQuery.parse(clazz,
				ctxMap);
		StringBuilder sb = new StringBuilder("update ");
		sb.append(partitionTableInfo.getTableName());
		sb.append(" set ").append(updateSqlSegment);
		if (where != null) {
			sb.append(" where ").append(where);
		}
		return this.hkObjQuery.updateBySQL(partitionTableInfo.getDsKey(),
				sb.toString(), params);
	}
}
