package com.hk.frame.dao.query2;

import java.util.List;

public interface IDao<T> {

	// ********************* 提供公用方法 **************************/
	/**
	 * 创建对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	Object save(Object keyValue, T t);

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

	int update(T t);

	int updateBySQL(Object keyValue, String updateSqlSegment, String where,
			Object[] params);

	/**
	 * 删除对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	int delete(Object keyValue, T t);

	int delete(T t);

	int deleteById(Object keyValue, Object idValue);

	int delete(Object keyValue, String where, Object[] params);

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

	<E> List<T> getListInField(Object keyValue, String field,
			List<E> fieldValueList);

	<E> List<T> getListInField(Object keyValue, String where, Object[] params,
			String field, List<E> fieldValueList);

	int count(Object keyValue, String where, Object[] params);

	T getObject(Object keyValue, String where, Object[] params, String order);

	T getObject(Object keyValue, String where, Object[] params);
}
