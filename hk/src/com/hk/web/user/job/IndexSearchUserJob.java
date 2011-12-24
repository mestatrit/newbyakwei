package com.hk.web.user.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.svr.UserService;

public class IndexSearchUserJob {
	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(IndexSearchUserJob.class);

	public void invoke() {
		log.info("begin index user ... ... ...");
		long begin = System.currentTimeMillis();
		this.userService.indexUser();
		long end = System.currentTimeMillis();
		log.info("end index user in [ " + (end - begin) + " ]");
	}
}