package com.hk.svr.pub;

import org.springframework.beans.factory.InitializingBean;
import com.hk.bean.CompanyKindUtil;

public class InitBean implements InitializingBean {

	private boolean init;

	public void setInit(boolean init) {
		this.init = init;
	}

	public void afterPropertiesSet() throws Exception {
		if (this.init) {
			ZoneUtil.initZoneMap();
			CompanyKindUtil.init();
		}
	}
}