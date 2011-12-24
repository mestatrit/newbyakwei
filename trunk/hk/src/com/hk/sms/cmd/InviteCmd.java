package com.hk.sms.cmd;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.DefFollowUser;
import com.hk.bean.HkbLog;
import com.hk.bean.Invite;
import com.hk.bean.ScoreLog;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.FollowService;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.processor.InviteProcessor;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;

public class InviteCmd extends BaseCmd {

	@Autowired
	private UserService userService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private InviteProcessor inviteProcessor;

	@Autowired
	private FollowService followService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		String mobile = receivedSms.getMobile();
		long inviterId = 0;
		String content = receivedSms.getContent();
		content = content.substring(2);
		int idx = content.indexOf(' ');
		if (idx != -1) {
			String nickName = content.substring(0, idx);
			User user = this.userService.getUserByNickName(nickName);
			if (user != null) {
				inviterId = user.getUserId();
			}
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByMobile(mobile);
		if (info != null) {
			return null;
		}
		String password = DataUtil.getRandom(4);
		long userId = this.userService.createUser(mobile, password, null);
		if (inviterId > 0) {
			this.inviteProcessor.acceptNewInvite(inviterId, userId,
					Invite.INVITETYPE_SMS, Invite.ADDHKBFLG_Y, true);
			// 绑定成功后,查询是否被邀请进入,给邀请者积分和火酷币
			List<Invite> list = this.inviteService
					.getSuccessInviteListByFriendId(userId);
			for (Invite i : list) {
				HkbLog hkbLog = HkbLog.create(i.getUserId(), HkLog.INVITE,
						userId, HkbConfig.getInvite());
				this.userService.addHkb(hkbLog);// 增加火酷币
				ScoreLog scoreLog = ScoreLog.create(i.getUserId(),
						HkLog.INVITE, userId, ScoreConfig.getInvite());
				this.userService.addScore(scoreLog);// 增加积分
				i.setAddhkbflg(Invite.ADDHKBFLG_Y);
				this.inviteService.updateInvite(i);
			}
			try {
				this.followService.addFollow(userId, inviterId, null, true);
				this.followService.addFollow(inviterId, userId, null, true);
			}
			catch (AlreadyBlockException e) {//
			}
		}
		// 添加默认关注
		List<DefFollowUser> list = this.userService.getDefFollowUserList(0, 20);
		for (DefFollowUser o : list) {
			try {
				this.followService
						.addFollow(userId, o.getUserId(), null, false);
			}
			catch (AlreadyBlockException e) {
				// TODO Auto-generated catch block
			}
		}
		Sms sms = new Sms();
		sms.setContent(ResourceConfig.getText("sms.reg_ok", password));
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(mobile);
		sms.setPort("1");
		this.sendMsg(sms);
		return null;
	}
}