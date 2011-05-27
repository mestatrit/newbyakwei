package gettingstart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import bean.Member;
import bean.TestUser;
import cactus.dao.query.HkObjQuery;
import cactus.dao.query.PartitionTableInfo;
import cactus.util.HkUtil;
import cactus.util.P;

/**
 * 配置spring文件，看guide.xml<br/>
 * 对于此类中的所有操作适用于所有sql的方式
 * 
 * @author akwei
 */
public class Guide {

	public void insert() {
		// 通过spring获取，获取方式有很多，可以注入等
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建insert sql
		String insert_sql = "insert into " + partitionTableInfo.getTableName()
				+ "(id,nick) values(?,?)";
		// 设置参数
		Object[] values = new Object[] { 1, "akwei" };
		// 使用sql操作
		hkObjQuery.insertBySQL(partitionTableInfo, insert_sql, values);
	}

	public void update() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建update sql
		String update_sql = "update " + partitionTableInfo.getTableName()
				+ " set nick=?,gender=?,birthday=?";
		// 设置参数
		Object[] values = new Object[] { "akwei", 0, new Date(), 1 };
		// 使用sql update 操作
		hkObjQuery.updateBySQL(partitionTableInfo, update_sql, values);
	}

	public void delete() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建update sql
		String update_sql = "delete from " + partitionTableInfo.getTableName()
				+ " where userid=?";
		// 设置参数
		Object[] values = new Object[] { 1 };
		// 使用sql update 操作
		hkObjQuery.updateBySQL(partitionTableInfo, update_sql, values);
	}

	public void selectList() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建select_sql
		String select_sql = "select * from "
				+ partitionTableInfo.getTableName() + " where nick=? limit ?,?";
		Object[] values = new Object[] { "akwei", 0, 10 };
		List<TestUser> list = hkObjQuery.getListBySQL(
				new PartitionTableInfo[] { partitionTableInfo }, select_sql,
				values, 0, -1, new RowMapper<TestUser>() {

					@Override
					public TestUser mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						// TODO Auto-generated method stub 组装结果集
						return null;
					}
				});
		for (TestUser o : list) {
			P.println(o);
		}
	}

	public void selectList2() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		ctxMap.put("member.memberuserid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo[] partitionTableInfos = hkObjQuery.parse(
				new Class<?>[] { TestUser.class, Member.class }, ctxMap);
		// 创建select_sql
		String select_sql = "select * from "
				+ partitionTableInfos[0].getTableName()
				+ " , "
				+ partitionTableInfos[1].getTableName()
				+ " where nick=? and testuser.userid=member.memberuserid limit ?,?";
		Object[] values = new Object[] { "akwei", 1, 10 };
		List<TestUser> list = hkObjQuery.getListBySQL(partitionTableInfos,
				select_sql, values, 0, -1, new RowMapper<TestUser>() {

					@Override
					public TestUser mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						// TODO Auto-generated method stub 组装结果集
						return null;
					}
				});
		for (TestUser o : list) {
			P.println(o);
		}
	}

	public void count() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建select_sql
		String select_sql = "select count(*) from "
				+ partitionTableInfo.getTableName() + " where nick=?";
		Object[] values = new Object[] { "akwei" };
		int count = hkObjQuery.getNumberBySQL(
				new PartitionTableInfo[] { partitionTableInfo }, select_sql,
				values).intValue();
		P.println(count);
	}

	public void selectObject() {
		HkObjQuery hkObjQuery = (HkObjQuery) HkUtil.getBean("hkObjQuery");
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		// 设置分区关键值
		ctxMap.put("testuser.userid", 1);
		// 通过分区分析器分析，返回分区信息
		PartitionTableInfo partitionTableInfo = hkObjQuery.parse(
				TestUser.class, ctxMap);
		// 创建select_sql
		String select_sql = "select * from "
				+ partitionTableInfo.getTableName() + " where nick=? limit ?,?";
		Object[] values = new Object[] { "akwei", 0, 1 };
		hkObjQuery.getObjectBySQL(
				new PartitionTableInfo[] { partitionTableInfo }, select_sql,
				values, new RowMapper<TestUser>() {

					@Override
					public TestUser mapRow(ResultSet arg0, int arg1)
							throws SQLException {
						// TODO Auto-generated method stub 组装结果集
						return null;
					}
				});
	}
}