package iwant.dao.database;

import java.util.Map;

import cactus.dao.query.DbPartitionHelper;
import cactus.dao.query.PartitionTableInfo;

/**
 * 单数据库解析
 * 
 * @author akwei
 */
public class SingleDbPartitionHelper extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName(Database.DB_IWANT);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}