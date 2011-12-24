package com.hk.svr.equipment;

import java.util.Map;

public class EquipmentMsg {

	private long eid;

	private long userId;

	private Map<String, String> datamap;

	public Map<String, String> getDatamap() {
		return datamap;
	}

	public void setDatamap(Map<String, String> datamap) {
		this.datamap = datamap;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public long getEid() {
		return eid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}