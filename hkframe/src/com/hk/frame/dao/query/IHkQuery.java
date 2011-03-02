package com.hk.frame.dao.query;

import java.util.List;
import java.util.Map;

public interface IHkQuery {

	/**
	 * 执行sql count函数
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 * @return count的结果
	 *         2010-9-20
	 */
	<T> int count(Class<T> clazz, String where, Object[] param);

	/**
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param paramList 查询条件中的参数值集合
	 * @return count的结果
	 *         2010-9-20
	 */
	<T> int countExParamList(Class<T> clazz, String where,
			List<Object> paramList);

	/**
	 * sql delete命令
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 *            2010-9-20
	 * @return 删除条数
	 */
	<T> int delete(Class<T> clazz, String where, Object[] param);

	/**
	 * 执行sql delete 删除数据
	 * 
	 * @param obj 要删除的数据对象，保证id有效，才能正确删除
	 * @return 删除条数
	 *         2010-9-20
	 */
	int delete(Object obj);

	/**
	 * 执行sql delete 删除数据
	 * 
	 * @param <T>目标类
	 * @param clazz目标类class，用来确定要执行的表
	 * @param idValue 数据的唯一id
	 * @return
	 *         2010-9-20
	 */
	<T> int deleteById(Class<T> clazz, Object idValue);

	/**
	 * 执行sql select * from table，获取符合条件的第一条数据
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 * @param orderBySql 排序sql片段
	 * @return
	 *         2010-9-20
	 */
	<T> T get(Class<T> clazz, String where, Object[] param, String orderBySql);

	/**
	 * 根据id获得对象
	 * 
	 * @param <T>目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param idValue 数据的唯一id
	 * @return 查询结果
	 */
	<T> T getById(Class<T> clazz, Object idValue);

	/**
	 * 执行sql insert，创建对象
	 * 
	 * @param obj 要插入的对象
	 * @return 数据插入后的id
	 *         2010-9-20
	 */
	Number insert(Object obj);

	/**
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 * @param orderBySql 排序sql片段
	 * @param begin 记录开始号
	 * @param size 获取记录的条数 ，<=0时获取所有，会忽略begin参数
	 * @return 符合条件的数据集合
	 *         2010-9-20
	 */
	<T> List<T> list(Class<T> clazz, String where, Object[] param,
			String orderBySql, int begin, int size);

	/**
	 * @param <T> 目标类
	 * @param <E> sql in 的字段值类型
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 * @param field sql in 的字段
	 * @param fieldValueList sql in 字段的值集合
	 * @param orderBySql 排序sql片段
	 * @return 符合条件的数据集合
	 *         2010-9-20
	 */
	<T, E> List<T> listInField(Class<T> clazz, String where, Object[] param,
			String field, List<E> fieldValueList, String orderBySql);

	/**
	 * @param <T> 目标类
	 * @param <E> sql not in 的字段值类型
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param param 查询条件的值
	 * @param field sql not in 的字段
	 * @param fieldValueList sql not in 字段的值集合
	 * @param orderBySql 排序sql片段
	 * @param begin 记录开始号
	 * @param size 获取记录的条数 ，<=0时获取所有，会忽略begin参数
	 * @return 符合条件的数据集合
	 *         2010-9-20
	 */
	<T, E> List<T> listNotInField(Class<T> clazz, String where, Object[] param,
			String field, List<E> fieldValueList, String orderBySql, int begin,
			int size);

	/**
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param where 查询条件
	 * @param paramList 查询条件中的参数值集合
	 * @param orderBySql 排序sql片段
	 * @param begin 记录开始号
	 * @param size 获取记录的条数 ，<=0时获取所有，会忽略begin参数
	 * @return 符合条件的数据集合
	 *         2010-9-20
	 */
	<T> List<T> listParamList(Class<T> clazz, String where,
			List<Object> paramList, String orderBySql, int begin, int size);

	/**
	 * 进行事务处理的过程，此过程如果失败，当前线程的数据更新操作将回滚
	 * 
	 * @param <T> 目标类
	 * @param hkQueryProcessor 事务过程类
	 * @return
	 *         2010-9-20
	 */
	<T> T process(HkQueryProcessor hkQueryProcessor);

	/**
	 * 执行 sql sum函数
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param field 需要进行sum的字段
	 * @param where 查询条件
	 * @param params 查询条件的值
	 * @return 返回sum函数的结果
	 *         2010-9-21
	 */
	<T> int sum(Class<T> clazz, String field, String where, Object[] params);

	/**
	 * 执行sql update
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param kvMap 需要更新的字段的map(字段=值)
	 * @param where 查询条件
	 * @param params 查询条件的值
	 * @return 更新条数
	 *         2010-9-21
	 */
	<T> int update(Class<T> clazz, Map<String, Object> kvMap, String where,
			Object[] params);

	/**
	 * 执行sql update,更新对象
	 * 
	 * @param obj 要更新的数据对象，保证有id才可以正确更新
	 * @return 更新条数
	 *         2010-9-21
	 */
	int update(Object obj);

	/**
	 * 执行sql update
	 * 
	 * @param <T> 目标类
	 * @param clazz 目标类class，用来确定要执行的表
	 * @param kvMap 需要更新的字段的map(字段=值)
	 * @param id 更新数据的id
	 * @return 更新条数
	 *         2010-9-21
	 */
	<T> int updateById(Class<T> clazz, Map<String, Object> kvMap, Object id);

	/**
	 * 更新对象，可以除去不更新的字段
	 * 
	 * @param obj要更新的对象
	 * @param param 不需要更新的字段
	 * @return 更新的条数
	 *         2010-5-5
	 */
	int updateExcept(Object obj, String[] field);
	// <T> List<T> list(Class<T> clazz);
	// <T> List<T> list(Class<T> clazz, int begin, int size);
	// <T> List<T> list(Class<T> clazz, String orderBySql);
	// <T> List<T> list(Class<T> clazz, String orderBySql, int begin, int size);
	// <T> List<T> list(Class<T> clazz, String where, Object[] param);
	// <T> List<T> list(Class<T> clazz, String where, Object[] param,
	// int begin, int size);
	// <T> List<T> list(Class<T> clazz, String where, Object[] param,
	// String orderBySql);
	// <T> int count(Class<T> clazz);
	// int countBySql(String ds, String sql, List<Object> olist);
	// int countBySql(String ds, String sql, Object[] value);
	// <T> List<T> listBySql(Class<T> clazz, String ds, String sql, int begin,
	// int size, Object[] values);
	//
	// <T> List<T> listBySql(Class<T> clazz, String ds, String sql, Object[]
	// values);
	//
	// <T> List<T> listBySqlParamList(String ds, String sql, Class<T> clazz,
	// List<Object> olist);
	//
	// <T> List<T> listBySqlParamList(String ds, String sql, int begin, int
	// size,
	// Class<T> clazz, List<Object> olist);
	//
	// List<Object[]> listData(String ds, String sql, int begin, int size,
	// Object[] param);
	//
	// List<Object[]> listData(String ds, String sql, Object[] param);
	// <T> T getObject(Class<T> clazz, String where, Object[] param);
}