package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpgroupsmsport", id = "oid")
public class CmpGroupSmsPort {
	private long oid;

	private long cmpgroupId;

	private String port;

	public long getCmpgroupId() {
		return cmpgroupId;
	}

	public void setCmpgroupId(long cmpgroupId) {
		this.cmpgroupId = cmpgroupId;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}
}