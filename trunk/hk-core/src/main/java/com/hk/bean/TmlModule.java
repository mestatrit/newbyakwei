package com.hk.bean;

import com.hk.frame.dao.annotation.Table;

@Table(name = "tmlmodule", id = "moduleid")
public class TmlModule {
	private int moduleId;

	private String title;

	private String funcurl;

	private String name;

	private String admin_title;

	private String admin_funcurl;

	public String getAdmin_title() {
		return admin_title;
	}

	public void setAdmin_title(String admin_title) {
		this.admin_title = admin_title;
	}

	public String getAdmin_funcurl() {
		return admin_funcurl;
	}

	public void setAdmin_funcurl(String admin_funcurl) {
		this.admin_funcurl = admin_funcurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFuncurl() {
		return funcurl;
	}

	public void setFuncurl(String funcurl) {
		this.funcurl = funcurl;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}