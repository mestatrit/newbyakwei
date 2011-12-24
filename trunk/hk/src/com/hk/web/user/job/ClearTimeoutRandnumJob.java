package com.hk.web.user.job;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.svr.UserService;

public class ClearTimeoutRandnumJob {
	@Autowired
	private UserService userService;

	public void invoke() {
		this.userService.clearTimeoutRandnum();
	}
}