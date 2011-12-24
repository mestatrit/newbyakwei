package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

@Table(name = "tb_domain")
public class Tb_Domain {

	@Id
	private int domainid;

	@Column
	private String name;

	public static final byte SHOW_LEVEL_ALL = 0;

	public static final byte SHOW_LEVEL_SINGLE = 1;

	@Column
	private byte show_level;

	public void setDomainid(int domainid) {
		this.domainid = domainid;
	}

	public int getDomainid() {
		return domainid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public byte getShow_level() {
		return show_level;
	}

	public void setShow_level(byte showLevel) {
		show_level = showLevel;
	}
}