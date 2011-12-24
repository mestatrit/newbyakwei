package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Laba;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.ContentFilterUtil;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.web.util.HkWebConfig;

public class CreateLabaCmd extends BaseCmd {
	private final Log log = LogFactory.getLog(CreateLabaCmd.class);

	@Autowired
	private LabaService labaService;

	@Autowired
	private ContentFilterUtil contentFilterUtil;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		log.info("create laba from sms");
		if (receivedSms.getMobile() == null
				|| receivedSms.getMobile().equals("")) {
			return null;
		}
		UserOtherInfo userOtherInfo = this.getUserSmsMo(receivedSms)
				.getUserOtherInfo();
		if (userOtherInfo == null) {
			return null;
		}
		String content = receivedSms.getContent();
		if (DataUtil.isEmpty(content)) {
			return null;
		}
		if (this.contentFilterUtil.hasFilterString(content)) {
			return null;
		}
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userOtherInfo.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		this.labaService.createLaba(labaInfo);
		return null;
	}
}