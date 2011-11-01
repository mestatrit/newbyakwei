package halo.dataserver.input;

import halo.dataserver.pub.SqlParameter;

import java.util.List;
import java.util.Map;

/**
 * 从输入端接受的信息分解成为输入对象
 * 
 * @author akwei
 */
public class InputSqlInfo {

	/**
	 * 接收的sql
	 */
	private String sql;

	/**
	 * 数据路由参数上下文
	 */
	private Map<String, String> ctxMap;

	/**
	 * 所有需要预处理的参数
	 */
	private List<SqlParameter> sqlParameters;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Map<String, String> getCtxMap() {
		return ctxMap;
	}

	public void setCtxMap(Map<String, String> ctxMap) {
		this.ctxMap = ctxMap;
	}

	public List<SqlParameter> getSqlParameters() {
		return sqlParameters;
	}

	public void setSqlParameters(List<SqlParameter> sqlParameters) {
		this.sqlParameters = sqlParameters;
	}
}