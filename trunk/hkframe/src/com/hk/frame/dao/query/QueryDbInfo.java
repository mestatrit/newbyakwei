package com.hk.frame.dao.query;

import com.hk.frame.dao.query.partition.PartitionTable;

public class QueryDbInfo {

	private String database;

	private PartitionTable partitionTable;

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public PartitionTable getPartitionTable() {
		return partitionTable;
	}

	public void setPartitionTable(PartitionTable partitionTable) {
		this.partitionTable = partitionTable;
	}
}