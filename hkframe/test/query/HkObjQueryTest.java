package query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import bean.TestUser;

import com.hk.frame.dao.query2.HkObjQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
@Transactional
public class HkObjQueryTest {

	@Resource
	private HkObjQuery hkObjQuery;

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
	public void testSelectObj() {
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
		o = this.hkObjQuery.queryObjectEx(ctxMap, TestUser.class, "gender=?",
				new Object[] { 3 }, null);
		Assert.assertNull(o);
		o = this.hkObjQuery.queryObjectEx(ctxMap, TestUser.class, "gender=?",
				new Object[] { 1 }, null);
		Assert.assertNotNull(o);
	}
}