package com.hk.sms.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Follow;
import com.hk.bean.PvtChat;
import com.hk.bean.SendInfo;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSms;
import com.hk.bean.UserSmsPort;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.FollowService;
import com.hk.svr.MsgService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.FmtUrlContent;
import com.hk.web.util.HkWebConfig;
import com.hk.web.util.HkWebUtil;

public class UserSmsPortCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private MsgService msgService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	@Autowired
	private FollowService followService;

	private final Log log = LogFactory.getLog(UserSmsPortCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		log.info("begin usersmsport");
		UserOtherInfo userOtherInfo = this.getUserSmsMo(receivedSms)
				.getUserOtherInfo();
		String smsport = receivedSms.getPort();
		String userport = smsport.replaceFirst(smsPortProcessAble
				.getBaseSmsPort(), "");
		UserSmsPort userSmsPort = userSmsPortService
				.getUserSmsPortByPort(userport);
		if (userSmsPort == null) {// 端口不存在
			return null;
		}
		if (userSmsPort.getUserId() == 0) {// 没有用户使用
			return null;
		}
		if (userOtherInfo.getUserId() == userSmsPort.getUserId()) {// 如果给自己发送
			return null;
		}
		UserOtherInfo receiverInfo = this.userService
				.getUserOtherInfo(userSmsPort.getUserId());
		// 发送私信
		FmtUrlContent fmtUrlContent = new FmtUrlContent(receivedSms
				.getContent(), true, HkWebConfig.getShortUrlDomain());
		String msg = fmtUrlContent.getFmtContent();
		SendInfo sendInfo = this.msgService.sendMsg(userSmsPort.getUserId(),
				userOtherInfo.getUserId(), DataUtil.toHtmlRow(msg),
				PvtChat.SmsFLG_Y);
		// 查看2人之间的好友关系
		Follow follow = this.followService.getFollow(userOtherInfo.getUserId(),
				userSmsPort.getUserId());
		if (follow == null) {
			try {
				this.followService.addFollow(userOtherInfo.getUserId(),
						userSmsPort.getUserId(), null, true);
			}
			catch (AlreadyBlockException e) {// 忽略异常
			}
		}
		if (DataUtil.isEmpty(receiverInfo.getMobile())) {// 用户没有手机号码
			return null;
		}
		UserSmsPort myUserSmsPort = this.userSmsPortService
				.getUserSmsPortByUserId(userOtherInfo.getUserId());
		if (myUserSmsPort == null) {
			return null;
		}
		// 发送短信到对方
		Sms sms = new Sms();
		sms.setContent(receivedSms.getContent());
		sms.setMobile(receiverInfo.getMobile());
		sms.setPort(smsPortProcessAble.getBaseSmsPort()
				+ myUserSmsPort.getPort());
		UserSms userSms = HkWebUtil.sendSms(sms, userOtherInfo.getUserId(),
				null);
		if (userSms != null) {
			this.msgService.updatePvtChatSmsmsgId(sendInfo.getReceiverPvtChat()
					.getChatId(), userSms.getMsgId());
			this.msgService.updatePvtChatSmsmsgId(sendInfo.getSenderPvtChat()
					.getChatId(), userSms.getMsgId());
		}
		return null;
	}
}