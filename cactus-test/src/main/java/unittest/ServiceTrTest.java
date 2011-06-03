package unittest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dev3g.cactus.transaction.ServiceTr;
import com.dev3g.cactus.transaction.TranscationMaker;
import com.dev3g.cactus.util.P;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test.xml" })
@Transactional
public class ServiceTrTest {

	@Resource
	ServiceTr serviceTr;

	@Test
	public void test() {
		TranscationMaker maker = new TranscationMaker() {

			@Override
			public Object proccess(Object... objects) {
				int a = (Integer) objects[0];
				String name = (String) objects[1];
				P.println("hello test " + a + " , " + name);
				return null;
			}
		};
		this.serviceTr.execute(maker, 1, "akwei");
	}
}