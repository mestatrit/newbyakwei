package com.hk.frame.dao.query.impl;

/**
 * 数据表的对象，sql写出的表名为逻辑名称，经过解析后，会修改为物理表的名称，已经产生相应的数据源
 * 
 * @author akwei
 */
public class QueryTable {
	private String name;

	private String alias;

	private String database;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public QueryTable(String name) {
		this.name = name;
	}

	public QueryTable(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}