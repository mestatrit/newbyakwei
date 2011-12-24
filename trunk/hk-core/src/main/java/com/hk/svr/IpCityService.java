package com.hk.svr;

import java.util.List;
import com.hk.bean.IpCity;
import com.hk.bean.IpCityRange;

public interface IpCityService {
	void createIpCity(String beginIp, String endIp, String name);

	List<IpCity> getIpCityList();

	IpCityRange getIpCityRange(String ip);

	/**
	 * 过滤掉127.0.0.1
	 * 
	 * @param ip
	 * @return
	 */
	IpCity getIpCityByIp(String ip);

	IpCity getIpCityByNameLike(String name);
}