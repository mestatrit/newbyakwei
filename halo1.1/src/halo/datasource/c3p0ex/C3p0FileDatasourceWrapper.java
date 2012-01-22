package halo.datasource.c3p0ex;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0FileDatasourceWrapper implements DataSource {

	private ComboPooledDataSource comboPooledDataSource;

	public C3p0FileDatasourceWrapper() {
	}

	public C3p0FileDatasourceWrapper(ComboPooledDataSource comboPooledDataSource) {
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

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		comboPooledDataSource.addPropertyChangeListener(pcl);
	}

	public void addPropertyChangeListener(String propName,
			PropertyChangeListener pcl) {
		comboPooledDataSource.addPropertyChangeListener(propName, pcl);
	}

	public void addVetoableChangeListener(VetoableChangeListener vcl) {
		comboPooledDataSource.addVetoableChangeListener(vcl);
	}

	public boolean equals(Object o) {
		return comboPooledDataSource.equals(o);
	}

	public int hashCode() {
		return comboPooledDataSource.hashCode();
	}

	public String getDataSourceName() {
		return comboPooledDataSource.getDataSourceName();
	}

	public int getNumConnections() throws SQLException {
		return comboPooledDataSource.getNumConnections();
	}

	public int getNumBusyConnections() throws SQLException {
		return comboPooledDataSource.getNumBusyConnections();
	}

	public int getNumUnclosedOrphanedConnections() throws SQLException {
		return comboPooledDataSource.getNumUnclosedOrphanedConnections();
	}

	public int getNumConnectionsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumConnectionsDefaultUser();
	}

	public int getNumBusyConnectionsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumBusyConnectionsDefaultUser();
	}

	public int getNumUnclosedOrphanedConnectionsDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.getNumUnclosedOrphanedConnectionsDefaultUser();
	}

	public int getStatementCacheNumStatementsDefaultUser() throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumStatementsDefaultUser();
	}

	public int getStatementCacheNumCheckedOutDefaultUser() throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumCheckedOutDefaultUser();
	}

	public int getStatementCacheNumConnectionsWithCachedStatementsDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumConnectionsWithCachedStatementsDefaultUser();
	}

	public float getEffectivePropertyCycleDefaultUser() throws SQLException {
		return comboPooledDataSource.getEffectivePropertyCycleDefaultUser();
	}

	public long getUpTimeMillisDefaultUser() throws SQLException {
		return comboPooledDataSource.getUpTimeMillisDefaultUser();
	}

	public long getNumFailedCheckinsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumFailedCheckinsDefaultUser();
	}

	public long getNumFailedCheckoutsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumFailedCheckoutsDefaultUser();
	}

	public long getNumFailedIdleTestsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumFailedIdleTestsDefaultUser();
	}

	public int getNumThreadsAwaitingCheckoutDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumThreadsAwaitingCheckoutDefaultUser();
	}

	public int getThreadPoolSize() throws SQLException {
		return comboPooledDataSource.getThreadPoolSize();
	}

	public int getThreadPoolNumActiveThreads() throws SQLException {
		return comboPooledDataSource.getThreadPoolNumActiveThreads();
	}

	public String getDescription() {
		return comboPooledDataSource.getDescription();
	}

	public int getThreadPoolNumIdleThreads() throws SQLException {
		return comboPooledDataSource.getThreadPoolNumIdleThreads();
	}

	public int getThreadPoolNumTasksPending() throws SQLException {
		return comboPooledDataSource.getThreadPoolNumTasksPending();
	}

	public void setDescription(String description) {
		comboPooledDataSource.setDescription(description);
	}

	public String getDriverClass() {
		return comboPooledDataSource.getDriverClass();
	}

	public void setDriverClass(String driverClass) throws PropertyVetoException {
		comboPooledDataSource.setDriverClass(driverClass);
	}

	public Properties getProperties() {
		return comboPooledDataSource.getProperties();
	}

	public String getUser() {
		return comboPooledDataSource.getUser();
	}

	public void setUser(String user) {
		comboPooledDataSource.setUser(user);
	}

	public String getPassword() {
		return comboPooledDataSource.getPassword();
	}

	public int getCheckoutTimeout() {
		return comboPooledDataSource.getCheckoutTimeout();
	}

	public void setCheckoutTimeout(int checkoutTimeout) {
		comboPooledDataSource.setCheckoutTimeout(checkoutTimeout);
	}

	public int getAcquireIncrement() {
		return comboPooledDataSource.getAcquireIncrement();
	}

	public int getAcquireRetryAttempts() {
		return comboPooledDataSource.getAcquireRetryAttempts();
	}

	public int getAcquireRetryDelay() {
		return comboPooledDataSource.getAcquireRetryDelay();
	}

	public void setAcquireRetryDelay(int acquireRetryDelay) {
		comboPooledDataSource.setAcquireRetryDelay(acquireRetryDelay);
	}

	public boolean isAutoCommitOnClose() {
		return comboPooledDataSource.isAutoCommitOnClose();
	}

	public void setAutoCommitOnClose(boolean autoCommitOnClose) {
		comboPooledDataSource.setAutoCommitOnClose(autoCommitOnClose);
	}

	public String getAutomaticTestTable() {
		return comboPooledDataSource.getAutomaticTestTable();
	}

	public void setAutomaticTestTable(String automaticTestTable) {
		comboPooledDataSource.setAutomaticTestTable(automaticTestTable);
	}

	public boolean isForceIgnoreUnresolvedTransactions() {
		return comboPooledDataSource.isForceIgnoreUnresolvedTransactions();
	}

	public void setForceIgnoreUnresolvedTransactions(
			boolean forceIgnoreUnresolvedTransactions) {
		comboPooledDataSource
				.setForceIgnoreUnresolvedTransactions(forceIgnoreUnresolvedTransactions);
	}

	public void softResetDefaultUser() throws SQLException {
		comboPooledDataSource.softResetDefaultUser();
	}

	public void setMaxPoolSize(int maxPoolSize) {
		comboPooledDataSource.setMaxPoolSize(maxPoolSize);
	}

	public int getNumConnections(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumConnections(username, password);
	}

	public void setMaxStatements(int maxStatements) {
		comboPooledDataSource.setMaxStatements(maxStatements);
	}

	public int getNumBusyConnections(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumBusyConnections(username, password);
	}

	public void setMaxStatementsPerConnection(int maxStatementsPerConnection) {
		comboPooledDataSource
				.setMaxStatementsPerConnection(maxStatementsPerConnection);
	}

	public int getNumUnclosedOrphanedConnections(String username,
			String password) throws SQLException {
		return comboPooledDataSource.getNumUnclosedOrphanedConnections(
				username, password);
	}

	public int getMinPoolSize() {
		return comboPooledDataSource.getMinPoolSize();
	}

	public void setMinPoolSize(int minPoolSize) {
		comboPooledDataSource.setMinPoolSize(minPoolSize);
	}

	public int getStatementCacheNumStatements(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getStatementCacheNumStatements(username,
				password);
	}

	public String getOverrideDefaultUser() {
		return comboPooledDataSource.getOverrideDefaultUser();
	}

	public int getStatementCacheNumCheckedOut(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getStatementCacheNumCheckedOut(username,
				password);
	}

	public int getStatementCacheNumConnectionsWithCachedStatements(
			String username, String password) throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumConnectionsWithCachedStatements(username,
						password);
	}

	public String getOverrideDefaultPassword() {
		return comboPooledDataSource.getOverrideDefaultPassword();
	}

	public float getEffectivePropertyCycle(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getEffectivePropertyCycle(username,
				password);
	}

	public int getPropertyCycle() {
		return comboPooledDataSource.getPropertyCycle();
	}

	public void setPropertyCycle(int propertyCycle) {
		comboPooledDataSource.setPropertyCycle(propertyCycle);
	}

	public long getUpTimeMillis(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getUpTimeMillis(username, password);
	}

	public boolean isBreakAfterAcquireFailure() {
		return comboPooledDataSource.isBreakAfterAcquireFailure();
	}

	public void setBreakAfterAcquireFailure(boolean breakAfterAcquireFailure) {
		comboPooledDataSource
				.setBreakAfterAcquireFailure(breakAfterAcquireFailure);
	}

	public long getNumFailedCheckins(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumFailedCheckins(username, password);
	}

	public long getNumFailedCheckouts(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumFailedCheckouts(username, password);
	}

	public boolean isTestConnectionOnCheckout() {
		return comboPooledDataSource.isTestConnectionOnCheckout();
	}

	public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout) {
		comboPooledDataSource
				.setTestConnectionOnCheckout(testConnectionOnCheckout);
	}

	public long getNumFailedIdleTests(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumFailedIdleTests(username, password);
	}

	public boolean isTestConnectionOnCheckin() {
		return comboPooledDataSource.isTestConnectionOnCheckin();
	}

	public void softReset(String username, String password) throws SQLException {
		comboPooledDataSource.softReset(username, password);
	}

	public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin) {
		comboPooledDataSource
				.setTestConnectionOnCheckin(testConnectionOnCheckin);
	}

	public int getNumBusyConnectionsAllUsers() throws SQLException {
		return comboPooledDataSource.getNumBusyConnectionsAllUsers();
	}

	public boolean isUsesTraditionalReflectiveProxies() {
		return comboPooledDataSource.isUsesTraditionalReflectiveProxies();
	}

	public void setUsesTraditionalReflectiveProxies(
			boolean usesTraditionalReflectiveProxies) {
		comboPooledDataSource
				.setUsesTraditionalReflectiveProxies(usesTraditionalReflectiveProxies);
	}

	public int getNumConnectionsAllUsers() throws SQLException {
		return comboPooledDataSource.getNumConnectionsAllUsers();
	}

	public int getStatementCacheNumStatementsAllUsers() throws SQLException {
		return comboPooledDataSource.getStatementCacheNumStatementsAllUsers();
	}

	public String getUserOverridesAsString() {
		return comboPooledDataSource.getUserOverridesAsString();
	}

	public void setUserOverridesAsString(String userOverridesAsString)
			throws PropertyVetoException {
		comboPooledDataSource.setUserOverridesAsString(userOverridesAsString);
	}

	public void softResetAllUsers() throws SQLException {
		comboPooledDataSource.softResetAllUsers();
	}

	public Collection getAllUsers() throws SQLException {
		return comboPooledDataSource.getAllUsers();
	}

	public void setMaxIdleTimeExcessConnections(int maxIdleTimeExcessConnections) {
		comboPooledDataSource
				.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
	}

	public void hardReset() {
		comboPooledDataSource.hardReset();
	}

	public void close() {
		comboPooledDataSource.close();
	}

	public String getConnectionCustomizerClassName() {
		return comboPooledDataSource.getConnectionCustomizerClassName();
	}

	public void setConnectionCustomizerClassName(
			String connectionCustomizerClassName) {
		comboPooledDataSource
				.setConnectionCustomizerClassName(connectionCustomizerClassName);
	}

	public void close(boolean force_destroy) {
		comboPooledDataSource.close(force_destroy);
	}

	public ConnectionPoolDataSource getConnectionPoolDataSource() {
		return comboPooledDataSource.getConnectionPoolDataSource();
	}

	public String getConnectionTesterClassName() {
		return comboPooledDataSource.getConnectionTesterClassName();
	}

	public int getUnreturnedConnectionTimeout() {
		return comboPooledDataSource.getUnreturnedConnectionTimeout();
	}

	public boolean isDebugUnreturnedConnectionStackTraces() {
		return comboPooledDataSource.isDebugUnreturnedConnectionStackTraces();
	}

	public String getFactoryClassLocation() {
		return comboPooledDataSource.getFactoryClassLocation();
	}

	public String getIdentityToken() {
		return comboPooledDataSource.getIdentityToken();
	}

	public String getJdbcUrl() {
		return comboPooledDataSource.getJdbcUrl();
	}

	public Throwable getLastAcquisitionFailureDefaultUser() throws SQLException {
		return comboPooledDataSource.getLastAcquisitionFailureDefaultUser();
	}

	public Throwable getLastCheckinFailureDefaultUser() throws SQLException {
		return comboPooledDataSource.getLastCheckinFailureDefaultUser();
	}

	public Throwable getLastCheckoutFailureDefaultUser() throws SQLException {
		return comboPooledDataSource.getLastCheckoutFailureDefaultUser();
	}

	public Throwable getLastIdleTestFailureDefaultUser() throws SQLException {
		return comboPooledDataSource.getLastIdleTestFailureDefaultUser();
	}

	public Throwable getLastConnectionTestFailureDefaultUser()
			throws SQLException {
		return comboPooledDataSource.getLastConnectionTestFailureDefaultUser();
	}

	public Throwable getLastAcquisitionFailure(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getLastAcquisitionFailure(username,
				password);
	}

	public Throwable getLastCheckinFailure(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getLastCheckinFailure(username, password);
	}

	public Throwable getLastCheckoutFailure(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getLastCheckoutFailure(username, password);
	}

	public Throwable getLastIdleTestFailure(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getLastIdleTestFailure(username, password);
	}

	public Throwable getLastConnectionTestFailure(String username,
			String password) throws SQLException {
		return comboPooledDataSource.getLastConnectionTestFailure(username,
				password);
	}

	public int getIdleConnectionTestPeriod() {
		return comboPooledDataSource.getIdleConnectionTestPeriod();
	}

	public int getInitialPoolSize() {
		return comboPooledDataSource.getInitialPoolSize();
	}

	public int getMaxIdleTime() {
		return comboPooledDataSource.getMaxIdleTime();
	}

	public int getMaxPoolSize() {
		return comboPooledDataSource.getMaxPoolSize();
	}

	public int getMaxStatements() {
		return comboPooledDataSource.getMaxStatements();
	}

	public int getMaxStatementsPerConnection() {
		return comboPooledDataSource.getMaxStatementsPerConnection();
	}

	public int getMaxAdministrativeTaskTime() {
		return comboPooledDataSource.getMaxAdministrativeTaskTime();
	}

	public int getMaxIdleTimeExcessConnections() {
		return comboPooledDataSource.getMaxIdleTimeExcessConnections();
	}

	public int getMaxConnectionAge() {
		return comboPooledDataSource.getMaxConnectionAge();
	}

	public int getNumHelperThreads() {
		return comboPooledDataSource.getNumHelperThreads();
	}

	public int getNumIdleConnections() throws SQLException {
		return comboPooledDataSource.getNumIdleConnections();
	}

	public int getNumIdleConnectionsDefaultUser() throws SQLException {
		return comboPooledDataSource.getNumIdleConnectionsDefaultUser();
	}

	public int getNumThreadsAwaitingCheckout(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumThreadsAwaitingCheckout(username,
				password);
	}

	public int getNumIdleConnections(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getNumIdleConnections(username, password);
	}

	public int getNumIdleConnectionsAllUsers() throws SQLException {
		return comboPooledDataSource.getNumIdleConnectionsAllUsers();
	}

	public int getNumUnclosedOrphanedConnectionsAllUsers() throws SQLException {
		return comboPooledDataSource
				.getNumUnclosedOrphanedConnectionsAllUsers();
	}

	public String getPreferredTestQuery() {
		return comboPooledDataSource.getPreferredTestQuery();
	}

	public int getNumUserPools() throws SQLException {
		return comboPooledDataSource.getNumUserPools();
	}

	public Reference getReference() throws NamingException {
		return comboPooledDataSource.getReference();
	}

	public long getStartTimeMillisDefaultUser() throws SQLException {
		return comboPooledDataSource.getStartTimeMillisDefaultUser();
	}

	public long getStartTimeMillis(String username, String password)
			throws SQLException {
		return comboPooledDataSource.getStartTimeMillis(username, password);
	}

	public int getStatementCacheNumCheckedOutStatementsAllUsers()
			throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumCheckedOutStatementsAllUsers();
	}

	public int getStatementCacheNumConnectionsWithCachedStatementsAllUsers()
			throws SQLException {
		return comboPooledDataSource
				.getStatementCacheNumConnectionsWithCachedStatementsAllUsers();
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		comboPooledDataSource.removePropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(String propName,
			PropertyChangeListener pcl) {
		comboPooledDataSource.removePropertyChangeListener(propName, pcl);
	}

	public void removeVetoableChangeListener(VetoableChangeListener vcl) {
		comboPooledDataSource.removeVetoableChangeListener(vcl);
	}

	public String sampleThreadPoolStackTraces() throws SQLException {
		return comboPooledDataSource.sampleThreadPoolStackTraces();
	}

	public String sampleThreadPoolStatus() throws SQLException {
		return comboPooledDataSource.sampleThreadPoolStatus();
	}

	public String sampleStatementCacheStatusDefaultUser() throws SQLException {
		return comboPooledDataSource.sampleStatementCacheStatusDefaultUser();
	}

	public String sampleStatementCacheStatus(String username, String password)
			throws SQLException {
		return comboPooledDataSource.sampleStatementCacheStatus(username,
				password);
	}

	public void setAcquireIncrement(int acquireIncrement) {
		comboPooledDataSource.setAcquireIncrement(acquireIncrement);
	}

	public String sampleLastAcquisitionFailureStackTraceDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.sampleLastAcquisitionFailureStackTraceDefaultUser();
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		comboPooledDataSource.setAcquireRetryAttempts(acquireRetryAttempts);
	}

	public String sampleLastCheckinFailureStackTraceDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.sampleLastCheckinFailureStackTraceDefaultUser();
	}

	public String sampleLastCheckoutFailureStackTraceDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.sampleLastCheckoutFailureStackTraceDefaultUser();
	}

	public String sampleLastIdleTestFailureStackTraceDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.sampleLastIdleTestFailureStackTraceDefaultUser();
	}

	public String sampleLastConnectionTestFailureStackTraceDefaultUser()
			throws SQLException {
		return comboPooledDataSource
				.sampleLastConnectionTestFailureStackTraceDefaultUser();
	}

	public String sampleLastAcquisitionFailureStackTrace(String username,
			String password) throws SQLException {
		return comboPooledDataSource.sampleLastAcquisitionFailureStackTrace(
				username, password);
	}

	public String sampleLastCheckinFailureStackTrace(String username,
			String password) throws SQLException {
		return comboPooledDataSource.sampleLastCheckinFailureStackTrace(
				username, password);
	}

	public String sampleLastCheckoutFailureStackTrace(String username,
			String password) throws SQLException {
		return comboPooledDataSource.sampleLastCheckoutFailureStackTrace(
				username, password);
	}

	public String sampleLastIdleTestFailureStackTrace(String username,
			String password) throws SQLException {
		return comboPooledDataSource.sampleLastIdleTestFailureStackTrace(
				username, password);
	}

	public String sampleLastConnectionTestFailureStackTrace(String username,
			String password) throws SQLException {
		return comboPooledDataSource.sampleLastConnectionTestFailureStackTrace(
				username, password);
	}

	public void resetPoolManager() {
		comboPooledDataSource.resetPoolManager();
	}

	public void resetPoolManager(boolean close_checked_out_connections) {
		comboPooledDataSource.resetPoolManager(close_checked_out_connections);
	}

	public void setConnectionPoolDataSource(
			ConnectionPoolDataSource connectionPoolDataSource)
			throws PropertyVetoException {
		comboPooledDataSource
				.setConnectionPoolDataSource(connectionPoolDataSource);
	}

	public void setConnectionTesterClassName(String connectionTesterClassName)
			throws PropertyVetoException {
		comboPooledDataSource
				.setConnectionTesterClassName(connectionTesterClassName);
	}

	public void setDataSourceName(String dataSourceName) {
		comboPooledDataSource.setDataSourceName(dataSourceName);
	}

	public void setDebugUnreturnedConnectionStackTraces(
			boolean debugUnreturnedConnectionStackTraces) {
		comboPooledDataSource
				.setDebugUnreturnedConnectionStackTraces(debugUnreturnedConnectionStackTraces);
	}

	public void setFactoryClassLocation(String factoryClassLocation) {
		comboPooledDataSource.setFactoryClassLocation(factoryClassLocation);
	}

	public void setIdentityToken(String identityToken) {
		comboPooledDataSource.setIdentityToken(identityToken);
	}

	public void setJdbcUrl(String jdbcUrl) {
		comboPooledDataSource.setJdbcUrl(jdbcUrl);
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		comboPooledDataSource
				.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
	}

	public void setInitialPoolSize(int initialPoolSize) {
		comboPooledDataSource.setInitialPoolSize(initialPoolSize);
	}

	public void setMaxIdleTime(int maxIdleTime) {
		comboPooledDataSource.setMaxIdleTime(maxIdleTime);
	}

	public void setMaxAdministrativeTaskTime(int maxAdministrativeTaskTime) {
		comboPooledDataSource
				.setMaxAdministrativeTaskTime(maxAdministrativeTaskTime);
	}

	public void setMaxConnectionAge(int maxConnectionAge) {
		comboPooledDataSource.setMaxConnectionAge(maxConnectionAge);
	}

	public void setNumHelperThreads(int numHelperThreads) {
		comboPooledDataSource.setNumHelperThreads(numHelperThreads);
	}

	public void setProperties(Properties properties) {
		comboPooledDataSource.setProperties(properties);
	}

	public void setPassword(String password) {
		comboPooledDataSource.setPassword(password);
	}

	public void setOverrideDefaultUser(String overrideDefaultUser) {
		comboPooledDataSource.setOverrideDefaultUser(overrideDefaultUser);
	}

	public void setOverrideDefaultPassword(String overrideDefaultPassword) {
		comboPooledDataSource
				.setOverrideDefaultPassword(overrideDefaultPassword);
	}

	public void setPreferredTestQuery(String preferredTestQuery) {
		comboPooledDataSource.setPreferredTestQuery(preferredTestQuery);
	}

	public void setUnreturnedConnectionTimeout(int unreturnedConnectionTimeout) {
		comboPooledDataSource
				.setUnreturnedConnectionTimeout(unreturnedConnectionTimeout);
	}

	public String toString() {
		return comboPooledDataSource.toString();
	}
}
