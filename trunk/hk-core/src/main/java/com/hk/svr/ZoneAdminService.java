package com.hk.svr;

import java.util.List;

import com.hk.bean.ZoneAdmin;

public interface ZoneAdminService {
	boolean createZoneAdmin(ZoneAdmin zoneAdmin);

	void deleteZoneAdmin(long oid);

	ZoneAdmin getZoneAdmin(long userId);

	/**
	 * 地区管理员列表
	 * 
	 * @param pcityId 为0忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<ZoneAdmin> getZoneAdminList(int pcityId, int begin, int size);

	void updatePcityIdData();
}