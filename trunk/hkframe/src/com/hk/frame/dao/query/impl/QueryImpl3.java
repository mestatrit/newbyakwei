package com.hk.frame.dao.query.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.hk.frame.dao.DaoDebugMode;
import com.hk.frame.dao.query.Cache;
import com.hk.frame.dao.query.ObjectSqlData;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.dao.query.TableHelper;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.criteria.TableField;
import com.hk.frame.datasource.DataSourceStatus;
import com.hk.frame.util.DataUtil;

public class QueryImpl3 implements Query {

	private final Log log = LogFactory.getLog(QueryImpl3.class);

	private StringBuilder builder = new StringBuilder();

	private QueryManager queryManager;

	private int begin;

	private int size;

	private String fieldSql;

	private String orderBySql;

	private String whereSql;

	private String whereSql2 = null;

	private String groupBySql;

	private Object[] values;

	/**
	 * insert,update字段表达式的集合(update insert操作时，会有数据)
	 */
	private final List<Expression> updateFieldlist = new ArrayList<Expression>();

	/**
	 * 字段表达式的集合
	 */
	private final List<Expression> fieldlist = new ArrayList<Expression>();

	/**
	 * 字段值的集合,用来存放insert update的字段
	 */
	private List<Object> updateFieldParamList = new ArrayList<Object>();

	/**
	 * 条件表达式值的集合,用来存放where 表达式中的参数值
	 */
	private List<Object> whereParamList = new ArrayList<Object>();

	/**
	 * 操作表的集合
	 */
	private List<QueryTable> table = new ArrayList<QueryTable>();

