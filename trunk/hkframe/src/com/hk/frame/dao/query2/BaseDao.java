package com.hk.frame.dao.query2;

import java.util.List;

public abstract class BaseDao<T> {

	private HkObjQuery hkObjQuery;

	public void setHkObjQuery(HkObjQuery hkObjQuery) {
		this.hkObjQuery = hkObjQuery;
	}

	public abstract String getKey();

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

	/**
	 * 删除对象
	 * 
	 * @param keyValue
	 *            分区关键值
	 * @param t
	 * @return
	 */
	public int delete(Object keyValue, T t) {
		BaseParam baseParam = this.hkObjQuery.createBaseParam(this.getClazz(),
				this.getKey(), keyValue);
		return this.hkObjQuery.deleteObj(baseParam, t);
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
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		return this.hkObjQuery.getObjectById(queryParam, getClazz(), idValue);
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

	public T getObject(Object keyValue, String where, Object[] params,
			String order) {
		QueryParam queryParam = this.hkObjQuery.createQueryParam(getClazz(),
				getKey(), keyValue);
		queryParam.setWhereAndParams(where, params);
		queryParam.setOrder(order);
		return this.hkObjQuery.getObject(queryParam, getClazz());
	}
}