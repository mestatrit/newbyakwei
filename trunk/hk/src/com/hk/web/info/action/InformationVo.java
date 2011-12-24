package com.hk.web.info.action;

import com.hk.bean.Information;
import com.hk.bean.User;

public class InformationVo {
	private User user;

	private Information information;

	private String useStatusData;

	public InformationVo(Information information) {
		this.information = information;
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getUseStatusData() {
		return useStatusData;
	}

	public void setUseStatusData(String useStatusData) {
		this.useStatusData = useStatusData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}