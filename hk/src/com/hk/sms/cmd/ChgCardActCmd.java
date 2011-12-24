package com.hk.sms.cmd;

import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ActUser;
import com.hk.bean.ChgCardAct;
import com.hk.bean.ChgCardActUser;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.ChgCardActService;
import com.hk.svr.FollowService;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.friend.exception.AlreadyBlockException;

public class ChgCardActCmd extends BaseCmd {
	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	private UserCardService userCardService;

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	ActSysNumService actSysNumService;

	@Autowired
	private ActService actService;

	@Autowired
	private CreateLabaCmd createLabaCmd;

	private final Log log = org.apache.commons.logging.LogFactory
			.getLog(ChgCardActCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		log.info("begin prodcess chgcardact ... ...");
		UserSmsMo mo = this.getUserSmsMo(receivedSms);
		String s = receivedSms.getContent();
		String num = null;
		String name = null;
		if (s.length() > 6) {
			num = s.substring(0, 6);
			name = s.substring(6).trim();
			UserOtherInfo info = mo.getUserOtherInfo();
			info.setName(name);
			this.userService.updateUserOtherInfo(info);
			User user = this.userService.getUser(info.getUserId());
			user.setNickName(name);
			this.userService.updateNickName(info.getUserId(), name);
		}
		else {
			num = receivedSms.getContent();
		}
		// 查看号码是否存在
		ActSysNum o = this.actSysNumService.getActSysNumBySysNum(num);
		if (o == null) {// 如果不存在交换名片活动，则吹喇叭
			createLabaCmd.execute(receivedSms, smsPortProcessAble);
			return null;
		}
		if (o.isOver()) {
			Sms sms = new Sms();
			sms.setMobile(receivedSms.getMobile());
			sms.setLinkid(receivedSms.getLinkid());
			sms.setContent(ResourceConfig.getText("sms.chgcardact_pause_stop"));
			this.sendMsg(sms);
			return null;
		}
		if (o.getUsetype() == ActSysNum.USETYPE_CARD) {
			this.processChgCard(o, receivedSms, mo);
		}
		else {
			this.processJoinAct(o, receivedSms, mo);
		}
		return null;
	}

	/**
	 * 参加活动
	 * 
	 * @param actSysNum
	 * @param receivedSms
	 * @param mo
	 */
	private void processJoinAct(ActSysNum actSysNum, ReceivedSms receivedSms,
			UserSmsMo mo) {
		long actId = actSysNum.getActId();
		long userId = mo.getUserOtherInfo().getUserId();
		Act act = this.actService.getAct(actId);
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setLinkid(receivedSms.getLinkid());
		if (act == null) {
			return;
		}
		if (act.getEndTime().getTime() < System.currentTimeMillis()) {// 活动已经结束，不能报名了
			sms.setContent(ResourceConfig.getText("sms.act_end_cannot_join"));
			this.sendMsg(sms);
			return;
		}
		if (act.getBeginTime().getTime() < System.currentTimeMillis()) {// 活动已经开始，不能报名了
			sms.setContent(ResourceConfig.getText("sms.act_begin_cannot_join"));
			this.sendMsg(sms);
			return;
		}
		if (this.actService.getActUser(actId, userId) == null) {
			this.actService.joinAct(actId, userId);
			List<ActUser> list = this.actService.getActUserList(actId);
			for (ActUser o : list) {
				if (o.getUserId() != userId) {
					try {
						this.followService.addFollow(userId, o.getUserId(),
								null, false);
						this.followService.addFollow(o.getUserId(), userId,
								null, false);
					}
					catch (AlreadyBlockException e) {// 忽略此异常
					}
				}
			}
		}
		sms.setContent(ResourceConfig
				.getText("sms.joinact_success", actId + ""));
		this.sendMsg(sms);
	}

	/**
	 * 交换名片活动
	 * 
	 * @param actSysNum
	 * @param receivedSms
	 * @param mo
	 */
	private void processChgCard(ActSysNum actSysNum, ReceivedSms receivedSms,
			UserSmsMo mo) {
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setLinkid(receivedSms.getLinkid());
		ChgCardAct act = this.chgCardActService.getChgCardAct(actSysNum
				.getActId());
		if (act == null) {
			return;
		}
		if (act.isOver()) {
			sms.setContent(ResourceConfig.getText("sms.chgcardact_pause_stop"));
			this.sendMsg(sms);
			return;
		}
		if (act.isPause() || act.isStop()) {
			sms.setContent(ResourceConfig.getText("sms.chgcardact_pause_stop"));
			this.sendMsg(sms);
			return;
		}
		this.chgCardActService.createChgCardActUser(act.getActId(), mo
				.getUserOtherInfo().getUserId());// 加入活动
		// 与所有交换名片的人相互关注
		List<ChgCardActUser> ulist = this.chgCardActService
				.getChgCardActUserList(act.getActId());
		for (ChgCardActUser u : ulist) {
			try {
				this.followService.addFollow(mo.getUserOtherInfo().getUserId(),
						u.getUserId(), null, false);
			}
			catch (AlreadyBlockException e) {//
			}
			try {
				this.followService.addFollow(u.getUserId(), mo
						.getUserOtherInfo().getUserId(), null, false);
			}
			catch (AlreadyBlockException e) {//
			}
		}
		UserCard userCard = this.userCardService.getUserCard(mo
				.getUserOtherInfo().getUserId());
		if (userCard == null) {// 如果用户没有名片,默认创建
			User user = this.userService.getUser(mo.getUserOtherInfo()
					.getUserId());
			userCard = new UserCard(mo.getUserOtherInfo(), user);
			this.userCardService.createUserCard(userCard);
		}
		sms.setContent(ResourceConfig.getText("sms.chgcardact_change_ok", act
				.getActId()));
		this.sendMsg(sms);
		return;
	}
}