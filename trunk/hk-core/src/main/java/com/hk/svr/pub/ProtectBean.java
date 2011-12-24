package com.hk.svr.pub;

public class ProtectBean {
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public ProtectBean(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}