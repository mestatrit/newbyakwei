package com.hk.frame.dao.query2;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件参数的对象表示方式，完全是为了程序易读<br/>
 * 集合查询时，所有参数都要用到<br/>
 * 查询单个对象时，不需要用到 begin ,size<br/>
 * 统计数字时,不需要用到order,begin,size,columns
 * 
 * @author akwei
 */
public class QueryParam extends Param {

	private final List<Class<?>> classList = new ArrayList<Class<?>>(2);

	private String[][] columns;

	private String order;

	private int begin;

	private int size;

	public QueryParam(ObjectSqlInfoCreater objectSqlInfoCreater) {
		super(objectSqlInfoCreater);
	}

	public <T> void addClass(Class<T> clazz) {
		if (!this.classList.contains(clazz)) {
			this.classList.add(clazz);
		}
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

	Class<? extends Object>[] getClasses() {
		return this.classList.toArray(new Class<?>[this.classList.size()]);
	}
}