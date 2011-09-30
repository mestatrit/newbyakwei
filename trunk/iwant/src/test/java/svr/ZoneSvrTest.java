package svr;

import halo.util.P;
import iwant.bean.Province;
import iwant.svr.ZoneSvr;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ZoneSvrTest extends BaseSvrTest {

	@Resource
	private ZoneSvr zoneSvr;

	private int countryid = 1;

	@Test
	public void getProvinceListByCountryid() {
		List<Province> list = this.zoneSvr
				.getProvinceListByCountryid(countryid);
		for (Province o : list) {
			P.println(o.getName());
		}
	}

	@Test
	public void testUpdate() {
	}

	public static void main(String[] args) {
		ApplicationContext factory = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		final ZoneSvr zoneSvr = (ZoneSvr) factory.getBean("zoneSvr");
		Runnable runnable0 = new Runnable() {

			@Override
			public void run() {
				zoneSvr.testUpdateCityTx(null);
				P.println("end update 0");
			}
		};
		Runnable runnable1 = new Runnable() {

			@Override
			public void run() {
				zoneSvr.testUpdateCityTx(null);
				P.println("end update 1");
			}
		};
		new Thread(runnable0).start();
		P.println("start 1");
		new Thread(runnable1).start();
		P.println("start 2");
	}
}
