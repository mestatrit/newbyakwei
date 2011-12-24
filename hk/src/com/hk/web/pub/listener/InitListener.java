package com.hk.web.pub.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class InitListener extends ContextLoaderListener {

	// private final Log log = LogFactory.getLog(InitListener.class);
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		// log.info("初始化地区数据");
		// ZoneUtil.initZoneMap();
		// CompanyKindUtil.init();
	}
}