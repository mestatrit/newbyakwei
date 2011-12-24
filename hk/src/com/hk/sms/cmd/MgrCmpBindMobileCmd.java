package com.hk.sms.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpWatch;
import com.hk.bean.Company;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;

/**
 * 设定当前值班人员
 * 
 * @author yuanwei
 */
public class MgrCmpBindMobileCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		String ss = receivedSms.getContent().toLowerCase();
		String usermobile = null;
		if (ss.startsWith("zb+")) {
			usermobile = ss.substring(3);
		}
		else {
			usermobile = ss.substring(2);
		}
		UserOtherInfo info = this.userService
				.getUserOtherInfoByMobile(usermobile);
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setPort(receivedSms.getPort());
		sms.setLinkid(receivedSms.getLinkid());
		String s = ResourceConfig.getText("func.sms.company.mgr.menu");
		s = DataUtil.toText(s);
		if (info == null) {// 不存在的用户
			sms.setContent(ResourceConfig
					.getText("func.sms.company.mgr.nouser")
					+ "\n" + s);
			return null;
		}
		CmpWatch cmpWatch = this.companyService.getCmpWatch(company
				.getCompanyId(), info.getUserId());
		if (cmpWatch == null) {// 不存在的值班用户
			sms.setContent(ResourceConfig
					.getText("func.sms.company.mgr.nocmpwatch")
					+ "\n" + s);
			return null;
		}
		this.companyService.setCmpWatchDuty(company.getCompanyId(), info
				.getUserId());
		sms.setContent(ResourceConfig
				.getText("func.sms.company.mgr.bindduty.success")
				+ "\n" + s);
		this.sendMsg(sms);
		return null;
	}

	public static void main(String[] args) {
		String s = "ZB+sdfsdf";
		System.out.println(s.substring(2));
	}
}