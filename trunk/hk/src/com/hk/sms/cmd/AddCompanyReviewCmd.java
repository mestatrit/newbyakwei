package com.hk.sms.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Company;
import com.hk.bean.CompanyReview;
import com.hk.bean.HkbLog;
import com.hk.bean.Laba;
import com.hk.bean.ScoreLog;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CompanyService;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;
import com.hk.web.util.HkWebConfig;

public class AddCompanyReviewCmd extends BaseCmd {
	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		long userId = userSmsMo.getUserOtherInfo().getUserId();
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		int score = this.getScore(receivedSms.getContent());
		long companyId = company.getCompanyId();
		String content = null;
		if (score == 0) {
			content = receivedSms.getContent();
		}
		else {
			content = receivedSms.getContent().substring(1);
		}
		if (DataUtil.isEmpty(content)) {
			return null;
		}
		// content = DataUtil.toHtmlRow(content);
		CompanyReview o = new CompanyReview();
		o.setScore(score);
		o.setCompanyId(companyId);
		o.setUserId(userId);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userId);
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		long labaId = this.labaService.createLaba(labaInfo);
		o.setLabaId(labaId);
		o.setSendFrom(labaInfo.getSendFrom());
		o.setContent(labaInfo.getParsedContent());
		this.companyService.createCompanyReview(o);
		HkbLog hkbLog = HkbLog.create(userId, HkLog.CREATESMSCOMPANYREVIEW,
				labaId, HkbConfig.getCreateSmsCompanyReview());
		ScoreLog scoreLog = ScoreLog.create(userId,
				HkLog.CREATESMSCOMPANYREVIEW, labaId, ScoreConfig
						.getCreateSmsCompanyReview());
		this.userService.addHkb(hkbLog);
		this.userService.addScore(scoreLog);
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setLinkid(receivedSms.getLinkid());
		sms.setPort(receivedSms.getPort());
		sms.setContent(ResourceConfig.getText(
				"func.sms.company.review.createsuccess", company.getName(),
				companyId + ""));
		this.sendMsg(sms);
		return null;
	}

	private int getScore(String content) {
		String s = content.substring(0, 1);
		if (DataUtil.isNumber(s)) {
			int i = Integer.parseInt(s);
			if (i == 5) {
				return 3;
			}
			if (i == 4) {
				return 2;
			}
			if (i == 3) {
				return 1;
			}
			if (i == 2) {
				return -1;
			}
			if (i == 1) {
				return -2;
			}
		}
		else {
			return 0;
		}
		return 0;
	}
}