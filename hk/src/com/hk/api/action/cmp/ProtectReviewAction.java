package com.hk.api.action.cmp;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.api.util.SessionKey;
import com.hk.bean.ApiUser;
import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.bean.CompanyUserScore;
import com.hk.bean.Laba;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CompanyService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.web.util.HkWebConfig;

// @Component("/pubapi/protect/review")
public class ProtectReviewAction extends BaseApiAction {

	@Autowired
	private LabaService labaService;

	@Autowired
	private CompanyService companyService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return null;
	}

	public String update(HkRequest req, HkResponse resp) throws Exception {
		SessionKey sessionKey = this.getSessionKey(req);
		int score = req.getInt("score");
		String content = req.getString("content");
		long labaId = req.getLong("labaId");
		if (DataUtil.isEmpty(content)) {
			return "/review/op/op_addscore.do";
		}
		CompanyReview o = this.companyService.getCompanyReview(labaId);
		if (o == null) {
			return null;
		}
		o.setScore(score);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			APIUtil.sendFailRespStatus(resp, code);
			return null;
		}
		Company company = this.companyService.getCompany(o.getCompanyId());
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(sessionKey.getUserId());
		String longContent = null;
		// 更新评论内容
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		String add = "对{[" + o.getCompanyId() + "," + company.getName() + "}说:";
		String scontent = add + labaInfo.getParsedContent();
		if (!DataUtil.isEmpty(labaInfo.getLongParsedContent())) {
			longContent = add + labaInfo.getLongParsedContent();
		}
		this.labaService.updateLaba(labaId, scontent, longContent);
		this.companyService.updateCompanyReview(o);
		APIUtil.sendSuccessRespStatus(resp);
		return null;
	}

	public String create(HkRequest req, HkResponse resp) throws Exception {
		SessionKey sessionKey = this.getSessionKey(req);
		long companyId = req.getLong("companyId");
		int score = req.getInt("score");
		if (CompanyUserScore.validateScore(score) != Err.SUCCESS) {
			score = 0;
		}
		String content = req.getString("content");
		String ip = req.getString("ip");
		// content = DataUtil.toHtmlRow(content);
		if (HkSvrUtil.isNotCompany(companyId)) {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
			return null;
		}
		CompanyReview o = new CompanyReview();
		o.setScore(score);
		o.setCompanyId(companyId);
		o.setUserId(sessionKey.getUserId());
		req.setAttribute("o", o);
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			APIUtil.sendRespStatus(resp, "fail", code);
			return null;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		if (DataUtil.validateIp(ip)) {
			labaInfo.setIp(ip);
		}
		labaInfo.setUserId(sessionKey.getUserId());
		ApiUser apiUser = this.getApiUser(req);
		labaInfo.setSendFrom(apiUser.getApiUserId());
		long labaId = this.labaService.createLaba(labaInfo);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setContent(labaInfo.getParsedContent());
		o.setLongContent(labaInfo.getLongParsedContent());
		this.companyService.createCompanyReview(o);
		VelocityContext context = new VelocityContext();
		context.put("code", Err.SUCCESS);
		context.put("status", "ok");
		context.put("reviewcreate", true);
		context.put("labaId", labaId);
		this.write(resp, "vm/validate.vm", context);
		return null;
	}

	public String delete(HkRequest req, HkResponse resp) throws Exception {
		SessionKey o = this.getSessionKey(req);
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba != null && laba.getUserId() == o.getUserId()) {
			VelocityContext context = new VelocityContext();
			labaService.removeLaba(o.getUserId(), labaId, false);
			context.put("code", Err.SUCCESS);
			context.put("status", "ok");
			context.put("deletereview", true);
			this.write(resp, "vm/validate.vm", context);
		}
		else {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
		}
		return null;
	}
}