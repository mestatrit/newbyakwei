package svr;

import halo.util.P;
import iwant.bean.Province;
import iwant.svr.ZoneSvr;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

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
	}
}
