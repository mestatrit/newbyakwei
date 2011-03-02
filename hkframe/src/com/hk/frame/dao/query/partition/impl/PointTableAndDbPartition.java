package com.hk.frame.dao.query.partition.impl;

import java.util.List;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.impl.QueryTable;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.query.partition.PartitionTable;

public class PointTableAndDbPartition implements Partition {
	public PartitionTable parse(QueryTable queryTable, List<Expression> list,
			String config) {
		PartitionTable table = new PartitionTable();
		table.setTable(queryTable.getName());
		String[] t = config.split(",");
		table.setDatabase(t[0]);
		table.setTable(t[1]);
		return table;
	}
}