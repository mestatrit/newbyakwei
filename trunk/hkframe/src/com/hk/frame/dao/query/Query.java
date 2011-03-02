package com.hk.frame.dao.query;

import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public interface Query {

	/**
	 * 添加字段
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	Query addField(String field, Object value);

	Query addField(String field, String op, Object value);

	int[] batchUpdateBySql(String ds, String sql,
			BatchPreparedStatementSetter bpss);

	<T> void clearCacheObject(Class<T> clazz, Object idValue);

	/**
	 * 不建议使用，以后要取消
	 * 
	 * @return
	 *         2010-4-24
	 */
	int count();

	<T> int count(Class<T> clazz);

	<T> int count(Class<T> clazz, String whereExpression, Object[] param);

	int countBySql(String ds, String sql, List<Object> olist);

	int countBySql(String ds, String sql, Object... value);

	<T> int countExParamList(Class<T> clazz, String whereExpression,
			List<Object> paramList);

	Object[] data(String ds, String sql, Object... param);

	/**
	 * 不建议使用，以后要取消
	 * 
	 * @return
	 *         2010-4-24
	 */
	int delete();

	<T> int delete(Class<T> clazz, String whereExpression, Object[] param);

	<T> int delete(Class<T> clazz);

	<T> int deleteById(Class<T> clazz, Object idValue);

	int deleteObject(Object obj);

	/**
	 * 不建议使用，以后要取消
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 *         2010-4-24
	 */
	<T> T getObject(Class<T> clazz);

	<T> T getObject(Class<T> clazz, String whereExpression, Object[] param,
			String orderBySql);

	/**
	 * 根据id获得对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param value
	 * @return
	 */
	<T> T getObjectById(Class<T> clazz, Object value);

	<T> T getObjectEx(Class<T> clazz, String whereExpression, Object[] param);

	<T> Number insert(Class<T> clazz);

	Number insertObject(Object obj);

	/**
	 * @param <T>
	 * @param begin
	 * @param size
	 * @param clazz
	 * @return
	 *         2010-4-24
	 */
	<T> List<T> list(int begin, int size, Class<T> clazz);

	<T> List<T> listBySql(String ds, String sql, int begin, int size,
			Class<T> clazz, Object... values);

	<T> List<T> listBySql(String ds, String sql, RowMapper<T> rm,
			Object... values);

	<T> List<T> listBySqlEx(String ds, String sql, Class<T> clazz,
			Object... values);

	<T> List<T> listBySqlParamList(String ds, String sql, Class<T> clazz,
			List<Object> olist);

	<T> List<T> listBySqlParamList(String ds, String sql, int begin, int size,
			Class<T> clazz, List<Object> olist);

	<T> List<T> listBySqlWithMapper(String ds, String sql, int begin, int size,
			RowMapper<T> rm, Object... values);

	List<Object[]> listdata(String ds, String sql, int begin, int size,
			Object... param);

	List<Object[]> listdata(String ds, String sql, Object... param);

	<T> List<T> listEx(Class<T> clazz);

	<T> List<T> listEx(Class<T> clazz, int begin, int size);

	<T> List<T> listEx(Class<T> clazz, String orderBySql);

	<T> List<T> listEx(Class<T> clazz, String orderBySql, int begin, int size);

	<T> List<T> listEx(Class<T> clazz, String whereExpression, Object[] param);

	<T> List<T> listEx(Class<T> clazz, String whereExpression, Object[] param,
			int begin, int size);

	<T> List<T> listEx(Class<T> clazz, String whereExpression, Object[] param,
			String orderBySql);

	<T> List<T> listEx(Class<T> clazz, String whereExpression, Object[] param,
			String orderBySql, int begin, int size);

	<T> List<T> listExParamList(Class<T> clazz, String whereExpression,
			List<Object> paramList, String orderBySql, int begin, int size);

	<T, E> List<T> listInField(Class<T> clazz, String whereExpression,
			Object[] param, String field, List<E> fieldValueList,
			String orderBySql);

	<T, E> List<T> listNotInField(Class<T> clazz, String whereExpression,
			Object[] param, String field, List<E> fieldValueList,
			String orderBySql, int begin, int size);

	/**
	 * 添加order by语句,不建议使用，以后要取消
	 * 
	 * @param orderBySql
	 * @return
	 */
	Query orderBy(String orderBySql);

	/**
	 * 添加order by asc语句,不建议使用，以后要取消
	 * 
	 * @param orderBySql
	 * @return
	 */
	Query orderByAsc(String orderBySql);

	/**
	 * 添加order by desc语句,不建议使用，以后要取消
	 * 
	 * @param orderBySql
	 * @return
	 */
	Query orderByDesc(String orderBySql);

	Number queryForNumberBySql(String ds, String sql, Object... values);

	/**
	 * 给条件表达式赋值,不建议使用，以后要取消
	 * 
	 * @param value
	 * @return
	 */
	Query setParam(Object value);

	void setQueryManager(QueryManager queryManager);

	/**
	 * 设置获取的字段,不建议使用，以后要取消
	 * 
	 * @param fieldSql
	 * @return
	 */
	Query setShowFields(String fieldSql);

	/**
	 * 设置访问的数据表,不建议使用，以后要取消
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T> Query setTable(Class<T> clazz);

	/**
	 * 不建议使用，以后要取消
	 * 
	 * @param <T>
	 * @param clazz
	 * @param alias
	 * @return
	 *         2010-4-24
	 */
	<T> Query setTable(Class<T> clazz, String alias);

	/**
	 * 设置访问的数据表,不建议使用，以后要取消
	 * 
	 * @param tableSql
	 * @return
	 */
	Query setTable(String tableSql);

	/**
	 * 求和
	 * 
	 * @param field
	 * @return
	 */
	<T> Number sum(String field, Class<T> clazz, String whereExpression,
			Object[] objs);

	/**
	 * 更新数据,不建议使用，以后要取消
	 * 
	 * @return
	 */
	int update();

	<T> int update(Class<T> clazz, String whereExpression, Object[] objs);

	<T> int updateById(Class<T> clazz, Object id);

	int updateObject(Object obj);

	/**
	 * 更新对象，可以除去不更新的字段
	 * 
	 * @param obj
	 * @param param
	 *            不需要更新的字段
	 * @return
	 *         2010-5-5
	 */
	int updateObjectExcept(Object obj, String[] param);

	/**
	 * 添加条件表达式
	 * 
	 * @param whereExpression
	 * @return
	 */
	Query where(String whereExpression);
}