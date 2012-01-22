package halo.datasource.c3p0ex;

import java.beans.PropertyVetoException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0FileDatasource implements DataSource {

	private ComboPooledDataSource comboPooledDataSource;

	public C3p0FileDatasource() {
	}

	public C3p0FileDatasource(ComboPooledDataSource comboPooledDataSource) {
		this.comboPooledDataSource = comboPooledDataSource;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setComboPooledDataSource(
			ComboPooledDataSource comboPooledDataSource) {
		this.comboPooledDataSource = comboPooledDataSource;
	}

	public void setConnectionCustomizerClassName(
			String connectionCustomizerClassName) {
		this.comboPooledDataSource
				.setConnectionCustomizerClassName(connectionCustomizerClassName);
	}

	public void setConnectionPoolDataSource(
			ConnectionPoolDataSource connectionPoolDataSource)
			throws PropertyVetoException {
		this.comboPooledDataSource
				.setConnectionPoolDataSource(connectionPoolDataSource);
	}

	public void setConnectionTesterClassName(String connectionTesterClassName)
			throws PropertyVetoException {
		this.comboPooledDataSource
				.setConnectionTesterClassName(connectionTesterClassName);
	}

	public void setDataSourceName(String dataSourceName) {
		this.comboPooledDataSource.setDataSourceName(dataSourceName);
	}

	public void setDebugUnreturnedConnectionStackTraces(
			boolean debugUnreturnedConnectionStackTraces) {
		this.comboPooledDataSource
				.setDebugUnreturnedConnectionStackTraces(debugUnreturnedConnectionStackTraces);
	}

	public void setDescription(String description) {
		this.comboPooledDataSource.setDescription(description);
	}

	public void setDriverClass(String driverClass) throws PropertyVetoException {
		this.comboPooledDataSource.setDriverClass(driverClass);
	}

	public void setFactoryClassLocation(String factoryClassLocation) {
		this.comboPooledDataSource
				.setFactoryClassLocation(factoryClassLocation);
	}

	public void setForceIgnoreUnresolvedTransactions(
			boolean forceIgnoreUnresolvedTransactions) {
		this.comboPooledDataSource
				.setForceIgnoreUnresolvedTransactions(forceIgnoreUnresolvedTransactions);
	}

	public void setIdentityToken(String identityToken) {
		this.comboPooledDataSource.setIdentityToken(identityToken);
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.comboPooledDataSource
				.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.comboPooledDataSource.setInitialPoolSize(initialPoolSize);
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.comboPooledDataSource.setJdbcUrl(jdbcUrl);
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.comboPooledDataSource.setMaxPoolSize(maxPoolSize);
	}

	public void setMaxAdministrativeTaskTime(int maxAdministrativeTaskTime) {
		this.comboPooledDataSource
				.setMaxAdministrativeTaskTime(maxAdministrativeTaskTime);
	}

	public void setMaxConnectionAge(int maxConnectionAge) {
		this.comboPooledDataSource.setMaxConnectionAge(maxConnectionAge);
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.comboPooledDataSource.setMaxIdleTime(maxIdleTime);
	}

	public void setMaxIdleTimeExcessConnections(int maxIdleTimeExcessConnections) {
		this.comboPooledDataSource
				.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
	}

	public void setMaxStatements(int maxStatements) {
		this.comboPooledDataSource.setMaxStatements(maxStatements);
	}

	public void setMaxStatementsPerConnection(int maxStatementsPerConnection) {
		this.comboPooledDataSource
				.setMaxStatementsPerConnection(maxStatementsPerConnection);
	}

	public void setMinPoolSize(int minPoolSize) {
		this.comboPooledDataSource.setMinPoolSize(minPoolSize);
	}

	public void setNumHelperThreads(int numHelperThreads) {
		this.comboPooledDataSource.setNumHelperThreads(numHelperThreads);
	}

	public void setOverrideDefaultPassword(String overrideDefaultPassword) {
		this.comboPooledDataSource
				.setOverrideDefaultPassword(overrideDefaultPassword);
	}

	public void setOverrideDefaultUser(String overrideDefaultUser) {
		this.comboPooledDataSource.setOverrideDefaultUser(overrideDefaultUser);
	}

	public void setPassword(String password) {
		this.comboPooledDataSource.setPassword(password);
	}

	public void setPreferredTestQuery(String preferredTestQuery) {
		this.comboPooledDataSource.setPreferredTestQuery(preferredTestQuery);
	}

	public void setProperties(Properties properties) {
		this.comboPooledDataSource.setProperties(properties);
	}

	public void setPropertyCycle(int propertyCycle) {
		this.comboPooledDataSource.setPropertyCycle(propertyCycle);
	}

	public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
		this.comboPooledDataSource
				.setTestConnectionOnCheckin(testConnectionOnCheckin);
	}

	public void setUnreturnedConnectionTimeout(int unreturnedConnectionTimeout) {
		this.comboPooledDataSource
				.setUnreturnedConnectionTimeout(unreturnedConnectionTimeout);
	}

	public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		this.comboPooledDataSource
				.setTestConnectionOnCheckout(testConnectionOnCheckout);
	}

	public void setUser(String user) {
		this.comboPooledDataSource.setUser(user);
	}

	public void setUserOverridesAsString(String userOverridesAsString)
			throws PropertyVetoException {
		this.comboPooledDataSource
				.setUserOverridesAsString(userOverridesAsString);
	}

	public void setUsesTraditionalReflectiveProxies(
			boolean usesTraditionalReflectiveProxies) {
		this.comboPooledDataSource
				.setUsesTraditionalReflectiveProxies(usesTraditionalReflectiveProxies);
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.comboPooledDataSource.setAcquireIncrement(acquireIncrement);
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.comboPooledDataSource
				.setAcquireRetryAttempts(acquireRetryAttempts);
	}

	public void setAcquireRetryDelay(int acquireRetryDelay) {
		this.comboPooledDataSource.setAcquireRetryDelay(acquireRetryDelay);
	}

	public void setAutoCommitOnClose(boolean autoCommitOnClose) {
		this.comboPooledDataSource.setAutoCommitOnClose(autoCommitOnClose);
	}

	public void setAutomaticTestTable(String automaticTestTable) {
		this.comboPooledDataSource.setAutomaticTestTable(automaticTestTable);
	}

	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		this.comboPooledDataSource
				.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
	}

	public void setCheckoutTimeout(int checkoutTimeout) {
		this.comboPooledDataSource.setCheckoutTimeout(checkoutTimeout);
	}
}
