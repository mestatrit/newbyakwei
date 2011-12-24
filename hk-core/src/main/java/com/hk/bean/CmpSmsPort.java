package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "cmpsmsport", id = "portid")
public class CmpSmsPort {
	private long portId;

	private long companyId;

	private String port;

	public long getPortId() {
		return portId;
	}

	public void setPortId(long portId) {
		this.portId = portId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}