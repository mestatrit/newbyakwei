package com.hk.frame.dao.query2;

/**
 * delete时的参数对象 deleteById时,where、params参数无效
 * 
 * @author akwei
 */
public class DeleteParam extends Param {

	private Class<? extends Object> clazz;

	public DeleteParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public void setClazz(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}
}
