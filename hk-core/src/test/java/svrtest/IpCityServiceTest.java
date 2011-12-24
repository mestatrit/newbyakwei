package svrtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import com.hk.svr.IpCityService;

public class IpCityServiceTest extends HkServiceTest {
	private IpCityService ipCityService;

	public void setIpCityService(IpCityService ipCityService) {
		this.ipCityService = ipCityService;
	}

	public void ttestCreateIpCity() throws Exception {
		String t = null;
		int i = 1;
		try {
			File file = new File("d:/test/ipcity.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			while ((t = reader.readLine()) != null) {
				String[] tt = t.trim().split(";");
				this.ipCityService.createIpCity(tt[0], tt[1], tt[2]);
				i++;
			}
		}
		catch (Exception e) {
			System.out.println(t + "=====" + i);
			throw e;
		}
	}
}