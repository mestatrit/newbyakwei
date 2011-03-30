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

	/**
	 * 添加需要查询的类。添加类的顺序会影响到设置现实字段的顺序。添加类的顺序一定要与columns的顺序一致
	 * 
	 * @param <T>
	 * @param clazz
	 *            需要查询的类
	 */
	public <T> void addClass(Class<T> clazz) {
		if (!this.classList.contains(clazz)) {
			this.classList.add(clazz);
		}
	}

	/**
	 * 获得需要查询的列，是与addClass表一致的顺序
	 * 
	 * @return
	 */
	public String[][] getColumns() {
		return columns;
	}

	/**
	 * 设置数据库需要显示的字段。(注意：是数据库字段，不是代码表现的属性)。<br/>
	 * 添加的顺序一定要与addClass的顺序一致<br/>
	 * 例如 addClass(User.class);addClass(Member.class); setColumns的参数为：<br/>
	 * new String[][]{<br/>
	 * {userid,nick,gender}(user表),<br/>
	 * {memberid,membername,birthday}(member表)<br/>
	 */
	public void setColumns(String[][] columns) {
		this.columns = columns;
	}

	/**
	 * 获得order by的排序sql片段
	 * 
	 * @return
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置sql中 order部分
	 * 
	 * @param order
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * 获得获取数据集合的开始位置
	 * 
	 * @return
	 */
	public int getBegin() {
		return begin;
	}

	/**
	 * 设置获取数据开始的位置
	 * 
	 * @param begin
	 */
	public void setBegin(int begin) {
		this.begin = begin;
	}

	/**
	 * 获取数据集合的数量<br/>
	 * <=0时，获取所有数据;>0时获取指定数量数据
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * 设置获取数据的数量。<=0时，获取所有数据;>0时获取指定数量数据
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 获取要查询的类<br/>
	 * 根据类信息可获取表信息
	 * 
	 * @return
	 */
	Class<? extends Object>[] getClasses() {
		return this.classList.toArray(new Class<?>[this.classList.size()]);
	}

	public void setWhereAndParams(String where, Object[] params) {
		this.setWhere(where);
		this.setParams(params);
	}

	public void setRange(int begin, int szie) {
		this.setBegin(begin);
		this.setSize(szie);
	}
}