package cactus.dao.query;

import java.util.Map;

/**
 * 单数据库解析
 * 
 * @author akwei
 */
public class SingleDbPartitionHelper extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String tableLogicName,
			Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName(this.getBaseDatasourceKey());
		partitionTableInfo.setTableName(tableLogicName);
		partitionTableInfo.setAliasName(tableLogicName);
		return partitionTableInfo;
	}
}