package com.hk.frame.dao.query2;

import java.util.HashMap;
import java.util.Map;

public class DbPartitionHelperCreater {

	private final Map<String, DbPartitionHelper> map = new HashMap<String, DbPartitionHelper>();

	public void add(String key, DbPartitionHelper dbPartitionHelper) {
		map.put(key, dbPartitionHelper);
	}

	public void remove(String key) {
		map.remove(key);
	}

	public DbPartitionHelper getDbPartitionHelper(String key) {
		return map.get(key);
	}
}