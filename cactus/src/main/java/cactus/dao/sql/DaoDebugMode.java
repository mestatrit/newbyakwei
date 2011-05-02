package cactus.dao.sql;

public class DaoDebugMode {
	private static boolean infoDeubg;

	private static boolean sqlDeubg;

	public void setSqlDeubg(boolean sqlDeubg) {
		DaoDebugMode.sqlDeubg = sqlDeubg;
	}

	public static boolean isSqlDeubg() {
		return sqlDeubg;
	}

	public void setInfoDeubg(boolean infoDeubg) {
		DaoDebugMode.infoDeubg = infoDeubg;
	}

	public static boolean isInfoDeubg() {
		return infoDeubg;
	}
}