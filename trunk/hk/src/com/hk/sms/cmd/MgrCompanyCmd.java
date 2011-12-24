package com.hk.sms.cmd;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpSmsPort;
import com.hk.bean.Company;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.util.page.SimplePage;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CmpSmsPortService;
import com.hk.svr.CompanyService;
import com.hk.web.util.SmsSession;
import com.hk.web.util.SmsSessionUtil;

public class MgrCompanyCmd extends BaseCmd {
	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpSmsPortService cmpSmsPortService;

	private String listattr = "currentcompanyidlist";

	private String pageattr = "page";

	private int size = 3;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		UserOtherInfo info = userSmsMo.getUserOtherInfo();
		if (receivedSms.getContent().equals("0")) {
			processContent0(receivedSms, info);
		}
		else {
			this.processSelect(receivedSms, info);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void processSelect(ReceivedSms receivedSms, UserOtherInfo info) {
		int idx = Integer.parseInt(receivedSms.getContent()) - 1;
		SmsSession session = SmsSessionUtil.getSmsSession(receivedSms
				.getMobile());
		List<Long> idList = (List<Long>) session.getAttribute(listattr);
		if (idx < 0 || idList == null || idList.size() == 0) {
			this.processContent0(receivedSms, info);
			return;
		}
		if (idx < idList.size()) {
			Company company = this.companyService.getCompany(idList.get(idx));
			this.processCompany(receivedSms, company);
			return;
		}
		if (idx >= idList.size()) {
			Integer page = (Integer) session.getAttribute(this.pageattr);
			if (page == null) {
				page = 0;
			}
			page = page + 1;
			SimplePage simplePage = new SimplePage(size, page);
			List<Company> list = this.companyService.getCompanyListByUserId(
					info.getUserId(), simplePage.getBegin(), size);
			if (list.size() == 0) {
				page = page - 1;
				simplePage = new SimplePage(size, page);
				list = this.companyService.getCompanyListByUserId(info
						.getUserId(), simplePage.getBegin(), size);
			}
			this.processCompanyList(receivedSms, list);
		}
	}

	private void processContent0(ReceivedSms receivedSms, UserOtherInfo info) {
		SmsSession session = SmsSessionUtil.getSmsSession(receivedSms
				.getMobile());
		session.setAttribute(pageattr, 1);
		// 查看用户认领的企业
		List<Company> list = this.companyService.getCompanyListByUserId(info
				.getUserId(), 0, size);
		if (list.isEmpty()) {
			// 如果没有认领，下行。。。。
			return;
		}
		if (list.size() == 1) {
			Company o = list.iterator().next();
			this.processCompany(receivedSms, o);
		}
		else {
			this.processCompanyList(receivedSms, list);
		}
	}

	private void processCompanyList(ReceivedSms receivedSms, List<Company> list) {
		Sms sms = new Sms();
		sms.setLinkid(receivedSms.getLinkid());
		sms.setPort(receivedSms.getPort());
		sms.setMobile(receivedSms.getMobile());
		StringBuilder sb = new StringBuilder("回复序号来管理企业：\n");
		int i = 1;
		List<Long> idList = new ArrayList<Long>();
		for (Company c : list) {
			sb.append(i++).append("、").append(c.getName()).append("\n");
			idList.add(c.getCompanyId());
		}
		sms.setContent(sb.toString());
		SmsSession session = SmsSessionUtil.getSmsSession(receivedSms
				.getMobile());
		session.setAttribute(listattr, idList);
		this.sendMsg(sms);
	}

	private void processCompany(ReceivedSms receivedSms, Company company) {
		CmpSmsPort cmpSmsPort = this.cmpSmsPortService
				.getCmpSmsPortByCompanyId(company.getCompanyId());
		SmsPortProcessAble cmpSmsPortProcessAble = (SmsPortProcessAble) HkUtil
				.getBean("cmpSmsPortProcessAble");
		if (cmpSmsPort != null) {
			Sms sms = new Sms();
			sms.setLinkid(receivedSms.getLinkid());
			sms.setPort(cmpSmsPortProcessAble.getBaseSmsPort()
					+ cmpSmsPort.getPort());
			sms.setMobile(receivedSms.getMobile());
			String s = ResourceConfig.getText("func.sms.company.mgr.menu");
			s = DataUtil.toText(s);
			sms.setContent(s);
			this.sendMsg(sms);
		}
	}
}