package tuxiazi.dao.dbpartitionhelper;

import halo.dao.partition.DbPartitionHelper;
import halo.dao.query.PartitionTableInfo;

import java.util.Map;

public class TuxiaziDbPartitionHelper extends DbPartitionHelper {

	private String dsKey = "ds_tuxiazi";

	@Override
	public PartitionTableInfo parse(String name, Map<String, Object> ctxMap) {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDsKey(dsKey);
		partitionTableInfo.setTableName(name);
		partitionTableInfo.setAliasName(name);
		return partitionTableInfo;
	}
}
