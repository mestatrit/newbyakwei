package com.hk.frame.dao.query.partition;

import java.util.List;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.impl.QueryTable;

public interface Partition {
	PartitionTable parse(QueryTable queryTable, List<Expression> list,
			String config);
}