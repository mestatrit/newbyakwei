package gettingstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import bean.Member;
import bean.TestUser;
import bean.UserVo;

import com.dev3g.cactus.dao.query.HkObjQuery;
import com.dev3g.cactus.dao.query.param.DeleteParam;
import com.dev3g.cactus.dao.query.param.InsertParam;
import com.dev3g.cactus.dao.query.param.QueryParam;
import com.dev3g.cactus.dao.query.param.UpdateParam;
import com.dev3g.cactus.util.DateUtil;
import com.dev3g.cactus.util.HkUtil;
import com.dev3g.cactus.util.P;

/**
 * 配置spring文件，看guide.xml<br/>
 * 以对象的方式来处理sql，此类中的所有例子对于类中的属性值会有所限制<br/>
 * 只允许short,byte,int,long,float,double,java .lang.String,java.util.Date这些数据类型<br/>
 * 查询不支持group by have in not in等复杂操作，不支持数据库函数。如果需要，请自行扩展
 * 
 * @author akwei
 */
public class ObjGuide {

	/**
	 * 创建对象
	 */
	public void insert() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		// 创建数据对象
		TestUser testUser = new TestUser();
		testUser.setUserid(1);
		testUser.setNick("akweiwei");
		testUser.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		testUser.setGender((byte) 1);
		testUser.setMoney(29.9);
		testUser.setPurchase(21.1f);
		// 创建参数对象
		InsertParam insertParam = new InsertParam();
		// 设置分区参数以及值
		insertParam.addKeyAndValue("userid", testUser.getUserid());
		// 创建操作
		hkObjQuery.insertObj(insertParam, testUser);
	}

	/**
	 * 修改对象
	 */
	public void updateObj() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		// 创建数据对象
		TestUser testUser = new TestUser();
		testUser.setUserid(1);
		testUser.setNick("akweiwei");
		testUser.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		testUser.setGender((byte) 1);
		testUser.setMoney(29.9);
		testUser.setPurchase(21.1f);
		// 创建参数对象
		UpdateParam updateParam = new UpdateParam();
		// 设置分区参数以及值
		updateParam.addKeyAndValue("userid", testUser.getUserid());
		// update
		hkObjQuery.updateObj(updateParam, testUser);
	}

	/**
	 * 修改
	 */
	public void update() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		UpdateParam updateParam = new UpdateParam();
		// 设置分区参数以及值
		updateParam.addKeyAndValue("userid", 1);
		// 设置需要修改的列
		updateParam.setUpdateColumns(new String[] { "gender", "createtime",
				"nick" });
		// 设置sql where
		updateParam.setWhere("nick=?");
		// 设置修改列与where条件的对应的参数
		updateParam
				.setParams(new Object[] { 1, new Date(), "akweiwei", "aaa" });
		// update
		hkObjQuery.update(updateParam, TestUser.class);
	}

	/**
	 * 根据id查询单对象
	 */
	public void selectObj() {
		long userid = 5;
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", userid);
		// select
		TestUser testUser = hkObjQuery.getObjectById(queryParam,
				TestUser.class, userid);
		P.println(testUser);
	}

	/**
	 * 单表查询
	 */
	public void selectList() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 添加需要查询的表,如果查询的表与返回值类型相同，可不设置此参数
		queryParam.addClass(TestUser.class);
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", new Long(4));
		// 设置查询范围如果size为<0时，取所有数据
		queryParam.setRange(0, 10);
		queryParam.setWhereAndParams("testuser.nick=?",
				new Object[] { "akwei" });
		queryParam.setOrder("gender desc");
		// select
		List<TestUser> list = hkObjQuery.getList(queryParam, TestUser.class);
		for (TestUser o : list) {
			P.println(o);
		}
	}

	/**
	 * 多表查询，返回所有列
	 */
	public void selectList2() {
		final HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 添加需要查询的表，此添加顺序会影响返回列的顺序
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		// 设置查询范围
		queryParam.setBegin(0);
		queryParam.setSize(-1);
		queryParam
				.setWhere("testuser.userid=member.memberuserid and testuser.userid=?");
		queryParam.setParams(new Object[] { 4 });
		queryParam.setOrder("testuser.nick asc");
		// 返回值的rowmapper
		RowMapper<Member> mapper = new RowMapper<Member>() {

			@Override
			public Member mapRow(ResultSet arg0, int arg1) throws SQLException {
				// 返回所有列时，可以使用表对应的类
				TestUser testUser = hkObjQuery.getRowMapper(TestUser.class)
						.mapRow(arg0, arg1);
				Member member = hkObjQuery.getRowMapper(Member.class).mapRow(
						arg0, arg1);
				member.setTestUser(testUser);
				return member;
			}
		};
		// select
		List<Member> list = hkObjQuery.getList(queryParam, mapper);
		for (Member o : list) {
			P.println(o);
		}
	}

	/**
	 * 多表查询返回选定列
	 */
	public void selectList2WithColumns() {
		final HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 添加需要查询的表，此添加顺序会影响返回列的顺序
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		// 设置需要返回的列，每组列必须与addClass的类顺序一致
		queryParam.setColumns(new String[][] { { "usetid", "nick", "gender" },
				{ "memberid", "membername" } });
		queryParam.setRange(0, -1);
		queryParam.setWhereAndParams(
				"testuser.userid=member.memberuserid and testuser.userid=?",
				new Object[] { 4 });
		queryParam.setOrder("testuser.nick asc");
		// select
		List<UserVo> list = hkObjQuery.getList(queryParam, UserVo.class);
		for (UserVo o : list) {
			P.println(o);
		}
	}

	/**
	 * 单表统计
	 */
	public void count() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", new Long(2));
		// 设置要统计的类
		queryParam.addClass(TestUser.class);
		// 设置查询条件与参数
		queryParam.setWhereAndParams("nick=?", new Object[] { "akweiwei" });
		// count
		hkObjQuery.count(queryParam);
	}

	/**
	 * 多表统计
	 */
	public void count2() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		QueryParam queryParam = new QueryParam();
		// 设置要统计的类
		queryParam.addClass(TestUser.class);
		queryParam.addClass(Member.class);
		// 设置分区参数以及值
		queryParam.addKeyAndValue("userid", new Long(4));
		queryParam.addKeyAndValue("memberuserid", new Long(4));
		// 设置查询条件与参数
		queryParam.setWhereAndParams(
				"testuser.userid=member.memberuserid and testuser.userid=?",
				new Object[] { 4 });
		// count
		hkObjQuery.count(queryParam);
	}

	/**
	 * 删除
	 */
	public void delete() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		DeleteParam deleteParam = new DeleteParam();
		// 设置分区参数以及值
		deleteParam.addKeyAndValue("userid", new Long(10));
		// 设置查询条件与参数
		deleteParam.setWhereAndParams("nick=?", new Object[] { "akwei" });
		// delete
		hkObjQuery.delete(deleteParam, TestUser.class);
	}

	/**
	 * 删除对象
	 */
	public void deleteObj() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		TestUser testUser = null;
		// 从数据库获得的数据
		DeleteParam deleteParam = new DeleteParam();
		// 设置分区参数以及值
		deleteParam.addKeyAndValue("userid", new Long(100));
		// delete
		hkObjQuery.deleteObj(deleteParam, testUser);
	}

	/**
	 * 根据id删除对象
	 */
	public void deleteObjById() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		DeleteParam deleteParam = new DeleteParam();
		// 设置分区参数以及值
		deleteParam.addKeyAndValue("userid", new Long(100));
		// delete
		hkObjQuery.deleteById(deleteParam, TestUser.class, 100);
	}
}
