package com.dev3g.cactus.dao.query;

/**
 * 数据库表的基本信息，包括数据库真是名称与表真是名称
 * 
 * @author akwei
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

	/**
	 * 表的别名
	 */
	private String aliasName;

	public PartitionTableInfo() {
	}

	public PartitionTableInfo(String databaseName, String tableName) {
		this.databaseName = databaseName;
		this.tableName = tableName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * 设置数据库真实key
	 * 
	 * @param databaseName
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置表真实名称
	 * 
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 设置表别名
	 * 
	 * @param aliasName
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getAliasName() {
		return aliasName;
	}
}
