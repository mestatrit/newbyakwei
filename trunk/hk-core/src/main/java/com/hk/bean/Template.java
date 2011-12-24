package com.hk.bean;

import java.util.List;

public class Template {
	private int templateId;

	private String name;

	private int kindId;

	private List<TmlModule> tmlModuleList;

	public List<TmlModule> getTmlModuleList() {
		return tmlModuleList;
	}

	public void setTmlModuleList(List<TmlModule> tmlModuleList) {
		this.tmlModuleList = tmlModuleList;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKindId() {
		return kindId;
	}

	public void setKindId(int kindId) {
		this.kindId = kindId;
	}
}