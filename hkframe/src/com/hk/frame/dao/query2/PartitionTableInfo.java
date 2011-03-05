package com.hk.frame.dao.query2;

/**
 * 数据库表的基本信息，包括数据库真是名称与表真是名称
 * 
 * @author fire9
 */
public class PartitionTableInfo {
	/**
	 * 数据库真是名称
	 */
	private String databaseName;
	/**
	 * 表真是名称
	 */
	private String tableName;

	public PartitionTableInfo() {
	}

	public PartitionTableInfo(String databaseName, String tableName) {
		this.databaseName = databaseName;
		this.tableName = tableName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
