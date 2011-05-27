package com.dev3g.cactus.dao.query;

import java.util.List;

public interface IDao<T> {

	int count(Object keyValue, String where, Object[] params);

	/**
	 * 非分区模式使用
	 * 
	 * @param where
	 * @param params
	 * @return
	 */
	int count(String where, Object[] params);

	int delete(Object keyValue, String where, Object[] params);

	/**
	 * 删除对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	int delete(Object keyValue, T t);

	/**
	 * 非分区模式使用
	 * 
	 * @param where
	 * @param params
	 * @return
	 */
	int delete(String where, Object[] params);

	/**
	 * 非分区模式使用
	 * 
	 * @param t
	 * @return
	 */
	int delete(T t);

	/**
	 * 非分区模式使用
	 * 
	 * @param idValue
	 * @return
	 */
	int deleteById(Object idValue);

	int deleteById(Object keyValue, Object idValue);

	/**
	 * 非分区模式使用
	 * 
	 * @param idValue
	 * @return
	 */
	T getById(Object idValue);

	/**
	 * 根据id查询对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param idValue
	 * @return
	 */
	T getById(Object keyValue, Object idValue);

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
	List<T> getList(Object keyValue, String where, Object[] params,
			String order, int begin, int size);

	/**
	 * 非分区模式使用
	 * 
	 * @param where
	 * @param params
	 * @param order
	 * @param begin
	 * @param size
	 * @return
	 */
	List<T> getList(String where, Object[] params, String order, int begin,
			int size);

	<E> List<T> getListInField(Object keyValue, String field,
			List<E> fieldValueList);

	<E> List<T> getListInField(Object keyValue, String where, Object[] params,
			String field, List<E> fieldValueList);

	/**
	 * 非分区模式使用
	 * 
	 * @param <E>
	 * @param field
	 * @param fieldValueList
	 * @return
	 */
	<E> List<T> getListInField(String field, List<E> fieldValueList);

	/**
	 * 非分区模式使用
	 * 
	 * @param <E>
	 * @param where
	 * @param params
	 * @param field
	 * @param fieldValueList
	 * @return
	 */
	<E> List<T> getListInField(String where, Object[] params, String field,
			List<E> fieldValueList);

	T getObject(Object keyValue, String where, Object[] params);

	T getObject(Object keyValue, String where, Object[] params, String order);

	/**
	 * 非分区模式使用
	 * 
	 * @param where
	 * @param params
	 * @return
	 */
	T getObject(String where, Object[] params);

	/**
	 * 非分区模式使用
	 * 
	 * @param where
	 * @param params
	 * @param order
	 * @return
	 */
	T getObject(String where, Object[] params, String order);

	/**
	 * 创建对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	Object save(Object keyValue, T t);

	/**
	 * 非分区模式使用
	 * 
	 * @param t
	 * @return
	 */
	Object save(T t);

	/**
	 * 更新对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	int update(Object keyValue, T t);

	/**
	 * 非分区模式使用
	 * 
	 * @param t
	 * @return
	 */
	int update(T t);

	int updateBySQL(Object keyValue, String updateSqlSegment, String where,
			Object[] params);

	/**
	 * 非分区模式使用
	 * 
	 * @param updateSqlSegment
	 * @param where
	 * @param params
	 * @return
	 */
	int updateBySQL(String updateSqlSegment, String where, Object[] params);
}
