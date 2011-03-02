package com.hk.frame.web.view;

import java.util.List;

public class ShowModeUtil {
	private List<ShowMode> modeList;

	public void setModeList(List<ShowMode> modeList) {
		this.modeList = modeList;
	}

	public List<ShowMode> getModeList() {
		return modeList;
	}

	public ShowMode getShowMode(int id) {
		for (ShowMode o : modeList) {
			if (o.getModeId() == id) {
				return o;
			}
		}
		return null;
	}
}