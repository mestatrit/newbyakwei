package cactus.dao.query;

import java.util.Map;

import cactus.dao.sql.HkDataSourceWrapper;

public class DbPartitionHelperDef extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName(HkDataSourceWrapper.default_dbkey);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}