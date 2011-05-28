package com.dev3g.cactus.dao.query.param;

/**
 * delete时的参数对象 deleteById时,where、params参数无效
 * 
 * @author akwei
 */
public class DeleteParam extends BaseParam {

	private String where;

	private Object[] params;

	public String getWhere() {
		return where;
	}

	/**
	 * 设置sql的where条件
	 * 
	 * @param where
	 */
	public void setWhere(String where) {
		this.where = where;
	}

	public Object[] getParams() {
		return params;
	}

	/**
	 * 设置条件对应的参数
	 * 
	 * @param params
	 */
	public void setParams(Object[] params) {
		this.params = params;
	}

	public void setWhereAndParams(String where, Object[] params) {
		this.setWhere(where);
		this.setParams(params);
	}
}
