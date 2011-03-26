package com.hk.frame.dao.query2;

/**
 * 查询条件参数的对象表示方式，完全是为了程序易读<br/>
 * 集合查询时，所有参数都要用到<br/>
 * 查询单个对象时，不需要用到 begin ,size<br/>
 * 统计数字时,不需要用到order,begin,size,columns
 * 
 * @author akwei
 */
public class QueryParam extends Param {

	private Class<? extends Object>[] classes;

	private String[][] columns;

	private String order;

	private int begin;

	private int size;

	public QueryParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public String[][] getColumns() {
		return columns;
	}

	public void setColumns(String[][] columns) {
		this.columns = columns;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setClasses(Class<? extends Object>[] classes) {
		this.classes = classes;
	}

	public Class<? extends Object>[] getClasses() {
		return classes;
	}
}