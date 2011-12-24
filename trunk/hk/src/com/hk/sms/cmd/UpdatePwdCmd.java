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

public class UpdatePwdCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	private final Log log = LogFactory.getLog(UpdatePwdCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		log.info("begin UpdatePwdCmd");
		String mobile = receivedSms.getMobile();
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		UserOtherInfo info = userSmsMo.getUserOtherInfo();
		if (UserValidate.validatePassword(receivedSms.getContent()) != Err.SUCCESS) {// 密码验证不通过
			Sms sms = new Sms();
			sms.setContent("密码是由4-16个数字或字母组成");
			sms.setLinkid(receivedSms.getLinkid());
			sms.setMobile(mobile);
			sms.setPort("7");
			this.sendMsg(sms);
			return null;
		}
		this.userService.updateNewPwd(info.getUserId(), receivedSms
				.getContent());
		// 提示修改成功
		Sms sms = new Sms();
		sms.setContent("密码修改成功");
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(mobile);
		this.sendMsg(sms);
		return null;
	}
}