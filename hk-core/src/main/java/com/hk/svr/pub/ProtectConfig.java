package com.hk.svr.pub;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;

public class ProtectConfig implements InitializingBean {
	private List<String> list;

	private static final List<ProtectBean> plist = new ArrayList<ProtectBean>();

	public void setList(List<String> list) {
		this.list = list;
	}

	public void afterPropertiesSet() throws Exception {
		int i = 0;
		for (String s : list) {
			plist.add(new ProtectBean(i, s));
			i++;
		}
	}

	public static List<ProtectBean> getProtectList() {
		return plist;
	}
}