package tuxiazi.dao.dbpartitionhelper;

import java.util.Map;

import com.hk.frame.dao.query2.DbPartitionHelper;
import com.hk.frame.dao.query2.PartitionTableInfo;

public class Tuxiazi_FeedDbPartitionHelper extends DbPartitionHelper {

	private String dsKey = "ds_tuxiazi_feed";

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName(dsKey);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}