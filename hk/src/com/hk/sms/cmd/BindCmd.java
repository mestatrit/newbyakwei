package com.hk.sms.cmd;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.HkbLog;
import com.hk.bean.Invite;
import com.hk.bean.ScoreLog;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSmsPort;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;

public class BindCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	private final Log log = LogFactory.getLog(BindCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		String mobile = receivedSms.getMobile();
		String content = receivedSms.getContent();
		// content = content.replaceFirst("bd", "");
		content = content.substring(2, content.length());
		Sms sms = new Sms();
		sms.setMobile(mobile);
		sms.setLinkid(receivedSms.getLinkid());
		String[] info = content.split(":");
		String nickName = info[0];
		String pwd = info[1];
		log.info("mobile [ " + mobile + " ]");
		log.info("content [ " + content + " ]");
		log.info("nickName [ " + nickName + " ]");
		log.info("pwd [ " + pwd + " ]");
		User user = this.userService.getUserByNickName(nickName);
		UserOtherInfo userOtherInfo = null;
		if (user != null) {
			userOtherInfo = this.userService.getUserOtherInfo(user.getUserId());
		}
		if (userOtherInfo == null) {
			sms.setContent("用户账号或密码错误,绑定失败");
			this.sendMsg(sms);
			return null;
		}
		long userId = userOtherInfo.getUserId();
		if (this.userService.equalPwd(userOtherInfo.getUserId(), pwd)) {// 验证通过
			// if (MD5Util.md5Encode32(pwd).hashCode() ==
			// userOtherInfo.getPwdHash()) {
			this.userService.bindMobile(userId, mobile);
			// 绑定成功后,查询是否被邀请进入,给邀请者积分和火酷币
			List<Invite> list = this.inviteService
					.getSuccessInviteListByFriendId(userId);
			for (Invite i : list) {
				if (i.getAddhkbflg() == Invite.ADDHKBFLG_Y) {
					continue;
				}
				HkbLog log = HkbLog.create(i.getUserId(), HkbConfig.INVITE,
						userId, HkbConfig.getInvite());
				this.userService.addHkb(log);// 增加火酷币
				ScoreLog scoreLog = ScoreLog.create(userId, HkLog.INVITE,
						userId, ScoreConfig.getInvite());
				this.userService.addScore(scoreLog);// 增加积分
				i.setAddhkbflg(Invite.ADDHKBFLG_Y);
				this.inviteService.updateInvite(i);
			}
			// 如果没有短信号码，就分配一个号码
			UserSmsPort port = this.userSmsPortService
					.getUserSmsPortByUserId(userId);
			if (port == null) {
				this.userSmsPortService.makeAvailableUserSmsPort(userId);
			}
			String msgContent = mobile + "已经成功绑定到" + nickName
					+ ",以后本账号将可以使用昵称、手机号、email三种方式的登录火酷";
			sms.setContent(msgContent);
			this.sendMsg(sms);
		}
		else {
			sms.setContent("没有这个昵称或密码错误,直接编辑bd昵称:火酷密码回复进行手机绑定");
			this.sendMsg(sms);
		}
		return null;
	}
}