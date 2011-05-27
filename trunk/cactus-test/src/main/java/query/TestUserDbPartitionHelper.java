package query;

import java.util.Map;

import com.dev3g.cactus.dao.partition.DbPartitionHelper;
import com.dev3g.cactus.dao.query.PartitionTableInfo;

public class TestUserDbPartitionHelper extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		long userid = (Long) ctxMap.get("userid");
		String lastChar = this.get01(userid);
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName(name);
		partitionTableInfo.setTableName("testuser" + lastChar);
		partitionTableInfo.setDatabaseName("mysql_test" + lastChar);
		return partitionTableInfo;
	}
}