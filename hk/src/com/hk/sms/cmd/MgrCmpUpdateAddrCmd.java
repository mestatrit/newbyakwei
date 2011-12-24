package com.hk.sms.cmd;

import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsPortProcessAble;

/**
 * 修改足迹地址
 * 
 * @author yuanwei
 */
public class MgrCmpUpdateAddrCmd extends BaseCmd {
	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		String ss = receivedSms.getContent().toLowerCase();
		String addr = null;
		if (ss.startsWith("dz+")) {
			addr = ss.substring(3);
		}
		else {
			addr = ss.substring(2);
		}
		if (DataUtil.isEmpty(addr)) {
			return null;
		}
		company.setAddr(addr);
		this.processUpdateCompany(receivedSms, company);
		return null;
	}
}
