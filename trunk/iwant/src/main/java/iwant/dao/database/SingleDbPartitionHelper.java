package iwant.dao.database;


import java.util.Map;

import com.hk.frame.dao.query2.DbPartitionHelper;
import com.hk.frame.dao.query2.PartitionTableInfo;

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