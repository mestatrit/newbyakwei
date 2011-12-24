package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.hk.bean.Company;
import com.hk.bean.CompanyFeed;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.svr.CompanyService;
import com.hk.web.util.HkWebConfig;

public class CompanyFeedVo {
	private CompanyFeed companyFeed;

	private String content;

	public CompanyFeedVo(CompanyFeed companyFeed) {
		this.companyFeed = companyFeed;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CompanyFeed getCompanyFeed() {
		return companyFeed;
	}

	public static List<CompanyFeedVo> createVoList(List<CompanyFeed> list) {
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		List<Long> idList = new ArrayList<Long>();
		for (CompanyFeed feed : list) {
			idList.add(feed.getCompanyId());
		}
		Map<Long, Company> map = companyService.getCompanyMapInId(idList);
		List<CompanyFeedVo> volist = new ArrayList<CompanyFeedVo>();
		for (CompanyFeed feed : list) {
			CompanyFeedVo vo = new CompanyFeedVo(feed);
			String url = "http://" + HkWebConfig.getWebDomain()
					+ "/home.do?userId=" + feed.getUserId();
			String nickName = feed.getUser().getNickName();
			String companyName = map.get(feed.getCompanyId()).getName();
			String companyUrl = "http://" + HkWebConfig.getWebDomain()
					+ "/e/cmp.do?companyId=" + feed.getCompanyId();
			String content = ResourceConfig.getText(
					"func.companyfeed.usercreate", url, nickName, companyUrl,
					companyName);
			vo.setContent(content);
			volist.add(vo);
		}
		return volist;
	}
}