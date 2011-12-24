package com.hk.sms.cmd;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.HkbLog;
import com.hk.bean.Invite;
import com.hk.bean.RegCode;
import com.hk.bean.ScoreLog;
import com.hk.frame.util.DataUtil;
import com.hk.sms.ReceivedSms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.FollowService;
import com.hk.svr.InviteService;
import com.hk.svr.RegCodeService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.processor.InviteProcessor;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;

public class RegCodeRegisteCmd extends BaseCmd {

	@Autowired
	private UserService userService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private FollowService followService;

	@Autowired
	private RegCodeService regCodeService;

	@Autowired
	private InviteProcessor inviteProcessor;

	private final Log log = LogFactory.getLog(RegCodeRegisteCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble)
			throws EmailDuplicateException, MobileDuplicateException {
		log.info("regcode invite regist .....");
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		long userId = userSmsMo.getUserOtherInfo().getUserId();
		RegCode regCode = null;
		String code = receivedSms.getContent();
		if (!DataUtil.isEmpty(code)) {
			regCode = regCodeService.getRegCodeByName(code);
		}
		if (regCode != null && userSmsMo.isNewUser()) {// 邀请码注册方式
			if (regCode.isUsrCode()) {
				long inviterId = regCode.getObjId();
				// 建立注册关系
				this.userService.processRegCodeUser(regCode, userId);
				// 注册成功后，创建成功邀请
				this.inviteProcessor.acceptNewInvite(inviterId, userId,
						Invite.INVITETYPE_REGCODE, Invite.ADDHKBFLG_N, true);
				// 绑定成功后,查询是否被邀请进入,给邀请者积分和火酷币
				List<Invite> list = this.inviteService
						.getSuccessInviteListByFriendId(userId);
				for (Invite i : list) {
					if (i.getAddhkbflg() == Invite.ADDHKBFLG_N) {
						HkbLog hkbLog = HkbLog.create(i.getUserId(),
								HkLog.INVITE, userId, HkbConfig.getInvite());
						this.userService.addHkb(hkbLog);// 增加火酷币
						ScoreLog scoreLog = ScoreLog.create(i.getUserId(),
								HkLog.INVITE, userId, ScoreConfig.getInvite());
						this.userService.addScore(scoreLog);// 增加积分
						i.setAddhkbflg(Invite.ADDHKBFLG_Y);
						this.inviteService.updateInvite(i);
					}
				}
				try {
					this.followService.addFollow(userId, inviterId, null, true);
					this.followService.addFollow(inviterId, userId, null, true);
				}
				catch (AlreadyBlockException e) {//
				}
			}
		}
		return null;
	}
}