	RowMapper<Object[]> mapMapper = new RowMapper<Object[]>() {

		public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
			ResultSetMetaData rsmd = rs.getMetaData();
			int column_count = rsmd.getColumnCount();
			Object[] obj = new Object[column_count];
			for (int i = 1; i <= column_count; i++) {
				obj[i - 1] = rs.getObject(i);
			}
			return obj;
		}
	};

	public Query addField(String field, Object value) {
		return this.addField(new TableField(field, "=", value));
	}

	public Query addField(String field, String op, Object value) {
		return this.addField(new TableField(field, op, value));
	}

	/** ****添加字段***** */
	private Query addField(TableField field) {
		this.updateFieldlist.add(field);
		this.updateFieldParamList.add(field.getValue());
		return this;
	}

	public int[] batchUpdateBySql(String ds, String sql,
			BatchPreparedStatementSetter bpss) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().batchUpdate(sql, bpss);
	}

	private void buildParameter() {
		this.fieldlist.addAll(updateFieldlist);
		// 聚合条件表达式字段以及值
		if (this.whereSql != null) {
			this.fieldlist.addAll(TableHelper.createExpressionList(
					this.whereSql, this.whereParamList));
		}
		// 聚合所有参数
		this.updateFieldParamList.addAll(this.whereParamList);
		this.values = updateFieldParamList
				.toArray(new Object[updateFieldParamList.size()]);
	}

	private StringBuilder buildShowField() {
		StringBuilder sb = new StringBuilder();
		if (this.fieldSql != null) {
			sb.append(this.fieldSql);
		}
		else {
			sb.append("*");
		}
		return sb;
	}

	private StringBuilder buildTableSql() {
		DataSourceStatus.setCurrentDsName(TableHelper.createDataSourceName(
				table, fieldlist, queryManager));
		StringBuilder sb = new StringBuilder();
		if (table.size() == 1) {
			return sb.append(table.iterator().next().getName());
		}
		for (QueryTable t : table) {
			sb.append(t.getName()).append(" ").append(t.getAlias()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb;
	}

	/**
	 * 创建where 条件语句
	 * 
	 * @return
	 */
	private StringBuilder buildWhere() {
		if (DataUtil.isEmpty(this.whereSql) && DataUtil.isEmpty(this.whereSql2)) {
			return new StringBuilder("");
		}
		StringBuilder sb = new StringBuilder(" where 1=1");
		if (!DataUtil.isEmpty(this.whereSql)) {
			sb.append(" and ").append(this.whereSql);
		}
		if (!DataUtil.isEmpty(this.whereSql2)) {
			sb.append(" and ").append(this.whereSql2);
		}
		return sb;
	}

	public <T> void clearCacheObject(Class<T> clazz, Object idValue) {
		Cache cache = this.getQueryManager().getCacheFromCacheMap(
				clazz.getName());
		if (cache != null) {
			cache.remove(idValue);
		}
	}

	public int count() {
		this.buildParameter();
		this.builder.append("select count(*) from ").append(
				this.buildTableSql()).append(this.buildWhere());
		logSql();
		int n = this.queryManager.getHkDaoSupport().queryForNumber(
				this.builder.toString(), values).intValue();
		this.reset();
		return n;
	}

	public <T> int count(Class<T> clazz) {
		return this.count(clazz, null, null);
	}

	public <T> int count(Class<T> clazz, String whereExpression, Object[] param) {
		this.setTable(clazz);
		if (whereExpression != null) {
			this.where(whereExpression);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					this.setParam(param[i]);
				}
			}
		}
		return this.count();
	}

	public int countBySql(String ds, String sql, List<Object> olist) {
		return this
				.countBySql(ds, sql, olist.toArray(new Object[olist.size()]));
	}

	public int countBySql(String ds, String sql, Object... value) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().queryForNumber(sql, value)
				.intValue();
	}

	public <T> int countExParamList(Class<T> clazz, String whereExpression,
			List<Object> paramList) {
		return this.count(clazz, whereExpression, paramList
				.toArray(new Object[paramList.size()]));
	}

	public Object[] data(String ds, String sql, Object... param) {
		DataSourceStatus.setCurrentDsName(ds);
		List<Object[]> list = this.listdata(ds, sql, 0, 1, param);
		return this.getSingleResult(list);
	}

	public int delete() {
		this.buildParameter();
		this.builder.append("delete from ").append(this.buildTableSql())
				.append(this.buildWhere());
		logSql();
		int n = this.queryManager.getHkDaoSupport().update(
				this.builder.toString(), values);
		this.reset();
		return n;
	}

	public <T> int delete(Class<T> clazz, String whereExpression, Object[] param) {
		this.setTable(clazz);
		this.where(whereExpression);
		if (param != null) {
			for (Object o : param) {
				this.setParam(o);
			}
		}
		return this.delete();
	}

	@Override
	public <T> int delete(Class<T> clazz) {
		this.setTable(clazz);
		return this.delete();
	}

	@Override
	public <T> int deleteById(Class<T> clazz, Object idValue) {
		return this.delete(clazz, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { idValue });
	}

	@Override
	public int deleteObject(Object obj) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		try {
			return this.delete(obj.getClass(), objectSqlData.getIdColumn()
					+ "=?",
					new Object[] { objectSqlData.getIdField().get(obj) });
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T getObject(Class<T> clazz) {
		List<T> list = this.list(0, 1, clazz);
		return this.getSingleResult(list);
	}

	public <T> T getObject(Class<T> clazz, String whereExpression,
			Object[] param, String orderBySql) {
		this.setTable(clazz);
		if (whereExpression != null) {
			this.where(whereExpression);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					this.setParam(param[i]);
				}
			}
		}
		if (orderBySql != null) {
			this.orderBy(orderBySql);
		}
		return this.getObject(clazz);
	}

	public <T> T getObjectById(Class<T> clazz, Object value) {
		return this.getObjectEx(clazz, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { value });
	}

	public <T> T getObjectEx(Class<T> clazz, String whereExpression,
			Object[] param) {
		return this.getObject(clazz, whereExpression, param, null);
	}

	public QueryManager getQueryManager() {
		return queryManager;
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
		return this.queryManager.getObjectSqlData(clazz.getName())
				.getTableName();
	}

	private Number insert() {
		this.buildParameter();
		this.builder.append("insert into ").append(this.buildTableSql())
				.append("(");
		for (Expression field : updateFieldlist) {
			this.builder.append(field.getField()).append(",");
		}
		this.builder.deleteCharAt(this.builder.length() - 1);
		this.builder.append(") values(");
		for (int i = 0; i < updateFieldlist.size(); i++) {
			this.builder.append("?,");
		}
		this.builder.deleteCharAt(this.builder.length() - 1);
		this.builder.append(")");
		logSql();
		Number n = this.queryManager.getHkDaoSupport().insertObject(
				this.builder.toString(), values);
		this.reset();
		return n;
	}

	public <T> Number insert(Class<T> clazz) {
		this.setTable(clazz);
		return this.insert();
	}

	public Number insertObject(Object obj) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		if (objectSqlData == null) {
			throw new RuntimeException("no table config");
		}
		try {
			Field idField = objectSqlData.getIdField();
			if (idField != null) {
				this.addField(objectSqlData.getColumn(idField.getName()),
						idField.get(obj));
			}
			for (Field f : objectSqlData.getFieldList()) {
				this.addField(objectSqlData.getColumn(f.getName()), f.get(obj));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.insert(obj.getClass());
	}

	private <T> List<T> list(Class<T> clazz) {
		return this.list(clazz, this.getQueryManager().getRowMapper(clazz));
	}

	public <T> List<T> list(int begin, int size, Class<T> clazz) {
		this.setBegin(begin);
		this.setSize(size);
		return this.list(clazz);
	}

	public <T> List<T> listEx(Class<T> clazz, String whereExpression,
			Object[] param, String orderBySql) {
		this.setTable(clazz);
		if (whereExpression != null) {
			this.where(whereExpression);
		}
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				this.setParam(param[i]);
			}
		}
		if (orderBySql != null) {
			this.orderBy(orderBySql);
		}
		return this.list(clazz);
	}

	private <T> List<T> list(Class<T> clazz, RowMapper<T> mapper) {
		this.buildParameter();
		this.builder.append("select ").append(this.buildShowField());
		this.builder.append(" from ").append(this.buildTableSql()).append(
				this.buildWhere());
		if (this.orderBySql != null) {
			this.builder.append(" order by ").append(this.orderBySql);
		}
		if (this.groupBySql != null) {
			this.builder.append(" group by ").append(groupBySql);
		}
		logSql();
		List<T> list = null;
		if (mapper != null) {
			list = this.queryManager.getHkDaoSupport().query(
					this.builder.toString(), begin, size, mapper, values);
		}
		else {
			list = this.queryManager.getHkDaoSupport().queryFunc(
					this.builder.toString(), begin, size, clazz,
					this.queryManager.getObjectSqlData(clazz.getName()),
					mapper, values);
		}
		this.reset();
		return list;
	}

	public <T> List<T> listBySql(String ds, String sql, int begin, int size,
			Class<T> clazz, Object... values) {
		RowMapper<T> mapper = this.getQueryManager().getRowMapper(clazz);
		return this.listBySqlWithMapper(ds, sql, begin, size, mapper, values);
	}

	public <T> List<T> listBySql(String ds, String sql, RowMapper<T> rm,
			Object... values) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport()
				.query(sql, 0, -1, rm, values);
	}

	public <T> List<T> listBySqlEx(String ds, String sql, Class<T> clazz,
			Object... values) {
		RowMapper<T> mapper = this.getQueryManager().getRowMapper(clazz);
		return this.listBySql(ds, sql, mapper, values);
	}

	public <T> List<T> listBySqlParamList(String ds, String sql,
			Class<T> clazz, List<Object> olist) {
		return this.listBySqlEx(ds, sql, clazz, olist.toArray(new Object[olist
				.size()]));
	}

	public <T> List<T> listBySqlParamList(String ds, String sql, int begin,
			int size, Class<T> clazz, List<Object> olist) {
		return this.listBySql(ds, sql, begin, size, clazz, olist
				.toArray(new Object[olist.size()]));
	}

	public <T> List<T> listBySqlWithMapper(String ds, String sql, int begin,
			int size, RowMapper<T> rm, Object... values) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().query(sql, begin, size, rm,
				values);
	}

	public List<Object[]> listdata(String ds, String sql, int begin, int size,
			Object... param) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().query(sql, begin, size,
				mapMapper, param);
	}

	public List<Object[]> listdata(String ds, String sql, Object... param) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().query(sql, 0, -1, mapMapper,
				param);
	}

	public <T> List<T> listEx(Class<T> clazz) {
		return this.listEx(clazz, null, null, null);
	}

	public <T> List<T> listEx(Class<T> clazz, int begin, int size) {
		return this.listEx(clazz, null, null, null, begin, size);
	}

	public <T> List<T> listEx(Class<T> clazz, String orderBySql) {
		return this.listEx(clazz, null, null, orderBySql);
	}

	public <T> List<T> listEx(Class<T> clazz, String orderBySql, int begin,
			int size) {
		this.setTable(clazz);
		if (orderBySql != null) {
			this.orderBy(orderBySql);
		}
		return this.list(begin, size, clazz);
	}

	public <T> List<T> listEx(Class<T> clazz, String whereExpression,
			Object[] param) {
		return this.listEx(clazz, whereExpression, param, null);
	}

	public <T> List<T> listEx(Class<T> clazz, String whereExpression,
			Object[] param, int begin, int size) {
		return this.listEx(clazz, whereExpression, param, null, begin, size);
	}

	public <T> List<T> listEx(Class<T> clazz, String whereExpression,
			Object[] param, String orderBySql, int begin, int size) {
		this.setTable(clazz);
		if (whereExpression != null) {
			this.where(whereExpression);
		}
		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				this.setParam(param[i]);
			}
		}
		if (orderBySql != null) {
			this.orderBy(orderBySql);
		}
		return this.list(begin, size, clazz);
	}

	public <T> List<T> listExParamList(Class<T> clazz, String whereExpression,
			List<Object> paramList, String orderBySql, int begin, int size) {
		this.setTable(clazz);
		this.where(whereExpression);
		int i = 0;
		for (Object p : paramList) {
			this.setParam(p);
			i++;
		}
		if (orderBySql != null) {
			this.orderBy(orderBySql);
		}
		return this.list(begin, size, clazz);
	}

	public <T, E> List<T> listInField(Class<T> clazz, String whereExpression,
			Object[] param, String field, List<E> idList, String orderBySql) {
		if (idList.size() == 0) {
			return new ArrayList<T>();
		}
		StringBuilder sql = new StringBuilder();
		sql.append(field).append(" in (");
		for (E o : idList) {
			sql.append(o.toString()).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		this.whereSql2 = sql.toString();
		return this.listEx(clazz, whereExpression, param, orderBySql);
	}

	public <T, E> List<T> listNotInField(Class<T> clazz,
			String whereExpression, Object[] param, String field,
			List<E> fieldValueList, String orderBySql, int begin, int size) {
		if (fieldValueList.size() == 0) {
			return this.listEx(clazz, whereExpression, param, orderBySql,
					begin, size);
		}
		StringBuilder sql = new StringBuilder();
		sql.append(field).append(" not in (");
		for (E o : fieldValueList) {
			sql.append(o.toString()).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		this.whereSql2 = sql.toString();
		return this.listEx(clazz, whereExpression, param, orderBySql, begin,
				size);
	}

	private void logSql() {
		if (DaoDebugMode.isSqlDeubg()) {
			log.info("query ::: " + this.builder.toString());
		}
	}

	public Query orderBy(String orderBySql) {
		this.orderBySql = orderBySql;
		return this;
	}

	public Query orderByAsc(String orderBySql) {
		this.orderBySql = orderBySql + " asc";
		return this;
	}

	public Query orderByDesc(String orderBySql) {
		this.orderBySql = orderBySql + " desc";
		return this;
	}

	public Number queryForNumberBySql(String ds, String sql, Object... values) {
		DataSourceStatus.setCurrentDsName(ds);
		return this.queryManager.getHkDaoSupport().queryForNumber(sql, values);
	}

	private void reset() {
		builder.delete(0, builder.length());
		begin = 0;
		size = 0;
		fieldSql = null;
		orderBySql = null;
		whereSql = null;
		groupBySql = null;
		values = null;
		updateFieldlist.clear();
		// whereFieldlist.clear();
		fieldlist.clear();
		updateFieldParamList.clear();
		whereParamList.clear();
		table.clear();
	}

	public Query setBegin(int begin) {
		this.begin = begin;
		return this;
	}

	public Query setParam(Object value) {
		this.whereParamList.add(value);
		return this;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public Query setShowFields(String fieldSql) {
		this.fieldSql = fieldSql;
		return this;
	}

	private Query setSize(int size) {
		this.size = size;
		return this;
	}

	public <T> Query setTable(Class<T> clazz) {
		this.table.add(new QueryTable(getTableNameFromClass(clazz)));
		return this;
	}

	public <T> Query setTable(Class<T> clazz, String alias) {
		this.table.add(new QueryTable(getTableNameFromClass(clazz), alias));
		return this;
	}

	public Query setTable(String tableSql) {
		this.table.addAll(TableHelper.parseQueryTableList(tableSql));
		return this;
	}

	public Number sum(String field) {
		this.buildParameter();
		this.builder.append("select sum(").append(field).append(") from ")
				.append(this.buildTableSql()).append(this.buildWhere());
		logSql();
		Number n = this.queryManager.getHkDaoSupport().queryForNumber(
				this.builder.toString(), values);
		this.reset();
		return n;
	}

	public <T> Number sum(String field, Class<T> clazz, String whereExpression,
			Object[] objs) {
		this.setTable(clazz);
		this.where(whereExpression);
		if (objs != null) {
			for (Object o : objs) {
				this.setParam(o);
			}
		}
		Number n = this.sum(field);
		if (n == null) {
			return 0;
		}
		return n;
	}

	public int update() {
		this.buildParameter();
		this.builder.append("update ").append(this.buildTableSql()).append(
				" set ");
		for (Expression field : updateFieldlist) {
			this.builder.append(field.getField()).append(field.getOp()).append(
					"?,");
		}
		this.builder.deleteCharAt(this.builder.length() - 1);
		this.builder.append(this.buildWhere());
		logSql();
		int n = this.queryManager.getHkDaoSupport().update(
				this.builder.toString(), values);
		this.reset();
		return n;
	}

	public <T> int update(Class<T> clazz, String whereExpression, Object[] param) {
		this.setTable(clazz);
		this.where(whereExpression);
		if (param != null) {
			for (Object o : param) {
				this.setParam(o);
			}
		}
		return this.update();
	}

	public <T> int updateById(Class<T> clazz, Object id) {
		return this.update(clazz, this.queryManager.getObjectSqlData(
				clazz.getName()).getIdColumn()
				+ "=?", new Object[] { id });
	}

	public int updateObject(Object obj) {
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		Field idField = objectSqlData.getIdField();
		if (idField == null) {
			throw new RuntimeException("no id [ " + obj.getClass().getName()
					+ " ]");
		}
		try {
			for (Field f : objectSqlData.getFieldList()) {
				if (f != idField) {
					this.addField(objectSqlData.getColumn(f.getName()), f
							.get(obj));
				}
			}
			return this.updateById(obj.getClass(), idField.get(obj));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateObjectExcept(Object obj, String[] param) {
		Set<String> set = new HashSet<String>();
		for (String s : param) {
			set.add(s);
		}
		ObjectSqlData objectSqlData = this.queryManager.getObjectSqlData(obj
				.getClass().getName());
		Field idField = objectSqlData.getIdField();
		if (idField == null) {
			throw new RuntimeException("no id [ " + obj.getClass().getName()
					+ " ]");
		}
		try {
			String colum = null;
			for (Field f : objectSqlData.getFieldList()) {
				if (f != idField) {
					colum = objectSqlData.getColumn(f.getName());
					if (!set.contains(colum)) {
						this.addField(objectSqlData.getColumn(f.getName()), f
								.get(obj));
					}
				}
			}
			return this.updateById(obj.getClass(), idField.get(obj));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Query where(String whereExpression) {
		this.whereSql = whereExpression;
		return this;
	}
}