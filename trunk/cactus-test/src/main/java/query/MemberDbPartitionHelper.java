package query;

import java.util.Map;

import cactus.dao.query.DbPartitionHelper;
import cactus.dao.query.PartitionTableInfo;

public class MemberDbPartitionHelper extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		long userid = (Long) ctxMap.get("member.memberuserid");
		String lastChar = this.get01(userid);
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName(name);
		partitionTableInfo.setTableName("member" + lastChar);
		partitionTableInfo.setDatabaseName("mysql_test" + lastChar);
		return partitionTableInfo;
	}
}
