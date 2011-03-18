package com.hk.frame.datasource;

public class DataSourceStatus {

	private static final ThreadLocal<String> currentDsName = new ThreadLocal<String>();

	public static void setCurrentDsName(String dsName) {
		currentDsName.set(dsName);
	}

	public static String getCurrentDsName() {
		return currentDsName.get();
	}

	public static String getCurrentDaoSupportFlg() {
		String dsName = getCurrentDsName();
		int idx = dsName.indexOf('_');
		if (idx == -1) {
			return null;
		}
		return dsName.substring(0, idx);
	}
}