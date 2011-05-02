package cactus.dao.sql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnectionProxyImpl implements ConnectionProxy {

	private final Log log = LogFactory.getLog(ConnectionProxyImpl.class);

	private final Map<String, Connection> conMap = new HashMap<String, Connection>();

	private boolean autoCommit;

	private int transactionIsolation;

	private int holdability;

	private boolean readOnly;

	private HkDataSourceWrapper cloudDataSourceWrapper;

	public ConnectionProxyImpl(HkDataSourceWrapper cloudDataSourceWrapper)
			throws SQLException {
		this.cloudDataSourceWrapper = cloudDataSourceWrapper;
		this.setAutoCommit(true);
	}

	public void clearWarnings() throws SQLException {
		this.getCurrentConnection().clearWarnings();
	}

	public void close() throws SQLException {
		if (DaoDebugMode.isInfoDeubg()) {
			log.info("begin close connection");
		}
		Collection<Connection> c = this.conMap.values();
		for (Connection con : c) {
			if (DaoDebugMode.isInfoDeubg()) {
				log.info("close  connection ");
			}
			con.close();
		}
		DataSourceStatus.setCurrentDsName(null);
	}

	public void commit() throws SQLException {
		Collection<Connection> c = this.conMap.values();
		for (Connection con : c) {
			if (DaoDebugMode.isInfoDeubg()) {
				log.info("commit connection ");
			}
			con.commit();
		}
	}

	public Statement createStatement() throws SQLException {
		return this.getCurrentConnection().createStatement();
	}

	public Connection getCurrentConnection() {
		String name = DataSourceStatus.getCurrentDsName();
		Connection con = this.conMap.get(name);
		if (con == null) {
			if (DaoDebugMode.isInfoDeubg()) {
				log.info("create database connection from datasource [ " + name
						+ " ]");
			}
			try {
				con = this.cloudDataSourceWrapper.getCurrentDataSource()
						.getConnection();
				this.initCurrentConnection(con);
				this.conMap.put(name, con);
			}
			catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return con;
	}

	private void initCurrentConnection(Connection con) throws SQLException {
		if (DaoDebugMode.isInfoDeubg()) {
			log.info("init real connection info");
		}
		con.setAutoCommit(this.getAutoCommit());
		if (this.getTransactionIsolation() != 0) {
			con.setTransactionIsolation(this.getTransactionIsolation());
		}
		con.setHoldability(this.getHoldability());
		if (this.isReadOnly()) {
			con.setReadOnly(true);
		}
		else {
			con.setReadOnly(this.isReadOnly());
		}
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.getCurrentConnection().createStatement(resultSetType,
				resultSetConcurrency);
	}

	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.getCurrentConnection().createStatement(resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public boolean getAutoCommit() throws SQLException {
		return this.autoCommit;
	}

	public int getHoldability() throws SQLException {
		return this.holdability;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return this.getCurrentConnection().getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return this.transactionIsolation;
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.getCurrentConnection().getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.getCurrentConnection().getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return this.getCurrentConnection().isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return this.readOnly;
	}

	public String nativeSQL(String sql) throws SQLException {
		return this.getCurrentConnection().nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.getCurrentConnection().prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.getCurrentConnection().prepareCall(sql, resultSetType,
				resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.getCurrentConnection().prepareCall(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql,
				autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql, columnNames);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql, resultSetType,
				resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.getCurrentConnection().prepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public void rollback() throws SQLException {
		Collection<Connection> c = conMap.values();
		for (Connection con : c) {
			if (DaoDebugMode.isInfoDeubg()) {
				log.info("rollback connection ");
			}
			con.rollback();
		}
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
		Collection<Connection> c = conMap.values();
		for (Connection con : c) {
			if (DaoDebugMode.isInfoDeubg()) {
				log.info("autoCommit : [ " + this.autoCommit + " ]");
			}
			con.setAutoCommit(autoCommit);
		}
	}

	public void setCatalog(String catalog) throws SQLException {
		this.getCurrentConnection().setCatalog(catalog);
	}

	public String getCatalog() throws SQLException {
		return this.getCurrentConnection().getCatalog();
	}

	public void setHoldability(int holdability) throws SQLException {
		this.holdability = holdability;
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		this.readOnly = readOnly;
	}

	public void setTransactionIsolation(int level) throws SQLException {
		this.transactionIsolation = level;
		Collection<Connection> c = conMap.values();
		for (Connection con : c) {
			con.setTransactionIsolation(level);
		}
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.getCurrentConnection().setTypeMap(map);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		throw new SQLException("do not support savepoint");
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		throw new SQLException("do not support savepoint");
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new SQLException("do not support savepoint");
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		throw new SQLException("do not support savepoint");
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		return this.getCurrentConnection().createArrayOf(typeName, elements);
	}

	@Override
	public Blob createBlob() throws SQLException {
		return this.getCurrentConnection().createBlob();
	}

	@Override
	public Clob createClob() throws SQLException {
		return this.getCurrentConnection().createClob();
	}

	@Override
	public NClob createNClob() throws SQLException {
		return this.getCurrentConnection().createNClob();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return this.getCurrentConnection().createSQLXML();
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		return this.getCurrentConnection().createStruct(typeName, attributes);
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return this.getCurrentConnection().getClientInfo();
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		Connection con = this.getCurrentConnection();
		return con.getClientInfo(name);
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return this.getCurrentConnection().isValid(timeout);
	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		this.getCurrentConnection().setClientInfo(properties);
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		this.getCurrentConnection().setClientInfo(name, value);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return this.getCurrentConnection().isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return this.getCurrentConnection().unwrap(iface);
	}
}