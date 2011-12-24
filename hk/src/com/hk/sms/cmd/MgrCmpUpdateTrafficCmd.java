package com.hk.sms.cmd;

import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsPortProcessAble;

public class MgrCmpUpdateTrafficCmd extends BaseCmd {
	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		String ss = receivedSms.getContent().toLowerCase();
		String traffic = null;
		if (ss.startsWith("jt")) {
			traffic = ss.substring(3);
		}
		else {
			traffic = ss.substring(2);
		}
		if (DataUtil.isEmpty(traffic)) {
			return null;
		}
		company.setTraffic(traffic);
		this.processUpdateCompany(receivedSms, company);
		return null;
	}
}
