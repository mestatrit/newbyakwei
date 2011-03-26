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

	public void setUpdateColumns(String[] updateColumns) {
		this.updateColumns = updateColumns;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}
}
