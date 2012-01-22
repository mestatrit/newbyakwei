package halo.datasource.c3p0ex;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CnfFileDatasource implements DataSource {

	private DataSource dataSource;

	private DataSourceCnfInfoWrapper dataSourceCnfInfoWrapper;

	private String cnfPath;

	private String dsName;

	public CnfFileDatasource() {
	}

	public void setDataSourceCnfInfoWrapper(
			DataSourceCnfInfoWrapper dataSourceCnfInfoWrapper) {
		this.dataSourceCnfInfoWrapper = dataSourceCnfInfoWrapper;
	}

	/**
	 * 数据库配置文件路径
	 * 
	 * @param cnfPath
	 */
	public void setCnfPath(String cnfPath) {
		this.cnfPath = cnfPath;
	}

	/**
	 * 数据源名称
	 * 
	 * @param dsName
	 */
	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void init() {
		this.dataSource = this.createDataSource();
	}

	/**
	 * 使用配置文件，创建c3p0连接池
	 * 
	 * @return
	 */
	private DataSource createDataSource() {
		File file = new File(cnfPath);
		DataSourceCnf dataSourceCnf = this.getDataSourceCnf(file, dsName);
		return this.buildC3p0DataSource(dataSourceCnf);
	}

	private ComboPooledDataSource buildC3p0DataSource(
			DataSourceCnf dataSourceCnf) {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		Map<String, String> map = dataSourceCnf.getCnfMap();
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> e : set) {
			String fieldName = e.getKey();
			String value = e.getValue();
			setValue(comboPooledDataSource, fieldName, value);
		}
		return comboPooledDataSource;
	}

	private void setValue(ComboPooledDataSource comboPooledDataSource,
			String fieldName, String value) {
		try {
			if (fieldName.equals("driverClass")) {
				comboPooledDataSource.setDriverClass(value);
			}
			else if (fieldName.equals("jdbcUrl")) {
				comboPooledDataSource.setJdbcUrl(value);
			}
			else if (fieldName.equals("user")) {
				comboPooledDataSource.setUser(value);
			}
			else if (fieldName.equals("password")) {
				if (this.dataSourceCnfInfoWrapper == null) {
					comboPooledDataSource.setPassword(value);
				}
				else {
					String pwd = this.dataSourceCnfInfoWrapper
							.getDecodedPassword(value);
					comboPooledDataSource.setPassword(pwd);
				}
			}
			else if (fieldName.equals("maxStatements")) {
				comboPooledDataSource.setMaxStatements(Integer.valueOf(value));
			}
			else if (fieldName.equals("idleConnectionTestPeriod")) {
				comboPooledDataSource.setIdleConnectionTestPeriod(Integer
						.valueOf(value));
			}
			else if (fieldName.equals("maxPoolSize")) {
				comboPooledDataSource.setMaxPoolSize(Integer.valueOf(value));
			}
			else if (fieldName.equals("initialPoolSize")) {
				comboPooledDataSource
						.setInitialPoolSize(Integer.valueOf(value));
			}
			else if (fieldName.equals("minPoolSize")) {
				comboPooledDataSource.setMinPoolSize(Integer.valueOf(value));
			}
		}
		catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建配置文件map
	 * 
	 * @param file
	 *            配置文件
	 * @return
	 */
	protected static Map<String, DataSourceCnf> createDataSourceCnfMapFromFile(
			File file) {
		try {
			List<String> list = readFile(file);
			return parse(list);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected static Map<String, DataSourceCnf> parse(final List<String> list) {
		List<CnfPos> olist = createCnfPosList(list);
		Map<String, DataSourceCnf> map = new HashMap<String, DataSourceCnf>();
		DataSourceCnf dataSourceCnf = null;
		for (CnfPos o : olist) {
			dataSourceCnf = createDataSourceCnf(o, list);
			map.put(dataSourceCnf.getDsName(), dataSourceCnf);
		}
		return map;
	}

	protected static DataSourceCnf createDataSourceCnf(final CnfPos cnfPos,
			final List<String> list) {
		DataSourceCnf o = new DataSourceCnf();
		String dsName = list.subList(cnfPos.getBegin(), cnfPos.getBegin() + 1)
				.get(0).replaceAll("=\\{", "");
		o.setDsName(dsName);
		List<String> olist = list.subList(cnfPos.getBegin() + 1,
				cnfPos.getEnd());
		for (String s : olist) {
			String[] k_v = s.split(":", 2);
			o.setKeyAndValue(k_v[0], k_v[1]);
		}
		return o;
	}

	protected static List<CnfPos> createCnfPosList(final List<String> list) {
		int cnfBegin = -1;
		int cnfEnd = -1;
		List<CnfPos> olist = new ArrayList<CnfPos>();
		String s = null;
		for (int i = 0; i < list.size(); i++) {
			s = list.get(i);
			if (s.startsWith("#")) {
				continue;
			}
			if (s.endsWith("={")) {// 寻找开始位置
				cnfBegin = i;
			}
			if (s.endsWith("}")) {// 寻找结束位置
				cnfEnd = i;
				CnfPos cnfPos = new CnfPos();
				cnfPos.setBegin(cnfBegin);
				cnfPos.setEnd(cnfEnd);
				olist.add(cnfPos);
			}
		}
		return olist;
	}

	protected static List<String> readFile(File file) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "utf-8"));
			List<String> list = new ArrayList<String>();
			String s = null;
			while ((s = reader.readLine()) != null) {
				list.add(s.trim());
			}
			return list;
		}
		catch (IOException e) {
			throw e;
		}
		finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * 获取指定与dsName匹配的配置信息
	 * 
	 * @param file
	 *            配置文件
	 * @param dsName
	 * @return 如果不存在就返回null
	 */
	private DataSourceCnf getDataSourceCnf(File file, String dsName) {
		Map<String, DataSourceCnf> map = createDataSourceCnfMapFromFile(file);
		return map.get(dsName);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return this.dataSource.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return this.dataSource.getLoginTimeout();
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		this.dataSource.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		this.dataSource.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return this.dataSource.getConnection(arg0, arg1);
	}
}
