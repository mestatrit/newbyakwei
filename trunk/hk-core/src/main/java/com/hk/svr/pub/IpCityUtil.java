package com.hk.svr.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hk.bean.IpCity;
import com.hk.frame.util.HkUtil;
import com.hk.svr.IpCityService;

public class IpCityUtil {
	private static final Map<Integer, IpCity> ipCityMap = new HashMap<Integer, IpCity>();

	private IpCityUtil() {//
	}

	public static void init() {
		IpCityService ipCityService = (IpCityService) HkUtil
				.getBean("ipCityService");
		List<IpCity> list = ipCityService.getIpCityList();
		for (IpCity city : list) {
			ipCityMap.put(city.getCityId(), city);
		}
	}
}