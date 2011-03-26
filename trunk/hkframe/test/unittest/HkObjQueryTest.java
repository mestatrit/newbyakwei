package unittest;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
}