package unittest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hk.frame.dao.query2.HkObjQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
@Transactional
public class HkObjQueryTest {

	@Resource
	private HkObjQuery hkObjQuery;

	public HkObjQuery getHkObjQuery() {
		return hkObjQuery;
	}

	@Test
	public void testInsert() {
		TestUser testUser = new TestUser();
		testUser.setUserid(2);
		testUser.setNick("原味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		TestUser testUser2 = this.hkObjQuery.queryObjectExById(ctxMap,
				TestUser.class, 2);
		Assert.assertEquals(testUser.getUserid(), testUser2.getUserid());
	}

	@Test
	public void testUpdate() {
		TestUser testUser = new TestUser();
		testUser.setUserid(10);
		testUser.setNick("原味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		TestUser testUser2 = this.hkObjQuery.queryObjectExById(ctxMap,
				TestUser.class, testUser.getUserid());
		testUser2.setNick("akweiwei");
		this.hkObjQuery.updateObj(ctxMap, testUser2);
		TestUser testUser3 = this.hkObjQuery.queryObjectExById(ctxMap,
				TestUser.class, testUser.getUserid());
		Assert.assertEquals(testUser2.getNick(), testUser3.getNick());
		Assert.assertNotSame(testUser.getNick(), testUser3.getNick());
	}

	@Test
	public void testDelete() {
		TestUser testUser = new TestUser();
		testUser.setUserid(2);
		testUser.setNick("原味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		TestUser testUser2 = this.hkObjQuery.queryObjectExById(ctxMap,
				TestUser.class, 2);
		Assert.assertEquals(testUser.getUserid(), testUser2.getUserid());
		this.hkObjQuery.deleteObj(ctxMap, testUser);
		testUser2 = this.hkObjQuery
				.queryObjectExById(ctxMap, TestUser.class, 2);
		Assert.assertNull(testUser2);
	}

	@Test
	public void testCount() {
		TestUser testUser = new TestUser();
		testUser.setUserid(2);
		testUser.setNick("原味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		int count = this.hkObjQuery.countEx(ctxMap, TestUser.class, "userid=?",
				new Object[] { testUser.getUserid() });
		Assert.assertEquals(1, count);
	}

	@Test
	public void testSelect() {
		TestUser testUser = new TestUser();
		testUser.setUserid(5);
		testUser.setNick("原-=-=味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		List<TestUser> list = this.hkObjQuery.queryListEx(ctxMap,
				TestUser.class, "nick=?", new Object[] { "aa" }, null, 0, -1);
		Assert.assertEquals(0, list.size());
		list = this.hkObjQuery.queryListEx(ctxMap, TestUser.class, "nick=?",
				new Object[] { "原-=-=味" }, null, 0, -1);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testSelect2() {
		TestUser testUser = new TestUser();
		testUser.setUserid(5);
		testUser.setNick("原-=-=味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		Member member = new Member();
		member.setUserid(testUser.getUserid());
		member.setNick("akweiwei");
		member.setGroupid(9);
		this.hkObjQuery.insertObj(ctxMap, member);
		final RowMapper<Member> memberMapper = this.hkObjQuery
				.getRowMapper(Member.class);
		final RowMapper<TestUser> testUserMapper = this.hkObjQuery
				.getRowMapper(TestUser.class);
		List<Member> list = this.hkObjQuery.queryListEx(ctxMap, new Class[] {
				TestUser.class, Member.class },
				"testuser.userid=member.userid", null, null, 0, -1,
				new RowMapper<Member>() {

					@Override
					public Member mapRow(ResultSet rs, int arg1)
							throws SQLException {
						Member member = memberMapper.mapRow(rs, arg1);
						TestUser testUser = testUserMapper.mapRow(rs, arg1);
						member.setTestUser(testUser);
						return member;
					}
				});
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testDelete2() {
		TestUser testUser = new TestUser();
		testUser.setUserid(10);
		testUser.setNick("原-=-=味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		TestUser o = this.hkObjQuery.queryObjectExById(ctxMap, TestUser.class,
				testUser.getUserid());
		Assert.assertNotNull(o);
		this.hkObjQuery.delete(ctxMap, TestUser.class, "nick=?",
				new Object[] { testUser.getNick() });
		o = this.hkObjQuery.queryObjectExById(ctxMap, TestUser.class, testUser
				.getUserid());
		Assert.assertNull(o);
	}

	@Test
	public void testUpdate2() {
		TestUser testUser = new TestUser();
		testUser.setUserid(10);
		testUser.setNick("原-=-=味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		this.hkObjQuery.insertObj(ctxMap, testUser);
		TestUser o = this.hkObjQuery.queryObjectExById(ctxMap, TestUser.class,
				testUser.getUserid());
		Assert.assertNotNull(o);
		int res = this.hkObjQuery.update(ctxMap, TestUser.class, new String[] {
				"nick", "gender" }, "userid=?", new Object[] { "akweiwei", "0",
				"11" });
		Assert.assertNotSame(1, res);
		res = this.hkObjQuery.update(ctxMap, TestUser.class, new String[] {
				"nick", "gender" }, "userid=?", new Object[] { "akweiwei", "0",
				"10" });
		Assert.assertEquals(1, res);
		o = this.hkObjQuery.queryObjectExById(ctxMap, TestUser.class, testUser
				.getUserid());
		Assert.assertNotNull(o);
		Assert.assertNotSame(testUser.getNick(), o.getNick());
		Assert.assertNotSame(testUser.getGender(), o.getGender());
	}
}