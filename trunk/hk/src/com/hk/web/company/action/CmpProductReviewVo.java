package com.hk.web.company.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductReview;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CmpProductService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class CmpProductReviewVo {
	private CmpProductReview cmpProductReview;

	private String content;

	private String longContent;

	public CmpProductReviewVo(CmpProductReview cmpProductReview) {
		this.cmpProductReview = cmpProductReview;
	}

	public CmpProductReview getCmpProductReview() {
		return cmpProductReview;
	}

	public void setCmpProductReview(CmpProductReview cmpProductReview) {
		this.cmpProductReview = cmpProductReview;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLongContent() {
		return longContent;
	}

	public void setLongContent(String longContent) {
		this.longContent = longContent;
	}

	public static CmpProductReviewVo createVo(CmpProductReview review,
			UrlInfo urlInfo) {
		CmpProductReviewVo o = new CmpProductReviewVo(review);
		LabaOutPutParser parser = new LabaOutPutParser();
		String content = null;
		if (urlInfo == null) {
			content = parser.getText(review.getContent());
		}
		else {
			content = parser.getHtml(urlInfo, review.getContent(), 0);
		}
		o.setContent(content);
		return o;
	}

	public static List<CmpProductReviewVo> createVoList(
			List<CmpProductReview> list, UrlInfo urlInfo, boolean needUser,
			boolean needProduct) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		CmpProductService cmpProductService = (CmpProductService) HkUtil
				.getBean("cmpProductService");
		if (needUser) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpProductReview review : list) {
				idList.add(review.getUserId());
			}
			Map<Long, User> usermap = userService.getUserMapInId(idList);
			for (CmpProductReview review : list) {
				review.setUser(usermap.get(review.getUserId()));
			}
		}
		if (needProduct) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpProductReview review : list) {
				idList.add(review.getProductId());
			}
			Map<Long, CmpProduct> pmap = cmpProductService
					.getCmpProductMapInId(idList);
			for (CmpProductReview review : list) {
				review.setCmpProduct(pmap.get(review.getProductId()));
			}
		}
		List<CmpProductReviewVo> volist = new ArrayList<CmpProductReviewVo>();
		for (CmpProductReview r : list) {
			CmpProductReviewVo o = new CmpProductReviewVo(r);
			LabaOutPutParser parser = new LabaOutPutParser();
			String content = parser.getHtml(urlInfo, r.getContent(), r
					.getUserId());
			o.setContent(content);
			volist.add(o);
		}
		return volist;
	}
}