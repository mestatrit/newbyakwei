package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.UserOtherInfo;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.validate.UserValidate;

public class UpdateBirthdayCmd extends BaseCmd {

	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(UpdateBirthdayCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		log.info("begin UpdateBirthdayCmd");
		String mobile = receivedSms.getMobile();
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (info == null) {
			return null;
		}
		String dd = receivedSms.getContent();
		String m = dd.substring(0, 2);
		String d = dd.substring(2, 4);
		int month = Integer.parseInt(m);
		int date = Integer.parseInt(d);
		if (UserValidate.validateBirthday(month, date) != Err.SUCCESS) { // 验证生日
			Sms sms = new Sms();
			sms.setPort("383");
			sms.setContent("生日输入错误,请重新输入");
			sms.setLinkid(receivedSms.getLinkid());
			sms.setMobile(mobile);
			this.sendMsg(sms);
		}
		// 验证成功后保存生日
		this.userService.updateBirthday(info.getUserId(), month, date);
		Sms sms = new Sms();
		sms.setContent("直接回复本号码,发送您在做什么或您此刻的心情,作为进入火酷的纪念.");
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(mobile);
		this.sendMsg(sms);
		this.sendUpdateOk(receivedSms);
		return null;
	}

	private void sendUpdateOk(ReceivedSms receivedSms) {
		Sms sms = new Sms();
		sms.setContent("修改生日成功");
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(receivedSms.getMobile());
		this.sendMsg(sms);
	}
}