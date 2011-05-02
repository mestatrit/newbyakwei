package cactus.dao.sql;

/**
 * 保存当前使用的数据库key
 * 
 * @author akwei
 */
public class DataSourceStatus {

	private static final ThreadLocal<String> currentDsName = new ThreadLocal<String>();

	public static void setCurrentDsName(String dsName) {
		currentDsName.set(dsName);
	}

	public static String getCurrentDsName() {
		return currentDsName.get();
	}
}