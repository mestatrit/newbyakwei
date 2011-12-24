package com.hk.sms.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;

public class UpdateNickName extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private UserCardService userCardService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		String s = receivedSms.getContent().substring(2);
		if (s.startsWith("+")) {
			s = s.substring(1);
		}
		String nickName = s;
		UserOtherInfo info = this.getUserSmsMo(receivedSms).getUserOtherInfo();
		if (this.userService.updateNickName(info.getUserId(), nickName)) {
			UserCard userCard = this.userCardService.getUserCard(info
					.getUserId());
			if (userCard != null) {
				User user = this.userService.getUser(info.getUserId());
				userCard.setNickName(user.getNickName());
				this.userCardService.updateUserCard(userCard);
			}
		}
		else {
			Sms sms = new Sms();
			sms.setMobile(receivedSms.getMobile());
			sms.setLinkid(receivedSms.getLinkid());
			sms.setContent("昵称:" + nickName + "已经存在,请重新输入");
			this.sendMsg(sms);
		}
		return null;
	}
}