package com.hk.svr.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.bean.City;
import com.hk.bean.Country;
import com.hk.bean.Pcity;
import com.hk.bean.Province;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.ZoneService;

public class ZoneUtil {

	public static Integer[] oin = { 1, 2, 3, 4, 34, 35 };

	private static final Map<Integer, City> cityMap = new HashMap<Integer, City>();

	private static final Map<Integer, Country> countryMap = new HashMap<Integer, Country>();

	private static final Map<Integer, Province> provinceMap = new HashMap<Integer, Province>();

	public static final Map<Integer, Pcity> pcityMap = new HashMap<Integer, Pcity>();

	private static List<City> cityList;

	private static List<Country> countryList;

	private static List<Province> provinceList;

	public static List<Pcity> pcityList;

	public static List<Pcity> getPcityList() {
		return pcityList;
	}

	public static Pcity getPcity(int cityId) {
		// return pcityMap.get(cityId);
		City city = cityMap.get(cityId);
		if (city == null) {
			return null;
		}
		Pcity pcity = new Pcity();
		pcity.setCity(city);
		return pcity;
	}

	public static Pcity getPcityOld(int cityId) {
		return pcityMap.get(cityId);
	}

	public static String getZoneName(int cityId) {
		Pcity pcity = getPcity(cityId);
		if (pcity == null) {
			return null;
		}
		return pcity.getName();
	}

	public static City getCity(int cityId) {
		return cityMap.get(cityId);
	}

	public static String getCityName(int cityId) {
		City city = getCity(cityId);
		if (city != null) {
			return city.getCity();
		}
		return null;
	}

	public static List<City> getCityList() {
		return cityList;
	}

	public static Country getCountry(int countryId) {
		return countryMap.get(countryId);
	}

	public static List<Country> getCountryList() {
		return countryList;
	}

	public static Province getProvince(int provinceId) {
		return provinceMap.get(provinceId);
	}

	public static List<Province> getProvinceList() {
		return provinceList;
	}

	public static String getZoneName(int cityId, int provinceId) {
		City city = getCity(cityId);
		if (city != null) {
			return city.getCity().replaceAll("市", "").replaceAll("区", "");
		}
		Province province = getProvince(provinceId);
		if (province != null) {
			return province.getProvince().replaceAll("市", "").replaceAll("区",
					"");
		}
		return null;
	}

	public static void initZoneMap() {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		countryList = zoneService.getCountryList();
		provinceList = zoneService.getProvinceList();
		cityList = zoneService.getCityList();
		pcityList = zoneService.getPcityList();
		for (Country c : countryList) {
			countryMap.put(c.getCountryId(), c);
		}
		for (Province p : provinceList) {
			provinceMap.put(p.getProvinceId(), p);
		}
		for (City c : cityList) {
			cityMap.put(c.getCityId(), c);
		}
		for (Pcity o : pcityList) {
			pcityMap.put(o.getCityIdOld(), o);
		}
	}

	/**
	 * 是否没有下级城市
	 * 
	 * @param provinceId
	 * @return true 有下级 false无下级
	 */
	public static boolean isProvince(int provinceId) {
		if (DataUtil.isInElements(provinceId, oin)) {
			return false;
		}
		return true;
	}

	/**
	 * @param provinceId
	 */
	public static int getPcityId(int cityId, int provinceId) {
		return cityId;
	}
	// public static Pcity getPcity(int cityId, int provinceId) {
	// ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
	// if (isProvince(provinceId)) {
	// City city = ZoneUtil.getCity(cityId);
	// if (city == null) {
	// return null;
	// }
	// return zoneService.getPcityByName(city.getCity());
	// }
	// Province province = getProvince(provinceId);
	// return zoneService.getPcityByName(province.getProvince());
	// }
	//	
	// public static int getPcityId(int cityId, int provinceId) {
	// Pcity pcity = getPcity(cityId, provinceId);
	// if (pcity == null) {
	// return 0;
	// }
	// return pcity.getCityId();
	// }
}