package com.hk.web.company.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.svr.badge.HandleUserBadgeProcessor;

public class HandleUserJob {
	@Autowired
	private HandleUserBadgeProcessor handleUserBadgeProcessor;

	public void invoke() {
		this.handleUserBadgeProcessor.invoke();
	}
}