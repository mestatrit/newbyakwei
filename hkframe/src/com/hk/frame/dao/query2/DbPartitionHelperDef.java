package com.hk.frame.dao.query2;

import java.util.Map;

public class DbPartitionHelperDef extends DbPartitionHelper {

	private String dsKey;

	public void setDsKey(String dsKey) {
		this.dsKey = dsKey;
	}

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName(dsKey);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}