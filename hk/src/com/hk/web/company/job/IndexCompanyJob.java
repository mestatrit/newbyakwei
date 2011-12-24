package com.hk.web.company.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.svr.CompanyService;

public class IndexCompanyJob {

	// private final Log log = LogFactory.getLog(IndexCompanyJob.class);
	@Autowired
	private CompanyService companyService;

	public void invoke() {
		// long begin = System.currentTimeMillis();
		this.companyService.createSearchIndex();
		this.companyService.createCmpTagSearchIndex();
		// long end = System.currentTimeMillis();
		// log.info("end index company in [ " + (end - begin) + " ]");
	}
}