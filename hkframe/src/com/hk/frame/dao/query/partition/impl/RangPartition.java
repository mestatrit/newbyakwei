package com.hk.frame.dao.query.partition.impl;

import java.util.List;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.impl.QueryTable;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.query.partition.PartitionTable;

/**
 * 在某个范围之内
 * 
 * @author yuanwei
 */
public class RangPartition implements Partition {
	public PartitionTable parse(QueryTable queryTable, List<Expression> list,
			String config) {
		return null;
	}
}