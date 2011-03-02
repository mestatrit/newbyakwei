package com.hk.frame.dao.query.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.hk.frame.dao.query.HkQueryProcessor;
import com.hk.frame.dao.query.IHkQuery;
import com.hk.frame.dao.query.ObjectSqlData;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.dao.query.TableHelper;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.criteria.TableField;
import com.hk.frame.dao.query.partition.PartitionTable;
import com.hk.frame.datasource.DataSourceStatus;

public class HkQuery implements IHkQuery {

	private QueryManager queryManager;

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	private <T> T getSingleResult(List<T> list) {
		if (list.size() == 0) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException("result more than one [ " + list.size()
					+ " ]");
		}
		return list.get(0);
	}

	private <T> String getTableNameFromClass(Class<T> clazz) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(clazz
				.getName());
		if (objectSqlData == null) {
			throw new RuntimeException("no table config [ " + clazz.getName()
					+ " ]");
		}
		return objectSqlData.getTableName();
	}

	private <T> QueryTable getQueryTable(Class<T> clazz) {
		return new QueryTable(getTableNameFromClass(clazz));
	}

	private List<Expression> buildParameter(String where,
			List<Object> paramlist, List<Expression> updateExpressionList) {
		List<Expression> fieldlist = new ArrayList<Expression>();
		if (where != null) {
			fieldlist
					.addAll(TableHelper.createExpressionList(where, paramlist));
		}
		if (updateExpressionList != null && updateExpressionList.size() > 0) {
			fieldlist.addAll(updateExpressionList);
		}
		return fieldlist;
	}

	private String buildTableName(QueryTable t, List<Expression> fieldlist) {
		PartitionTable pt = TableHelper.buildPartitionTable(t, fieldlist,
				queryManager);
		DataSourceStatus.setCurrentDsName(pt.getDatabase());
		return pt.getTable();
	}

	private String buildWhere(String where) {
		if (where != null) {
			return " where 1=1 and " + where;
		}
		return " where 1=1 ";
	}

	private <T> String buildSelectSQL(Class<T> clazz, String where,
			Object[] param, String orderSql) {
		List<Object> paramlist = null;
		if (param != null) {
			paramlist = new ArrayList<Object>();
			for (Object o : param) {
				paramlist.add(o);
			}
		}
		String tmp = this.buildTableName(getQueryTable(clazz), this
				.buildParameter(where, paramlist, null))
				+ this.buildWhere(where);
		if (orderSql == null) {
			tmp += " order by " + orderSql;
		}
		return tmp;
	}

	@Override
	public <T> int count(Class<T> clazz, String where, Object[] param) {
		return this.queryManager.getHkDaoSupport()
				.queryForNumber(
						"select count(*) from "
								+ this
										.buildSelectSQL(clazz, where, param,
												null), param).intValue();
	}

	@Override
	public <T> int countExParamList(Class<T> clazz, String where,
			List<Object> paramList) {
		return this.count(clazz, where, paramList.toArray(new Object[paramList
				.size()]));
	}

	@Override
	public <T> int delete(Class<T> clazz, String where, Object[] params) {
		return this.queryManager.getHkDaoSupport().update(
				"delete from "
						+ this.buildSelectSQL(clazz, where, params, null),
				params);
	}

	@Override
	public int delete(Object obj) {
		try {
			return this.deleteById(obj.getClass(), this.queryManager
					.getObjectSqlData(obj.getClass().getName()).getIdField()
					.get(obj));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> int deleteById(Class<T> clazz, Object idValue) {
		return this.delete(clazz, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { idValue });
	}

	@Override
	public <T> T get(Class<T> clazz, String where, Object[] param,
			String orderBySql) {
		List<T> list = this.list(clazz, where, param, orderBySql, 0, 1);
		return this.getSingleResult(list);
	}

	@Override
	public <T> T getById(Class<T> clazz, Object idValue) {
		return this.get(clazz, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { idValue }, null);
	}

	private <T> String buildInsertSQL(Object obj,
			List<Expression> updateFieldlist) {
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(this.buildTableName(getQueryTable(obj.getClass()),
				updateFieldlist));
		sb.append("(");
		for (Expression field : updateFieldlist) {
			sb.append(field.getField()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") values(");
		for (int i = 0; i < updateFieldlist.size(); i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	@Override
	public Number insert(Object obj) {
		List<Expression> updateFieldlist = new ArrayList<Expression>();
		List<Object> paramList = new ArrayList<Object>();
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		try {
			Field idField = objectSqlData.getIdField();
			if (idField != null) {
				Object value = idField.get(obj);
				updateFieldlist.add(new TableField(objectSqlData.getIdColumn(),
						"=", value));
				paramList.add(value);
			}
			for (Field f : objectSqlData.getFieldList()) {
				Object value = f.get(obj);
				updateFieldlist.add(new TableField(objectSqlData.getColumn(f
						.getName()), "=", value));
				paramList.add(value);
			}
			return this.queryManager.getHkDaoSupport().insertObject(
					this.buildInsertSQL(obj, updateFieldlist),
					paramList.toArray(new Object[paramList.size()]));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> List<T> list(Class<T> clazz, String where, Object[] param,
			String orderBySql, int begin, int size) {
		return this.queryManager.getHkDaoSupport().query(
				"select * from "
						+ this.buildSelectSQL(clazz, where, param, orderBySql),
				begin, size, this.queryManager.getRowMapper(clazz), param);
	}

	@Override
	public <T, E> List<T> listInField(Class<T> clazz, String where,
			Object[] param, String field, List<E> fieldValueList,
			String orderBySql) {
		if (fieldValueList.size() == 0) {
			return new ArrayList<T>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(field).append(" and in (");
		for (E o : fieldValueList) {
			sb.append(o.toString()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		return this.list(clazz, where + sb.toString(), param, orderBySql, 0, 0);
	}

	@Override
	public <T, E> List<T> listNotInField(Class<T> clazz, String where,
			Object[] param, String field, List<E> fieldValueList,
			String orderBySql, int begin, int size) {
		if (fieldValueList.size() == 0) {
			return new ArrayList<T>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(field).append(" and not in (");
		for (E o : fieldValueList) {
			sb.append(o.toString()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		return this.list(clazz, where + sb.toString(), param, orderBySql, 0, 0);
	}

	@Override
	public <T> List<T> listParamList(Class<T> clazz, String where,
			List<Object> paramList, String orderBySql, int begin, int size) {
		return this.list(clazz, where, paramList.toArray(new Object[paramList
				.size()]), orderBySql, begin, size);
	}

	@Override
	public <T> T process(HkQueryProcessor hkQueryProcessor) {
		return hkQueryProcessor.execute();
	}

	@Override
	public <T> int sum(Class<T> clazz, String field, String where,
			Object[] params) {
		return this.queryManager.getHkDaoSupport().queryForNumber(
				"select sum(" + field + ") from "
						+ this.buildSelectSQL(clazz, where, params, null),
				params).intValue();
	}

	private <T> String buildUpdateSQL(Class<T> clazz, String where,
			List<Object> paramlist, List<Expression> updateFieldList) {
		StringBuilder sb = new StringBuilder("update ");
		sb.append(this.buildTableName(this.getQueryTable(clazz), this
				.buildParameter(where, paramlist, updateFieldList)));
		sb.append(" set ");
		for (Expression field : updateFieldList) {
			sb.append(field.getField()).append(field.getOp()).append("?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(this.buildWhere(where));
		return sb.toString();
	}

	private List<Expression> buildExpressionFromMap(Map<String, Object> map) {
		List<Expression> list = new ArrayList<Expression>();
		Set<Entry<String, Object>> set = map.entrySet();
		for (Entry<String, Object> e : set) {
			list.add(new TableField(e.getKey(), "=", e.getValue()));
		}
		return list;
	}

	@Override
	public <T> int update(Class<T> clazz, Map<String, Object> kvMap,
			String where, Object[] params) {
		List<Object> paramlist = new ArrayList<Object>();
		if (params != null) {
			for (Object o : params) {
				paramlist.add(o);
			}
		}
		List<Expression> updateFieldList = this.buildExpressionFromMap(kvMap);
		for (Expression o : updateFieldList) {
			paramlist.add(o.getValue());
		}
		return this.queryManager.getHkDaoSupport().update(
				this.buildUpdateSQL(clazz, where, paramlist, updateFieldList),
				paramlist.toArray(new Object[paramlist.size()]));
	}

	private Map<String, Object> buildMap(Object obj,
			ObjectSqlData objectSqlData, Field idField, String[] exceptField)
			throws Exception {
		Set<String> set = null;
		if (exceptField != null) {
			set = new HashSet<String>();
			for (String s : exceptField) {
				set.add(s);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String colum = null;
		for (Field f : objectSqlData.getFieldList()) {
			if (f != idField) {
				colum = objectSqlData.getColumn(f.getName());
				if (set != null && !set.contains(colum)) {
					map.put(objectSqlData.getColumn(f.getName()), f.get(obj));
				}
			}
		}
		return map;
	}

	@Override
	public int update(Object obj) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		Field idField = objectSqlData.getIdField();
		if (idField == null) {
			throw new RuntimeException("no id [ " + obj.getClass().getName()
					+ " ]");
		}
		try {
			return this.updateById(obj.getClass(), this.buildMap(obj,
					objectSqlData, idField, null), idField.get(obj));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> int updateById(Class<T> clazz, Map<String, Object> kvMap,
			Object id) {
		return this.update(clazz, kvMap, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { id });
	}

	@Override
	public int updateExcept(Object obj, String[] field) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		Field idField = objectSqlData.getIdField();
		if (idField == null) {
			throw new RuntimeException("no id [ " + obj.getClass().getName()
					+ " ]");
		}
		try {
			return this.updateById(obj.getClass(), this.buildMap(obj,
					objectSqlData, idField, field), idField.get(obj));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}