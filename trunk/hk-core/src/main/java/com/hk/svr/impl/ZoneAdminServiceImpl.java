package com.hk.svr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.Pcity;
import com.hk.bean.ZoneAdmin;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.ZoneAdminService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.ZoneUtil;

public class ZoneAdminServiceImpl implements ZoneAdminService {
	@Autowired
	private QueryManager manager;

	public boolean createZoneAdmin(ZoneAdmin zoneAdmin) {
		Query query = this.manager.createQuery();
		if (query.count(ZoneAdmin.class, "userid=? and pcityid=?",
				new Object[] { zoneAdmin.getUserId(), zoneAdmin.getPcityId() }) > 0) {
			return false;
		}
		zoneAdmin.setOid(query.insertObject(zoneAdmin).longValue());
		return true;
	}

	public void deleteZoneAdmin(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(ZoneAdmin.class, oid);
	}

	public List<ZoneAdmin> getZoneAdminList(int pcityId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(ZoneAdmin.class, "pcityid=?",
					new Object[] { pcityId }, "oid desc", begin, size);
		}
		return query.listEx(ZoneAdmin.class, "oid desc", begin, size);
	}

	public ZoneAdmin getZoneAdmin(long userId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(ZoneAdmin.class, "userid=?",
				new Object[] { userId });
	}

	public void updatePcityIdData() {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		Query query = this.manager.createQuery();
		List<ZoneAdmin> list = query.listEx(ZoneAdmin.class);
		for (ZoneAdmin o : list) {
			Pcity pcity = ZoneUtil.getPcityOld(o.getPcityId());
			if (pcity != null) {
				String name = DataUtil.filterZoneName(pcity.getNameOld());
				City city = zoneService.getCityLike(name);
				if (city != null) {
					o.setPcityId(city.getCityId());
					query.updateObject(o);
				}
			}
		}
	}
}