package halo.dataserver.sqlbuilder;

import halo.dataserver.sqlinfo.SqlParameter;

import java.util.List;

/**
 * @author akwei
 */
public class SqlInfo {

	private String sql;

	/**
	 * 所有需要预处理的参数
	 */
	private List<SqlParameter> sqlParameters;

	public void setSqlParameters(List<SqlParameter> sqlParameters) {
		this.sqlParameters = sqlParameters;
	}

	public List<SqlParameter> getSqlParameters() {
		return sqlParameters;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}
}
