package svr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/test-app-ds.xml", "/app-dao.xml", "/app-svr.xml" })
@Transactional
public class BaseSvrTest {

	@Test
	public void testBegin() {
	}
}
