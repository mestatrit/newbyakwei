package query;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.TestUser;

import com.hk.frame.dao.query2.ObjectSqlInfo;
import com.hk.frame.dao.query2.PartitionTableInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
public class DbPartitionHelperTest {

	/**
	 * 测试描述，检测分表分库的准确性,testuser:testuser0,1;db:test0,1<br/>
	 * 按照userid进行分区
	 */
	@Test
	public void testDbParitionHelper() {
		TestUserDbPartitionHelper dbPartitionHelper = new TestUserDbPartitionHelper();
		TestUser testUser = new TestUser();
		testUser.setUserid(2);
		ObjectSqlInfo<TestUser> objectSqlInfo = new ObjectSqlInfo<TestUser>(
				TestUser.class);
		Map<String, Object> ctxMap = new HashMap<String, Object>();
		ctxMap.put("userid", testUser.getUserid());
		PartitionTableInfo partitionTableInfo = dbPartitionHelper.parse(
				objectSqlInfo.getTableName(), ctxMap);
		Assert.assertEquals("testuser0", partitionTableInfo.getTableName());
		Assert.assertEquals("ds_test0", partitionTableInfo.getDatabaseName());
		Assert.assertEquals("testuser", objectSqlInfo.getTableName());
	}
}
