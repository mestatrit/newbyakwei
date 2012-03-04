package test;

import halo.datasource.c3p0ex.CnfPos;
import halo.datasource.c3p0ex.DataSourceCnf;
import halo.datasource.c3p0ex.C3p0Cnf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0CnfTest extends C3p0Cnf {

	@Test
	public void readFile() {
		List<String> tlist = new ArrayList<String>();
		tlist.add("ds0={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		String filePath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/ds0.txt";
		File file = new File(filePath);
		try {
			List<String> list = C3p0Cnf.readFile(file);
			Assert.assertNotNull(list);
			Assert.assertEquals(tlist.size(), list.size());
			for (int i = 0; i < tlist.size(); i++) {
				Assert.assertEquals(tlist.get(i), list.get(i));
			}
		}
		catch (IOException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void createCnfPosList() {
		CnfPos cnfPos0 = new CnfPos();
		cnfPos0.setBegin(0);
		cnfPos0.setEnd(10);
		CnfPos cnfPos1 = new CnfPos();
		cnfPos1.setBegin(11);
		cnfPos1.setEnd(21);
		List<CnfPos> cnflist = new ArrayList<CnfPos>();
		cnflist.add(cnfPos0);
		cnflist.add(cnfPos1);
		// 0
		List<String> tlist = new ArrayList<String>();
		tlist.add("ds0={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		// 1
		tlist.add("ds1={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		List<CnfPos> list = C3p0Cnf.createCnfPosList(tlist);
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
		for (int i = 0; i < list.size(); i++) {
			CnfPos actual = list.get(i);
			CnfPos expected = cnflist.get(i);
			Assert.assertEquals(expected.toString(), actual.toString());
		}
	}

	@Test
	public void createDataSourceCnf() {
		CnfPos cnfPos0 = new CnfPos();
		cnfPos0.setBegin(0);
		cnfPos0.setEnd(10);
		CnfPos cnfPos1 = new CnfPos();
		cnfPos1.setBegin(11);
		cnfPos1.setEnd(21);
		List<CnfPos> cnflist = new ArrayList<CnfPos>();
		cnflist.add(cnfPos0);
		cnflist.add(cnfPos1);
		// 0
		List<String> tlist = new ArrayList<String>();
		tlist.add("ds0={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		tlist.add("ds1={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		DataSourceCnf expected0 = new DataSourceCnf();
		expected0.setDsName("ds0");
		expected0.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver");
		expected0
				.setKeyAndValue(
						"jdbcUrl",
						"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		expected0.setKeyAndValue("user", "root");
		expected0.setKeyAndValue("password", "root_pwd");
		expected0.setKeyAndValue("maxStatements", "120");
		expected0.setKeyAndValue("idleConnectionTestPeriod", "60");
		expected0.setKeyAndValue("maxPoolSize", "400");
		expected0.setKeyAndValue("initialPoolSize", "20");
		expected0.setKeyAndValue("minPoolSize", "50");
		DataSourceCnf expected1 = new DataSourceCnf();
		expected1.setDsName("ds1");
		expected1.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver");
		expected1
				.setKeyAndValue(
						"jdbcUrl",
						"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		expected1.setKeyAndValue("user", "root");
		expected1.setKeyAndValue("password", "root_pwd");
		expected1.setKeyAndValue("maxStatements", "120");
		expected1.setKeyAndValue("idleConnectionTestPeriod", "60");
		expected1.setKeyAndValue("maxPoolSize", "400");
		expected1.setKeyAndValue("initialPoolSize", "20");
		expected1.setKeyAndValue("minPoolSize", "50");
		DataSourceCnf actual = C3p0Cnf.createDataSourceCnf(cnfPos0, tlist);
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected0.toString(), actual.toString());
		actual = C3p0Cnf.createDataSourceCnf(cnfPos1, tlist);
		Assert.assertNotNull(actual);
		Assert.assertEquals(expected1.toString(), actual.toString());
	}

	@Test
	public void parse() {
		CnfPos cnfPos0 = new CnfPos();
		cnfPos0.setBegin(0);
		cnfPos0.setEnd(10);
		CnfPos cnfPos1 = new CnfPos();
		cnfPos1.setBegin(11);
		cnfPos1.setEnd(21);
		List<CnfPos> cnflist = new ArrayList<CnfPos>();
		cnflist.add(cnfPos0);
		cnflist.add(cnfPos1);
		// 0
		List<String> tlist = new ArrayList<String>();
		tlist.add("ds0={");
		tlist.add("driverClass:com.mysql.jdbc.Driver0");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-80");
		tlist.add("user:root0");
		tlist.add("password:root_pwd0");
		tlist.add("maxStatements:1200");
		tlist.add("idleConnectionTestPeriod:600");
		tlist.add("maxPoolSize:4000");
		tlist.add("initialPoolSize:200");
		tlist.add("minPoolSize:500");
		tlist.add("}");
		// 1
		tlist.add("ds1={");
		tlist.add("driverClass:com.mysql.jdbc.Driver");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		tlist.add("user:root");
		tlist.add("password:root_pwd");
		tlist.add("maxStatements:120");
		tlist.add("idleConnectionTestPeriod:60");
		tlist.add("maxPoolSize:400");
		tlist.add("initialPoolSize:20");
		tlist.add("minPoolSize:50");
		tlist.add("}");
		// 2
		tlist.add("ds2={");
		tlist.add("driverClass:com.mysql.jdbc.Driver2");
		tlist.add("jdbcUrl:jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-82");
		tlist.add("user:root2");
		tlist.add("password:root_pwd2");
		tlist.add("maxStatements:1202");
		tlist.add("idleConnectionTestPeriod:602");
		tlist.add("maxPoolSize:4002");
		tlist.add("initialPoolSize:202");
		tlist.add("minPoolSize:502");
		tlist.add("}");
		DataSourceCnf expected0 = new DataSourceCnf();
		expected0.setDsName("ds0");
		expected0.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver0");
		expected0
				.setKeyAndValue(
						"jdbcUrl",
						"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-80");
		expected0.setKeyAndValue("user", "root0");
		expected0.setKeyAndValue("password", "root_pwd0");
		expected0.setKeyAndValue("maxStatements", "1200");
		expected0.setKeyAndValue("idleConnectionTestPeriod", "600");
		expected0.setKeyAndValue("maxPoolSize", "4000");
		expected0.setKeyAndValue("initialPoolSize", "200");
		expected0.setKeyAndValue("minPoolSize", "500");
		DataSourceCnf expected1 = new DataSourceCnf();
		expected1.setDsName("ds1");
		expected1.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver");
		expected1
				.setKeyAndValue(
						"jdbcUrl",
						"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		expected1.setKeyAndValue("user", "root");
		expected1.setKeyAndValue("password", "root_pwd");
		expected1.setKeyAndValue("maxStatements", "120");
		expected1.setKeyAndValue("idleConnectionTestPeriod", "60");
		expected1.setKeyAndValue("maxPoolSize", "400");
		expected1.setKeyAndValue("initialPoolSize", "20");
		expected1.setKeyAndValue("minPoolSize", "50");
		DataSourceCnf expected2 = new DataSourceCnf();
		expected2.setDsName("ds2");
		expected2.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver2");
		expected2
				.setKeyAndValue(
						"jdbcUrl",
						"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-82");
		expected2.setKeyAndValue("user", "root2");
		expected2.setKeyAndValue("password", "root_pwd2");
		expected2.setKeyAndValue("maxStatements", "1202");
		expected2.setKeyAndValue("idleConnectionTestPeriod", "602");
		expected2.setKeyAndValue("maxPoolSize", "4002");
		expected2.setKeyAndValue("initialPoolSize", "202");
		expected2.setKeyAndValue("minPoolSize", "502");
		Map<String, DataSourceCnf> expectedMap = new HashMap<String, DataSourceCnf>();
		expectedMap.put("ds0", expected0);
		expectedMap.put("ds1", expected1);
		expectedMap.put("ds2", expected2);
		Map<String, DataSourceCnf> actualMap = C3p0Cnf.parse(tlist);
		Assert.assertNotNull(actualMap);
		Assert.assertEquals(3, actualMap.size());
		Set<Entry<String, DataSourceCnf>> expectedSet = expectedMap.entrySet();
		List<Entry<String, DataSourceCnf>> expectedList = new ArrayList<Entry<String, DataSourceCnf>>(
				expectedSet);
		Set<Entry<String, DataSourceCnf>> actualSet = expectedMap.entrySet();
		List<Entry<String, DataSourceCnf>> actualList = new ArrayList<Entry<String, DataSourceCnf>>(
				actualSet);
		for (int i = 0; i < actualMap.size(); i++) {
			Entry<String, DataSourceCnf> expectedEntry = expectedList.get(i);
			Entry<String, DataSourceCnf> actualEntry = actualList.get(i);
			Assert.assertEquals(expectedEntry.getKey(), actualEntry.getKey());
			Assert.assertEquals(expectedEntry.getValue().toString(),
					actualEntry.getValue().toString());
		}
	}

	@Test
	public void createDataSourceCnfMapFromFile() {
		int size = 4;
		Map<String, DataSourceCnf> expectedMap = new HashMap<String, DataSourceCnf>();
		for (int i = 0; i < size; i++) {
			DataSourceCnf o = new DataSourceCnf();
			o.setDsName("dsName" + i);
			o.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver" + i);
			o.setKeyAndValue(
					"jdbcUrl",
					"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8"
							+ i);
			o.setKeyAndValue("user", "root" + i);
			o.setKeyAndValue("password", "root_pwd" + i);
			o.setKeyAndValue("maxStatements", "120" + i);
			o.setKeyAndValue("idleConnectionTestPeriod", "60" + i);
			o.setKeyAndValue("maxPoolSize", "400" + i);
			o.setKeyAndValue("initialPoolSize", "20" + i);
			o.setKeyAndValue("minPoolSize", "50" + i);
			expectedMap.put(o.getDsName(), o);
		}
		String filePath = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ "/ds.txt";
		File file = new File(filePath);
		Map<String, DataSourceCnf> actualMap = C3p0Cnf
				.createDataSourceCnfMapFromFile(file);
		Assert.assertNotNull(actualMap);
		Assert.assertEquals(4, actualMap.size());
		Set<Entry<String, DataSourceCnf>> expectedSet = expectedMap.entrySet();
		List<Entry<String, DataSourceCnf>> expectedList = new ArrayList<Entry<String, DataSourceCnf>>(
				expectedSet);
		Set<Entry<String, DataSourceCnf>> actualSet = expectedMap.entrySet();
		List<Entry<String, DataSourceCnf>> actualList = new ArrayList<Entry<String, DataSourceCnf>>(
				actualSet);
		for (int i = 0; i < actualMap.size(); i++) {
			Entry<String, DataSourceCnf> expectedEntry = expectedList.get(i);
			Entry<String, DataSourceCnf> actualEntry = actualList.get(i);
			Assert.assertEquals(expectedEntry.getKey(), actualEntry.getKey());
			Assert.assertEquals(expectedEntry.getValue().toString(),
					actualEntry.getValue().toString());
		}
	}

	@Test
	public void buildC3p0DataSource() {
		DataSourceCnf cnf = new DataSourceCnf();
		cnf.setDsName("ds");
		cnf.setKeyAndValue("driverClass", "com.mysql.jdbc.Driver");
		cnf.setKeyAndValue(
				"jdbcUrl",
				"jdbc:mysql://127.0.0.1:3306/orderdemo?useUnicode=true&amp;characterEncoding=UTF-8");
		cnf.setKeyAndValue("user", "root");
		cnf.setKeyAndValue("password", "boseeandbokee");
		cnf.setKeyAndValue("maxStatements", "120");
		cnf.setKeyAndValue("idleConnectionTestPeriod", "60");
		cnf.setKeyAndValue("maxPoolSize", "40");
		cnf.setKeyAndValue("initialPoolSize", "20");
		cnf.setKeyAndValue("minPoolSize", "5");
		ComboPooledDataSource dataSource = C3p0Cnf.buildC3p0DataSource(null,
				cnf);
		Assert.assertNotNull(dataSource);
		Assert.assertEquals(cnf.getValueForKey("driverClass"),
				dataSource.getDriverClass());
		Assert.assertEquals(cnf.getValueForKey("jdbcUrl"),
				dataSource.getJdbcUrl());
		Assert.assertEquals(cnf.getValueForKey("user"), dataSource.getUser());
		Assert.assertEquals(cnf.getValueForKey("password"),
				dataSource.getPassword());
		Assert.assertEquals(cnf.getValueForKey("maxStatements"),
				dataSource.getMaxStatements() + "");
		Assert.assertEquals(cnf.getValueForKey("idleConnectionTestPeriod"),
				dataSource.getIdleConnectionTestPeriod() + "");
		Assert.assertEquals(cnf.getValueForKey("maxPoolSize"),
				dataSource.getMaxPoolSize() + "");
		Assert.assertEquals(cnf.getValueForKey("initialPoolSize"),
				dataSource.getInitialPoolSize() + "");
		Assert.assertEquals(cnf.getValueForKey("minPoolSize"),
				dataSource.getMinPoolSize() + "");
	}
}