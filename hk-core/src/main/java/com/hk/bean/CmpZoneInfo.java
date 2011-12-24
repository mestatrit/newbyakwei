package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.svr.pub.ZoneUtil;

@Table(name = "cmpzoneinfo", id = "sysid")
public class CmpZoneInfo {
	@Id
	private int sysId;

	@Column
	private int cmpcount;

	@Column
	private int pcityId;

	public String getName() {
		Pcity pcity = ZoneUtil.getPcity(pcityId);
		if (pcity != null) {
			return pcity.getName();
		}
		return null;
	}

	public Pcity getPcity() {
		return ZoneUtil.getPcity(pcityId);
	}

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public int getCmpcount() {
		return cmpcount;
	}

	public void setCmpcount(int cmpcount) {
		this.cmpcount = cmpcount;
	}

	public int getPcityId() {
		return pcityId;
	}

	public void setPcityId(int pcityId) {
		this.pcityId = pcityId;
	}
}