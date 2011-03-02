package com.hk.frame.dao.query.partition.impl;

import java.util.List;
import com.hk.frame.dao.query.criteria.Expression;
import com.hk.frame.dao.query.impl.QueryTable;
import com.hk.frame.dao.query.partition.Partition;
import com.hk.frame.dao.query.partition.PartitionTable;

public class ModPartition implements Partition {

	public PartitionTable parse(QueryTable queryTable, List<Expression> list,
			String config) {
		String[] s = config.split(",");
		String ds = s[0];
		String field = null;
		if (queryTable.getAlias() != null) {
			StringBuilder sb = new StringBuilder(queryTable.getAlias());
			sb.append(".").append(s[1]);
			field = sb.toString();
		}
		else {
			field = s[1];
		}
		Expression exp = null;
		if (list != null) {
			for (Expression e : list) {
				if (e.getField().equals(field)) {
					exp = e;
					break;
				}
			}
		}
		if (exp == null) {
			throw new RuntimeException("can not find experssion tableName [ "
					+ queryTable.getName() + " ] field [ " + field + " ]");
		}
		String bit = this.getLastChar((Number) exp.getValue());
		PartitionTable table = new PartitionTable();
		table.setTable(queryTable.getName() + bit);
		table.setDatabase(ds);
		return table;
	}

	protected String getLastChar(Number id) {
		String ss = id + "";
		if (ss.equals("0")) {
			throw new IllegalArgumentException("Id is 0");
		}
		int len = ss.length();
		if (len == 0) {
			throw new IllegalArgumentException("Id is null");
		}
		if (len > 1) {
			ss = ss.substring(ss.length() - 1, ss.length());
		}
		try {
			Long.parseLong(ss);
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException("Id is less than 0");
		}
		return ss;
	}
}