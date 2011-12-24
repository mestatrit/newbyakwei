package com.hk.sms.cmd;

import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsPortProcessAble;

/**
 * 修改足迹介绍
 * 
 * @author yuanwei
 */
public class MgrCmpUpdateIntroCmd extends BaseCmd {
	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		String ss = receivedSms.getContent().toLowerCase();
		String intro = null;
		if (ss.startsWith("js+")) {
			intro = ss.substring(3);
		}
		else {
			intro = ss.substring(2);
		}
		if (DataUtil.isEmpty(intro)) {
			return null;
		}
		company.setIntro(intro);
		this.processUpdateCompany(receivedSms, company);
		return null;
	}
}
