package com.hk.web.pub.action;

import com.hk.bean.City;
import com.hk.bean.IpCity;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.IpCityService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;

public class IpZoneInfo {
	private int cityId;

	private int provinceId;

	private int ipCityId;

	public IpZoneInfo(int cityId) {
		this.cityId = cityId;
		City city = ZoneUtil.getCity(cityId);
		String key = null;
		if (city != null) {
			key = DataUtil.filterZoneName(city.getCity());
		}
		if (!DataUtil.isEmpty(key)) {
			IpCityService ipCityService = (IpCityService) HkUtil
					.getBean("ipCityService");
			IpCity ipCity = ipCityService.getIpCityByNameLike(key);
			if (ipCity != null) {
				this.ipCityId = ipCity.getCityId();
			}
		}
	}

	public IpZoneInfo(String ip) {
		IpCityService ipCityService = (IpCityService) HkUtil
				.getBean("ipCityService");
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		IpCity c = ipCityService.getIpCityByIp(ip);
		if (c != null) {
			this.ipCityId = c.getCityId();
			String name = c.getName();
			if (name.length() > 2) {
				name = name.substring(0, 2);
			}
			City city = zoneService.getCityLike(name);
			if (city != null) {
				cityId = city.getCityId();
			}
		}
	}

	public boolean isHasInfo() {
		if (this.cityId == 0 && this.provinceId == 0) {
			return false;
		}
		return true;
	}

	public int getIpCityId() {
		return ipCityId;
	}

	public int getCityId() {
		return cityId;
	}

	public int getProvinceId() {
		return provinceId;
	}
}