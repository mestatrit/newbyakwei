package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;

public class UpdateNickNameCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private UserCardService userCardService;

	private final Log log = LogFactory.getLog(UpdateNickNameCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		log.info("begin UpdateNickNameCmd");
		String nickName = receivedSms.getContent().trim();
		if (nickName.length() > 10) {
			nickName = nickName.substring(0, 10);
		}
		String name = receivedSms.getContent().trim();
		if (name.length() > 10) {
			name = name.substring(0, 10);
		}
		String mobile = receivedSms.getMobile();
		UserSmsMo mo = this.getUserSmsMo(receivedSms);
		UserOtherInfo info = mo.getUserOtherInfo();
		info.setName(name);
		this.userService.updateUserOtherInfo(info);
		User user = this.userService.getUser(info.getUserId());
		if (user.getNickName().equals(user.getUserId() + "")) {
			this.userService.updateNickName(info.getUserId(), nickName);
		}
		if (!mo.isNewUser()) {
			Sms sms = new Sms();
			sms.setContent("修改信息成功");
			sms.setLinkid(receivedSms.getLinkid());
			sms.setMobile(mobile);
			this.sendMsg(sms);
		}
		UserCard userCard = this.userCardService.getUserCard(info.getUserId());
		if (userCard != null) {
			userCard.setNickName(user.getNickName());
			userCard.setName(name);
			this.userCardService.updateUserCard(userCard);
		}
		return null;
	}
}