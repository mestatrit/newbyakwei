package com.hk.svr.pub;

/**
 * 排序对象，一个排序字段和一个排序方式 目前只支持单个排序
 * 
 * @author yuanwei
 */
public class DataSort {
	/**
	 * 排序字段
	 */
	private String field;

	/**
	 * 排序方式,是否支持倒序
	 */
	private boolean desc;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}
}