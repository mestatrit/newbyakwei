package halo.dataserver.sqlinfo;

/**
 * 对原生sql尽心拆分后获得的sql基本信息，目前只支持对同一数据库的多个表进行分析，暂时不支持多数据库关联查询方式
 * 
 * @author akwei
 */
public class SqlInfo {

	private String dbName;

	private String[] tableNames;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String[] getTableNames() {
		return tableNames;
	}

	public void setTableNames(String[] tableNames) {
		this.tableNames = tableNames;
	}
}
