package com.dev3g.cactus.dao.partition;

import java.util.Map;

import com.dev3g.cactus.dao.query.PartitionTableInfo;
import com.dev3g.cactus.dao.sql.HkDataSourceWrapper;

public class DbPartitionHelperDef extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDsKey(HkDataSourceWrapper.DEFAULT_DBKEY);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}