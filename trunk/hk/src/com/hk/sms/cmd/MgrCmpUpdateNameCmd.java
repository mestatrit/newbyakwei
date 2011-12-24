package com.hk.sms.cmd;

import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;

/**
 * 修改足迹名称
 * 
 * @author yuanwei
 */
public class MgrCmpUpdateNameCmd extends BaseCmd {
	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		Company company = this.getCompany(receivedSms, smsPortProcessAble);
		if (company == null) {
			return null;
		}
		String s = receivedSms.getContent().toLowerCase();
		String name = null;
		if (s.startsWith("mc+")) {
			name = s.substring(3);
		}
		else {
			name = s.substring(2);
		}
		if (DataUtil.isEmpty(name)) {
			return null;
		}
		company.setName(name);
		this.processUpdateCompany(receivedSms, company);
		return null;
	}

	protected Sms getReplySms(ReceivedSms receivedSms) {
		Sms sms = new Sms();
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(receivedSms.getMobile());
		sms.setPort(receivedSms.getPort());
		return sms;
	}
}