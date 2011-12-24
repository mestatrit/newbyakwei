package com.hk.sms.cmd;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.ChgCardAct;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.ChgCardActService;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.user.exception.NoAvailableActSysNumException;

public class CreateChgCardActCmd extends BaseCmd {
	@Autowired
	private UserService userService;

	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	private UserCardService userCardService;

	private int delPersistHour;

	public void setDelPersistHour(int delPersistHour) {
		this.delPersistHour = delPersistHour;
	}

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		Date date = new Date();
		UserSmsMo mo = this.getUserSmsMo(receivedSms);
		ChgCardAct o = new ChgCardAct();
		o.setUserId(mo.getUserOtherInfo().getUserId());
		User user = this.userService.getUser(mo.getUserOtherInfo().getUserId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String d = sdf.format(date);
		o.setName(user.getNickName() + "创建于" + d);
		o.setChgflg(UserCard.CHGFLG_PUBLIC);
		o.setCreateTime(date);
		o.setPersistHour(delPersistHour);
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setLinkid(receivedSms.getLinkid());
		try {
			this.chgCardActService.createChgCardAct(o);
			UserCard userCard = this.userCardService.getUserCard(user
					.getUserId());
			if (userCard == null) {// 如果用户没有名片,默认创建
				userCard = new UserCard(mo.getUserOtherInfo(), user);
				this.userCardService.createUserCard(userCard);
			}
			sms.setContent(ResourceConfig.getText("sms.createchgcardact", o
					.getSysnum(), o.getActId()));// 活动创建成功，发送短信到创建人
			this.sendMsg(sms);
		}
		catch (NoAvailableActSysNumException e) {
			sms.setContent(ResourceConfig.getText("sms.createchgcardact_fail"));// 失败
			this.sendMsg(sms);
		}
		return null;
	}
}