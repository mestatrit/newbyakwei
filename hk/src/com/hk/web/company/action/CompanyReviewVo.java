package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import com.hk.bean.CompanyReview;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.impl.CompanyScoreConfig;
import com.hk.svr.laba.parser.LabaAPIOutPutParser;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class CompanyReviewVo {
	private CompanyReview companyReview;

	private String content;

	private User user;

	private String starRate;

	private boolean hasMoreContent;

	public boolean isHasMoreContent() {
		return hasMoreContent;
	}

	public void setHasMoreContent(boolean hasMoreContent) {
		this.hasMoreContent = hasMoreContent;
	}

	public String getStarRate() {
		return starRate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CompanyReviewVo(CompanyReview companyReview) {
		this.companyReview = companyReview;
		if (companyReview.getLongContent() != null) {
			setHasMoreContent(true);
		}
		if (this.user == null) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			this.user = userService.getUser(this.companyReview.getUserId());
		}
		Integer star = CompanyScoreConfig
				.getStar(this.companyReview.getScore());
		if (star == null) {
			star = 0;
		}
		this.starRate = star.intValue() + "";
	}

	public CompanyReview getCompanyReview() {
		return companyReview;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	private static CompanyReviewVo createVo(CompanyReview companyReview,
			UrlInfo urlInfo) {
		CompanyReviewVo o = new CompanyReviewVo(companyReview);
		LabaOutPutParser parser = new LabaOutPutParser();
		String content = null;
		if (urlInfo == null) {
			content = parser.getText(companyReview.getContent());
		}
		else {
			content = parser.getHtml(urlInfo, companyReview.getContent(),
					companyReview.getUserId());
		}
		o.setContent(content);
		return o;
	}

	public static CompanyReviewVo createVo(CompanyReview companyReview,
			UrlInfo urlInfo, boolean parseLong) {
		if (parseLong && companyReview.getLongContent() != null) {
			CompanyReviewVo o = new CompanyReviewVo(companyReview);
			LabaOutPutParser parser = new LabaOutPutParser();
			String content = null;
			if (urlInfo == null) {
				content = parser.getText(companyReview.getLongContent());
			}
			else {
				content = parser.getHtml(urlInfo, companyReview
						.getLongContent(), companyReview.getUserId());
			}
			o.setContent(content);
			return o;
		}
		return createVo(companyReview, urlInfo);
	}

	public static List<CompanyReviewVo> createVoList(List<CompanyReview> list,
			UrlInfo urlInfo) {
		List<CompanyReviewVo> volist = new ArrayList<CompanyReviewVo>();
		for (CompanyReview r : list) {
			CompanyReviewVo o = new CompanyReviewVo(r);
			LabaOutPutParser parser = new LabaOutPutParser();
			String content = parser.getHtml(urlInfo, r.getContent(), r
					.getUserId());
			o.setContent(content);
			volist.add(o);
		}
		return volist;
	}

	public static List<CompanyReviewVo> createVoListForAPI(
			List<CompanyReview> list) {
		List<CompanyReviewVo> volist = new ArrayList<CompanyReviewVo>();
		for (CompanyReview r : list) {
			CompanyReviewVo o = new CompanyReviewVo(r);
			LabaOutPutParser parser = new LabaAPIOutPutParser();
			String content = parser.getText(r.getContent());
			o.setContent(content);
			volist.add(o);
		}
		return volist;
	}
}