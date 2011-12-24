package com.hk.listener.act.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAct;
import com.hk.bean.CmpUnionFeed;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.listener.act.CmpActEventListener;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CompanyService;
import com.hk.svr.pub.CmpUnionMessageUtil;

public class CmpActEventListenerImpl implements CmpActEventListener {
	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private CompanyService companyService;

	public void cmpActCreated(CmpAct cmpAct) {
		Company company = companyService.getCompany(cmpAct.getCompanyId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("actid", String.valueOf(cmpAct.getActId()));
		map.put("name", cmpAct.getName());
		map.put("cmp", company.getName());
		CmpUnionFeed feed = new CmpUnionFeed();
		feed.setUid(cmpAct.getUid());
		feed.setFeedflg(CmpUnionMessageUtil.FEED_CREATECMPACT);
		feed.setObjId(cmpAct.getCompanyId());
		feed.setData(DataUtil.toJson(map));
		this.cmpUnionMessageService.createCmpUnionFeed(feed);
	}
}