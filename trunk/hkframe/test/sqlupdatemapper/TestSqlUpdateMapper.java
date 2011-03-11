package sqlupdatemapper;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.TestUser;

import com.hk.frame.dao.query2.ObjectSqlInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
public class TestSqlUpdateMapper {

	@Test
	public void testCreateObjectInfo() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		TestUser testUser = new TestUser();
		testUser.setUserid(1);
		testUser.setNick("akwei");
		testUser.setGender((byte) 2);
		testUser.setMoney(25);
		testUser.setPurchase(30f);
		testUser.setCreatetime(date);
		testUser.setPurchase1('a');
		ObjectSqlInfo<TestUser> objectSqlInfo = new ObjectSqlInfo<TestUser>(
				TestUser.class);
		int want_fieldListSize = 6;
		int want_allFieldListSize = 7;
		Assert.assertEquals(want_fieldListSize, objectSqlInfo.getFieldList()
				.size());
		Assert.assertEquals(want_allFieldListSize, objectSqlInfo
				.getAllfieldList().size());
		// id assert
		Assert.assertEquals(new Long(testUser.getUserid()), objectSqlInfo
				.getSqlUpdateMapper().getIdParam(testUser));
		// objects for insert
		Object[] objs = objectSqlInfo.getSqlUpdateMapper().getParamsForInsert(
				testUser);
		for (Object o : objs) {
			System.out.print(o.toString() + " | ");
		}
		System.out.println("\n");
		// objects for update
		objs = objectSqlInfo.getSqlUpdateMapper().getParamsForUpdate(testUser);
		for (Object o : objs) {
			System.out.print(o.toString() + " | ");
		}
	}
}
