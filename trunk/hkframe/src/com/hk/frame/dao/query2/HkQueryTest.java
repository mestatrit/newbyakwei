package com.hk.frame.dao.query2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
// @Transactional
public class HkQueryTest {

	@Test
	public void testCreateSQL() {
		String create_sql = "insert into user(userid,nick,gender) values(?,?,?)";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_create_sql = hkQuery.getInsertSQL(partitionTableInfo,
				new String[] { "userid", "nick", "gender" });
		Assert.assertEquals(create_sql, res_create_sql);
	}

	@Test
	public void testUpdateSQL() {
		String update_sql = "update user set nick=?,gender=? where userid=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_update_sql = hkQuery.getUpdateSQL(partitionTableInfo,
				new String[] { "nick", "gender" }, "userid=?");
		Assert.assertEquals(update_sql, res_update_sql);
	}

	@Test
	public void testCountSQL1() {
		String count_sql = "select count(*) from user where userid=? and nick=? and gender=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery.getCountSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				"userid=? and nick=? and gender=?");
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testCountSQL2() {
		String count_sql = "select count(*) from user,info where user.userid=? and user.nick=?"
				+ " and user.gender=? and info.userid=? and info.nick=? and user.userid=info.userid";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery
				.getCountSQL(
						new PartitionTableInfo[] { partitionTableInfo1,
								partitionTableInfo2 },
						"1.userid=? and 1.nick=?"
								+ " and 1.gender=? and 2.userid=? and 2.nick=? and 1.userid=2.userid");
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testSelectSQL1() {
		String select_sql = "select user.userid,user.nick,user.gender from user where userid=? and nick=? and gender=? order by createtime desc,nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "userid", "nick", "gender" } },
				"userid=? and nick=? and gender=?", "createtime desc,nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL2() {
		String select_sql = "select user.userid,user.nick,user.gender,user.createtime,"
				+ "info.userid,info.birthday,info.fansnum "
				+ "from user,info where user.userid=info.userid and user.userid=? and user.nick=? "
				+ "and user.gender=? and info.birthday>? "
				+ "order by user.createtime desc,user.nick asc,info.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "userid", "nick", "gender", "createtime" },
				{ "userid", "birthday", "fansnum" } },
				"1.userid=2.userid and 1.userid=? and 1.nick=? "
						+ "and 1.gender=? and 2.birthday>?",
				"1.createtime desc,1.nick asc,2.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL1() {
		String select_sql = "select user.userid,user.nick,user.gender from user where userid=? and nick=? and gender=? order by createtime desc,nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "userid", "nick", "gender" } },
				"userid=? and nick=? and gender=?", "createtime desc,nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL2() {
		String select_sql = "select user.userid,user.nick,user.gender,user.createtime,"
				+ "info.userid,info.birthday,info.fansnum "
				+ "from user,info where user.userid=info.userid and user.userid=? and user.nick=? "
				+ "and user.gender=? and info.birthday>? "
				+ "order by user.createtime desc,user.nick asc,info.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getObjectSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "userid", "nick", "gender", "createtime" },
				{ "userid", "birthday", "fansnum" } },
				"1.userid=2.userid and 1.userid=? and 1.nick=? "
						+ "and 1.gender=? and 2.birthday>?",
				"1.createtime desc,1.nick asc,2.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}
}