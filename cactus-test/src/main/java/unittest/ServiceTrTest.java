package unittest;

import javax.annotation.Resource;

import org.junit.Test;

import com.dev3g.cactus.transaction.ServiceTr;
import com.dev3g.cactus.transaction.TranscationMaker;

public class ServiceTrTest {

	@Resource
	ServiceTr serviceTr;

	@Test
	public void test() {
		TranscationMaker maker = new TranscationMaker() {

			@Override
			public Object proccess(Object... objects) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		this.serviceTr.execute(maker);
	}
}