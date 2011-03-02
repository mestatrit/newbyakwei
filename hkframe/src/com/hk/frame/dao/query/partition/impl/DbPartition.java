package com.hk.frame.dao.query.partition.impl;

import java.util.List;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.impl.QueryTable;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.query.partition.PartitionTable;

public class DbPartition implements Partition {
	private String ds;

	public void setDs(String ds) {
		this.ds = ds;
	}

	public PartitionTable parse(QueryTable t, List<Expression> list,
			String config) {
		PartitionTable table = new PartitionTable();
		table.setTable(t.getName());
		table.setDatabase(this.ds);
		return table;
	}
}