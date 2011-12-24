package com.etbhk.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.City;
import com.hk.bean.Province;
import com.hk.frame.util.HkUtil;
import com.hk.svr.ZoneService;

public class TbHkViewUtil {

	public static void loadZoneInfo(HttpServletRequest request) {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		List<City> citylist = zoneService.getCityList(1, 0, null);
		List<Province> provincelist = zoneService.getProvinceList(1);
		request.setAttribute("citylist", citylist);
		request.setAttribute("provincelist", provincelist);
	}
}