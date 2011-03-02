package com.hk.frame.dao.query;

public class TableMapper {
	private String tableName;

	private String currentTableName;

	public TableMapper() {//
	}

	public TableMapper(String tableName, String currentTableName) {
		this.tableName = tableName;
		this.currentTableName = currentTableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCurrentTableName() {
		return currentTableName;
	}

	public void setCurrentTableName(String currentTableName) {
		this.currentTableName = currentTableName;
	}
}