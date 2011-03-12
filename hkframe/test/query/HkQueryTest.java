package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import bean.TestUser;

import com.hk.frame.dao.query2.HkQuery;
import com.hk.frame.dao.query2.PartitionTableInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
@Transactional
public class HkQueryTest {

	@Resource
	private HkQuery hkQuery;

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
	public void testUpdateSQL1() {
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
	public void testUpdateSQL2() {
		String update_sql = "update user set nick=?,gender=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		HkQuery hkQuery = new HkQuery();
		String res_update_sql = hkQuery.getUpdateSQL(partitionTableInfo,
				new String[] { "nick", "gender" }, null);
		Assert.assertEquals(update_sql, res_update_sql);
	}

	@Test
	public void testDeleteSQL1() {
		String delete_sql = "delete from user where userid=? and nick=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_delete_sql = hkQuery.getDeleteSQL(partitionTableInfo,
				"userid=? and nick=?");
		Assert.assertEquals(delete_sql, res_delete_sql);
	}

	@Test
	public void testDeleteSQL2() {
		String delete_sql = "delete from user";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_delete_sql = hkQuery.getDeleteSQL(partitionTableInfo, null);
		Assert.assertEquals(delete_sql, res_delete_sql);
	}

	@Test
	public void testCountSQL1() {
		String count_sql = "select count(*) from user u where u.userid=? and u.nick=? and u.gender=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery.getCountSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				"u.userid=? and u.nick=? and u.gender=?");
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testCountSQL2() {
		String count_sql = "select count(*) from user u,info i where u.userid=? and u.nick=?"
				+ " and u.gender=? and i.userid=? and i.nick=? and u.userid=i.userid";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery
				.getCountSQL(
						new PartitionTableInfo[] { partitionTableInfo1,
								partitionTableInfo2 },
						"u.userid=? and u.nick=?"
								+ " and u.gender=? and i.userid=? and i.nick=? and u.userid=i.userid");
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testCountSQL3() {
		String count_sql = "select count(*) from user u";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery.getCountSQL(
				new PartitionTableInfo[] { partitionTableInfo }, null);
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testCountSQL4() {
		String count_sql = "select count(*) from user u,info i";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_count_sql = hkQuery.getCountSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, null);
		Assert.assertEquals(count_sql, res_count_sql);
	}

	@Test
	public void testSelectSQL1() {
		String select_sql = "select u.userid,u.nick,u.gender from user u where u.userid=? and u.nick=? and u.gender=? "
				+ "order by u.createtime desc,u.nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } },
				"u.userid=? and u.nick=? and u.gender=?",
				"u.createtime desc,u.nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL2() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum "
				+ "from user u,info i where u.userid=i.userid and u.userid=? and u.nick=? "
				+ "and u.gender=? and i.birthday>? "
				+ "order by u.createtime desc,u.nick asc,i.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } },
				"u.userid=i.userid and u.userid=? and u.nick=? "
						+ "and u.gender=? and i.birthday>?",
				"u.createtime desc,u.nick asc,i.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL3() {
		String select_sql = "select u.userid,u.nick,u.gender from user u "
				+ "order by u.createtime desc,u.nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } }, null,
				"u.createtime desc,u.nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL4() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum " + "from user u,info i "
				+ "order by u.createtime desc,u.nick asc,i.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } }, null,
				"u.createtime desc,u.nick asc,i.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL5() {
		String select_sql = "select u.userid,u.nick,u.gender from user u";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } }, null,
				null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL6() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum " + "from user u,info i";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } }, null, null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL7() {
		String select_sql = "select u.userid,u.nick,u.gender from user u where u.userid=? and u.nick=? and u.gender=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } },
				"u.userid=? and u.nick=? and u.gender=?", null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testSelectSQL8() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum "
				+ "from user u,info i where u.userid=i.userid and u.userid=? and u.nick=? "
				+ "and u.gender=? and i.birthday>?";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } },
				"u.userid=i.userid and u.userid=? and u.nick=? "
						+ "and u.gender=? and i.birthday>?", null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL1() {
		String select_sql = "select u.userid,u.nick,u.gender from user u where userid=? and nick=? and gender=? order by createtime desc,nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } },
				"userid=? and nick=? and gender=?", "createtime desc,nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL2() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum "
				+ "from user u,info i where u.userid=i.userid and u.userid=? and u.nick=? "
				+ "and u.gender=? and i.birthday>? "
				+ "order by u.createtime desc,u.nick asc,i.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getObjectSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } },
				"u.userid=i.userid and u.userid=? and u.nick=? "
						+ "and u.gender=? and i.birthday>?",
				"u.createtime desc,u.nick asc,i.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL3() {
		String select_sql = "select u.userid,u.nick,u.gender from user u order by createtime desc,nick asc";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } }, null,
				"createtime desc,nick asc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL4() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum " + "from user u,info i "
				+ "order by u.createtime desc,u.nick asc,i.birthday desc";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getObjectSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } }, null,
				"u.createtime desc,u.nick asc,i.birthday desc");
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL5() {
		String select_sql = "select u.userid,u.nick,u.gender from user u";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } }, null,
				null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL6() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum " + "from user u,info i";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getObjectSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } }, null, null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL7() {
		String select_sql = "select u.userid,u.nick,u.gender from user u where userid=? and nick=? and gender=?";
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setDatabaseName("ds");
		partitionTableInfo.setTableName("user");
		partitionTableInfo.setAliasName("u");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getListSQL(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "u.userid", "u.nick", "u.gender" } },
				"userid=? and nick=? and gender=?", null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testObjectSQL8() {
		String select_sql = "select u.userid,u.nick,u.gender,u.createtime,"
				+ "i.userid,i.birthday,i.fansnum "
				+ "from user u,info i where u.userid=i.userid and u.userid=? and u.nick=? "
				+ "and u.gender=? and i.birthday>?";
		PartitionTableInfo partitionTableInfo1 = new PartitionTableInfo();
		partitionTableInfo1.setDatabaseName("ds");
		partitionTableInfo1.setTableName("user");
		partitionTableInfo1.setAliasName("u");
		PartitionTableInfo partitionTableInfo2 = new PartitionTableInfo();
		partitionTableInfo2.setDatabaseName("ds");
		partitionTableInfo2.setTableName("info");
		partitionTableInfo2.setAliasName("i");
		HkQuery hkQuery = new HkQuery();
		String res_select_sql = hkQuery.getObjectSQL(new PartitionTableInfo[] {
				partitionTableInfo1, partitionTableInfo2 }, new String[][] {
				{ "u.userid", "u.nick", "u.gender", "u.createtime" },
				{ "i.userid", "i.birthday", "i.fansnum" } },
				"u.userid=i.userid and u.userid=? and u.nick=? "
						+ "and u.gender=? and i.birthday>?", null);
		Assert.assertEquals(select_sql, res_select_sql);
	}

	@Test
	public void testInsert() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
	}

	@Test
	public void testUpdate() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
		int res = this.hkQuery.update(partitionTableInfo, new String[] {
				"nick", "gender", "money", "purchase", "createtime" },
				"userid=?", new Object[] { "原味原味原味", "0", 12.5, 16.7,
						new Date(), 1 });
		Assert.assertEquals(1, res);
	}

	@Test
	public void testDelete() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
		int res = this.hkQuery.delete(partitionTableInfo, "userid=?",
				new Object[] { 1 });
		Assert.assertEquals(1, res);
	}

	@Test
	public void testSelectList() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName("a");
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
		List<TestUser> list = this.hkQuery.queryList(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "userid", "nick", "gender", "money",
						"purchase", "createtime" } }, null, null, null, 0, 10,
				new RowMapper<TestUser>() {

					@Override
					public TestUser mapRow(ResultSet rs, int arg1)
							throws SQLException {
						TestUser o = new TestUser();
						o.setUserid(rs.getLong("a.userid"));
						o.setNick(rs.getString("a.nick"));
						o.setCreatetime(rs.getTimestamp("a.createtime"));
						o.setGender(rs.getByte("a.gender"));
						o.setMoney(rs.getDouble("a.money"));
						o.setPurchase(rs.getFloat("a.purchase"));
						return o;
					}
				});
		Assert.assertEquals(1, list.size());
		System.out.println(list.get(0).getUserid());
	}

	@Test
	public void testSelectObject() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName("a");
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
		TestUser testUser = this.hkQuery.queryObject(
				new PartitionTableInfo[] { partitionTableInfo },
				new String[][] { { "userid", "nick", "gender", "money",
						"purchase", "createtime" } }, "userid=?",
				new Object[] { 1 }, null, new RowMapper<TestUser>() {

					@Override
					public TestUser mapRow(ResultSet rs, int arg1)
							throws SQLException {
						TestUser o = new TestUser();
						o.setUserid(rs.getLong("a.userid"));
						o.setNick(rs.getString("a.nick"));
						o.setCreatetime(rs.getTimestamp("a.createtime"));
						o.setGender(rs.getByte("a.gender"));
						o.setMoney(rs.getDouble("a.money"));
						o.setPurchase(rs.getFloat("a.purchase"));
						return o;
					}
				});
		Assert.assertNotNull(testUser);
		System.out.println(testUser.getNick());
	}

	@Test
	public void testSelectCount() {
		PartitionTableInfo partitionTableInfo = new PartitionTableInfo();
		partitionTableInfo.setAliasName("a");
		partitionTableInfo.setTableName("testuser0");
		this.hkQuery.insert(partitionTableInfo, new String[] { "userid",
				"nick", "gender", "money", "purchase", "createtime" },
				new Object[] { 1, "原味", "0", 12.5, 16.7, new Date() });
		int count = this.hkQuery.count(
				new PartitionTableInfo[] { partitionTableInfo }, null, null);
		Assert.assertEquals(1, count);
	}
}