package com.hk.bean;

import java.util.List;

/**
 * 企业模板分类
 * 
 * @author akwei
 */
public class CmpMod {

	private int modId;

	private String name;

	private List<CmpPageMod> homePageModList;

	private List<CmpPageMod> secondPageModList;

	private List<CmpPageMod> thirdPageModList;

	public int getModId() {
		return modId;
	}

	public void setModId(int modId) {
		this.modId = modId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CmpPageMod> getHomePageModList() {
		return homePageModList;
	}

	public void setHomePageModList(List<CmpPageMod> homePageModList) {
		this.homePageModList = homePageModList;
	}

	public List<CmpPageMod> getSecondPageModList() {
		return secondPageModList;
	}

	public void setSecondPageModList(List<CmpPageMod> secondPageModList) {
		this.secondPageModList = secondPageModList;
	}

	public List<CmpPageMod> getThirdPageModList() {
		return thirdPageModList;
	}

	public void setThirdPageModList(List<CmpPageMod> thirdPageModList) {
		this.thirdPageModList = thirdPageModList;
	}
}