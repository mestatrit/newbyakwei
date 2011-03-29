package com.hk.frame.dao.query2;

/**
 * 更新时参数的对象表示方式，完全是为了程序易读<br/>
 * 
 * @author akwei
 */
public class UpdateParam extends Param {

	private String[] updateColumns;

	private Class<? extends Object> clazz;

	public UpdateParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public String[] getUpdateColumns() {
		return updateColumns;
	}

	/**
	 * 设置需要更新的列(与数据库字段相同)
	 * 
	 * @param updateColumns
	 */
	public void setUpdateColumns(String[] updateColumns) {
		this.updateColumns = updateColumns;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	/**
	 * 设置要更新的类，通过此类可获取对应的表信息
	 * 
	 * @param clazz
	 */
	public void setClazz(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}
}
