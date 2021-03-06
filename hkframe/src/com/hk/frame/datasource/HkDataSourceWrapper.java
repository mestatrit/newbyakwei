package com.hk.frame.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hk.frame.dao.DaoDebugMode;

/**
 * DataSource的包装类
 * 
 * @author akwei
 */
public class HkDataSourceWrapper implements DataSource {

	private Map<String, DataSource> dataSourceMap;

	private PrintWriter logWriter;

	private int loginTimeout = 3;

	private final Log log = LogFactory.getLog(HkDataSourceWrapper.class);

	public DataSource getCurrentDataSource() {
		return this.dataSourceMap.get(DataSourceStatus.getCurrentDsName());
	}

	public void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public Connection getConnection() throws SQLException {
		if (DaoDebugMode.isInfoDeubg()) {
			log.info("begin open connection");
		}
		return new ConnectionProxyImpl(this);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		throw new SQLException("only support getConnection()");
	}

	public PrintWriter getLogWriter() throws SQLException {
		return this.logWriter;
	}

	public int getLoginTimeout() throws SQLException {
		return this.loginTimeout;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		this.logWriter = out;
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		this.loginTimeout = seconds;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getCurrentDataSource().isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.getCurrentDataSource().unwrap(iface);
	}
}