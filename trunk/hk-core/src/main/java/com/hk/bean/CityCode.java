package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "citycode", id = "codeid")
public class CityCode {
	private int codeId;

	private String name;

	private String code;

	public int getCodeId() {
		return codeId;
	}

	public void setCodeId(int codeId) {
		this.codeId = codeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}