package com.hk.svr.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.IpCity;
import com.hk.bean.IpCityRange;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.IpCityService;

public class IpCityServiceImpl implements IpCityService {
	@Autowired
	private QueryManager queryManager;

	public void createIpCity(String beginIp, String endIp, String name) {
		Query query = this.queryManager.createQuery();
		query.where("name=?").setParam(name);
		IpCity ipCity = query.getObject(IpCity.class);
		int cityId = 0;
		if (ipCity == null) {
			query.setTable(IpCity.class);
			query.addField("name", name);
			cityId = query.insert(IpCity.class).intValue();
		}
		else {
			cityId = ipCity.getCityId();
		}
		this.createIpCityRange(cityId, beginIp, endIp);
	}

	private void createIpCityRange(int cityId, String beginIp, String endIp) {
		long beginIpNum = Long.parseLong(beginIp.replaceAll("\\.", ""));
		long endIpNum = Long.parseLong(endIp.replaceAll("\\.", ""));
		Query query = this.queryManager.createQuery();
		query.addField("cityid", cityId);
		query.addField("beginip", beginIpNum);
		query.addField("endip", endIpNum);
		query.insert(IpCityRange.class);
	}

	public List<IpCity> getIpCityList() {
		Query query = this.queryManager.createQuery();
		return query.listEx(IpCity.class);
	}

	public IpCityRange getIpCityRange(String ip) {
		long ipNum = DataUtil.parseIpNumber(ip);
		Query query = this.queryManager.createQuery();
		query.setTable(IpCityRange.class);
		query.where("beginip<=? and endip>=?").setParam(ipNum).setParam(ipNum);
		return query.getObject(IpCityRange.class);
	}

	public IpCity getIpCityByIp(String ip) {
		IpCityRange range = this.getIpCityRange(ip);
		if (range == null) {
			return null;
		}
		if (range.getCityId() == 710) {
			return null;
		}
		Query query = this.queryManager.createQuery();
		return query.getObjectById(IpCity.class, range.getCityId());
	}

	public IpCity getIpCityByNameLike(String name) {
		Query query = this.queryManager.createQuery();
		return query.getObjectEx(IpCity.class, "name like ?",
				new Object[] { "%" + name + "%" });
	}
}