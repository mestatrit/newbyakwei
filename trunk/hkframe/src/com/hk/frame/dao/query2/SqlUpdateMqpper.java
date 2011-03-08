package com.hk.frame.dao.query2;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 此接口的存在是为了利用asm进行类生成，提高性能
 * 
 * @author akwei
 * @param <T>
 */
public interface SqlUpdateMqpper<T> {

	/**
	 * 返回insert需要的参数
	 * 
	 * @param fieldList
	 *            参数对应的字段
	 * @param t
	 *            需要insert的对象
	 * @return
	 */
	Object[] getParamsForInsert(List<Field> fieldList, T t);

	/**
	 * 返回update需要的参数以及id所对应的参数组成的数组
	 * 
	 * @param fieldList
	 *            参数对应的字段
	 * @param idField
	 *            id字段
	 * @param t
	 *            需要update的对象
	 * @return
	 */
	Object[] getParamsForUpdate(List<Field> fieldList, Field idField, T t);

	/**
	 * 返回delete by id 时id的参数
	 * 
	 * @param idField
	 *            id 字段
	 * @param t
	 *            要删除的对象
	 * @return
	 */
	Object getIdParam(Field idField, T t);
}
