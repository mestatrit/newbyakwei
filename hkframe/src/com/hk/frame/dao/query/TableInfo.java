package com.hk.frame.dao.query;

import java.util.LinkedHashMap;

/**
 * 当前操作表的信息
 * 
 * @author yuanwei
 */
public class TableInfo {
	private String database;

	private LinkedHashMap<String, String> nameMap = new LinkedHashMap<String, String>();

	private LinkedHashMap<String, String> aliasNameMap = new LinkedHashMap<String, String>();

	private LinkedHashMap<String, String> fieldValueMap = new LinkedHashMap<String, String>();

	public String getDatabase() {
		return database;
	}

	public void chgDatabase(String database) {
		this.database = database;
	}

	public void chgTable(String name, String newName) {
		this.nameMap.put(name, newName);
	}

	public void addTable(String name) {
		if (name.indexOf(',') != -1) {
			String[] lname = name.trim().split(",");
			for (int i = 0; i < lname.length; i++) {
				this.parseTable(lname[i].trim());
			}
		}
		else {
			this.parseTable(name.trim());
		}
	}

	private void parseTable(String t) {
		String tmp_t = t.replaceAll("\\s+", " ");
		String[] nameAndAlias = tmp_t.split(" ");
		String realName = nameAndAlias[0];
		String realAlias = nameAndAlias[1];
		this.nameMap.put(realName, realName);
		this.aliasNameMap.put(realName, realAlias);
	}

	public void addFieldValue(String field, String value) {
		this.fieldValueMap.put(field, value);
	}

	public void parseUpdateFieldSql(String s) {
		String[] t = s.split(",");
		for (int i = 0; i < t.length; i++) {
			this.fieldValueMap.put(t[i].trim(), t[i].trim() + "=?");
		}
	}

	public void parseWhereFieldSql(String s) {
		String[] t = s.split(" and ");
		for (int i = 0; i < t.length; i++) {
			String field = null;
			String value = null;
			this.fieldValueMap.put(field, value);
		}
	}

	public static void main(String[] args) {
		TableInfo info = new TableInfo();
		info.addTable("user  u , member m");
	}
}