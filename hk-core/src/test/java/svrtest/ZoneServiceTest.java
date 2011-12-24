package svrtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.hk.svr.ZoneService;

public class ZoneServiceTest extends HkServiceTest {
	private ZoneService zoneService;

	public void setZoneService(ZoneService zoneService) {
		this.zoneService = zoneService;
	}

	public void testupdatepcityiddata() {
		this.zoneService.updateCityData();
		this.commit();
	}

	// public void testGetCityListByCity() {
	// this.zoneService.getCityList("青岛");
	// }
	//
	// public void testGetCountryList() {
	// this.zoneService.getCountryList();
	// }
	//
	// public void testGetProvinceList() {
	// this.zoneService.getProvinceList();
	// }
	public void ttestImportCityCode() throws IOException {
		File file = new File("d:/qh.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String s = null;
		while ((s = reader.readLine()) != null) {
			String[] t = s.trim().split(",");
			this.zoneService.tmpcreateCityCode(t[0], t[1]);
		}
	}
}