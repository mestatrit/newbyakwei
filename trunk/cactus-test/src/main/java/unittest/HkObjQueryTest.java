package unittest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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

import bean.Member;
import bean.TestUser;
import bean.UserVo;
import cactus.dao.query.BaseParam;
import cactus.dao.query.HkObjQuery;
import cactus.dao.query.QueryParam;
import cactus.util.DateUtil;
import cactus.util.P;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
@Transactional
public class HkObjQueryTest {

	@Resource
	private HkObjQuery hkObjQuery;

	public HkObjQuery getHkObjQuery() {
		return hkObjQuery;
	}

	private TestUser createTestUser(long userid) {
		TestUser testUser = new TestUser();
		testUser.setUserid(userid);
		testUser.setNick("akweiwei");
		testUser.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		testUser.setGender((byte) 1);
		testUser.setMoney(29.9);
		testUser.setPurchase(21.1f);
		BaseParam baseParam = this.hkObjQuery.createBaseParam();
		baseParam.addKeyAndValue("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(baseParam, testUser);
		return testUser;
	}

	private Member createMember(long userid) {
		Member member = new Member();
		member.setUserid(userid);
		member.setGroupid(999);
		member.setNick("member akwei");
		BaseParam baseParam = this.hkObjQuery.createBaseParam();
		baseParam.addKeyAndValue("memberuserid", member.getUserid());
		this.hkObjQuery.insertObj(baseParam, member);
		return member;
	}

	private TestUser getTestUser(long userid) {
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		// queryParam.addClass(TestUser.class);
		queryParam.addKeyAndValue("userid", userid);
		return this.hkObjQuery
				.getObjectById(queryParam, TestUser.class, userid);
	}

	@Test
	public void getObject() {
		TestUser testUser = this.getTestUser(2);
		Assert.assertNull(testUser);
		TestUser testUser_insert = this.createTestUser(2);
		P.println(testUser_insert.getCreatetime().getTime());
		testUser = this.getTestUser(2);
		Assert.assertNotNull(testUser);
		Assert.assertNotSame(testUser, testUser_insert);
		Assert.assertEquals(testUser.getUserid(), testUser_insert.getUserid());
		Assert.assertEquals(testUser.getNick(), testUser_insert.getNick());
		Assert.assertEquals(new Double(testUser.getMoney()), new Double(
				testUser_insert.getMoney()));
		Assert.assertEquals(testUser.getGender(), testUser_insert.getGender());
		Assert.assertEquals(new Float(testUser.getPurchase()), new Float(
				testUser_insert.getPurchase()));
		Calendar testuser_cal = Calendar.getInstance();
		testuser_cal.setTime(testUser.getCreatetime());
		Calendar testuser_insert_cal = Calendar.getInstance();
		testuser_insert_cal.setTime(testUser_insert.getCreatetime());
		Assert.assertEquals(testuser_cal.get(Calendar.MILLISECOND),
				testuser_insert_cal.get(Calendar.MILLISECOND));
		Assert.assertEquals(testUser.getCreatetime().getTime(), testUser_insert
				.getCreatetime().getTime());
	}

	@Test
	public void getObject2() {
		this.createTestUser(4);
		QueryParam queryParam = this.hkObjQuery.createQueryParam("userid",
				new Long(4));
		queryParam.setWhereAndParams("userid=?", new Object[] { 4 });
		TestUser testUser = this.hkObjQuery.getObject(queryParam,
				TestUser.class);
		Assert.assertNotNull(testUser);
	}

	@Test
	public void getListOneTableNoWhereNoOrder() {
		this.createTestUser(4);
		this.createTestUser(5);
		this.createTestUser(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.addClass(TestUser.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		List<TestUser> list = this.hkObjQuery.getList(queryParam,
				TestUser.class);
		Assert.assertEquals(2, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, TestUser.class);
		Assert.assertEquals(1, list.size());
		queryParam.setSize(-1);
		queryParam.addKeyAndValue("userid", new Long(5));
		list = this.hkObjQuery.getList(queryParam, TestUser.class);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListOneTableNoWhereHasOrder() {
		this.createTestUser(4);
		this.createTestUser(5);
		this.createTestUser(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.setOrder("testuser.userid desc");
		queryParam.addClass(TestUser.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		List<TestUser> list = this.hkObjQuery.getList(queryParam,
				TestUser.class);
		Assert.assertEquals(2, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, TestUser.class);
		Assert.assertEquals(1, list.size());
		queryParam.setSize(-1);
		queryParam.addKeyAndValue("userid", new Long(5));
		list = this.hkObjQuery.getList(queryParam, TestUser.class);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListOneTableHasWhereHasOrder() {
		this.createTestUser(4);
		this.createTestUser(5);
		this.createTestUser(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.setWhere("userid=?");
		queryParam.setParams(new Object[] { new Long(4) });
		queryParam.setOrder("testuser.userid desc");
		queryParam.addClass(TestUser.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		List<TestUser> list = this.hkObjQuery.getList(queryParam,
				TestUser.class);
		Assert.assertEquals(1, list.size());
		queryParam.setParams(new Object[] { new Long(5) });
		queryParam.addKeyAndValue("userid", new Long(5));
		list = this.hkObjQuery.getList(queryParam, TestUser.class);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListTwoTableNoWhereNoOrder() {
		this.createTestUser(4);
		this.createMember(4);
		this.createTestUser(5);
		this.createMember(5);
		this.createTestUser(6);
		this.createMember(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		queryParam.setWhere("testuser.userid=member.memberuserid");
		RowMapper<Member> mapper = new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet arg0, int arg1) throws SQLException {
				TestUser testUser = hkObjQuery.getRowMapper(TestUser.class)
						.mapRow(arg0, arg1);
				Member member = hkObjQuery.getRowMapper(Member.class).mapRow(
						arg0, arg1);
				member.setTestUser(testUser);
				return member;
			}
		};
		List<Member> list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(2, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListTwoTableNoWhereHasOrder() {
		this.createTestUser(4);
		this.createMember(4);
		this.createTestUser(5);
		this.createMember(5);
		this.createTestUser(6);
		this.createMember(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		queryParam.setWhere("testuser.userid=member.memberuserid");
		queryParam.setOrder("testuser.nick asc");
		RowMapper<Member> mapper = new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet arg0, int arg1) throws SQLException {
				TestUser testUser = hkObjQuery.getRowMapper(TestUser.class)
						.mapRow(arg0, arg1);
				Member member = hkObjQuery.getRowMapper(Member.class).mapRow(
						arg0, arg1);
				member.setTestUser(testUser);
				return member;
			}
		};
		List<Member> list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(2, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListTwoTableHasWhereHasOrder() {
		this.createTestUser(4);
		this.createMember(4);
		this.createTestUser(5);
		this.createMember(5);
		this.createTestUser(6);
		this.createMember(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		queryParam
				.setWhere("testuser.userid=member.memberuserid and testuser.userid=?");
		queryParam.setParams(new Object[] { 4 });
		queryParam.setOrder("testuser.nick asc");
		RowMapper<Member> mapper = new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet arg0, int arg1) throws SQLException {
				TestUser testUser = hkObjQuery.getRowMapper(TestUser.class)
						.mapRow(arg0, arg1);
				Member member = hkObjQuery.getRowMapper(Member.class).mapRow(
						arg0, arg1);
				member.setTestUser(testUser);
				return member;
			}
		};
		List<Member> list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(1, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, mapper);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getListTwoTableResultSetData() {
		this.createTestUser(4);
		this.createMember(4);
		this.createTestUser(5);
		this.createMember(5);
		this.createTestUser(6);
		this.createMember(6);
		QueryParam queryParam = this.hkObjQuery.createQueryParam();
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		queryParam
				.setWhere("testuser.userid=member.memberuserid and testuser.userid=?");
		queryParam.setParams(new Object[] { 4 });
		queryParam.setOrder("testuser.nick asc");
		List<UserVo> list = this.hkObjQuery.getList(queryParam, UserVo.class);
		Assert.assertEquals(1, list.size());
		queryParam.setSize(1);
		list = this.hkObjQuery.getList(queryParam, UserVo.class);
		Assert.assertEquals(1, list.size());
		for (UserVo vo : list) {
			P.println(vo.getUserid() + " | " + vo.getNick());
		}
	}
}