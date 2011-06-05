package query;

import java.util.Map;

import com.dev3g.cactus.dao.partition.DbPartitionHelper;
import com.dev3g.cactus.dao.query.PartitionTableInfo;

public class MemberDbPartitionHelper extends DbPartitionHelper {

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		long userid = (Long) ctxMap.get("memberuserid");
		String lastChar = this.get01(userid);
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName(name);
		partitionTableInfo.setTableName("member" + lastChar);
		partitionTableInfo.setDsKey("mysql_test" + lastChar);
		return partitionTableInfo;
	}
}
