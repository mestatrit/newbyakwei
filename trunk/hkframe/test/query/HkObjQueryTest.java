package query;

import java.util.Date;

import javax.annotation.Resource;

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

	public void testInsertObj() {
		TestUser testUser = new TestUser();
		testUser.setUserid(2);
		testUser.setNick("原味");
		testUser.setCreatetime(new Date());
		testUser.setGender((byte) 1);
		testUser.setMoney(45.5);
		testUser.setPurchase(20.5f);
	}
}