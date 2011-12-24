package com.hk.sms.cmd;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmdData;
import com.hk.bean.CmpAct;
import com.hk.bean.CmpActUser;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CmdDataService;
import com.hk.svr.CmpActService;

public class JoinCmpActCmd extends BaseCmd {
	@Autowired
	private CmpActService cmpActService;

	@Autowired
	private CmdDataService cmdDataService;

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception {
		UserSmsMo userSmsMo = this.getUserSmsMo(receivedSms);
		long userId = userSmsMo.getUserOtherInfo().getUserId();
		CmdData cmdData = this.cmdDataService.getCmdDataByName(receivedSms
				.getContent());
		long actId = cmdData.getOid();
		CmpAct act = this.cmpActService.getCmpAct(actId);
		Sms sms = this.createReceiveSms(receivedSms);
		if (act == null || !act.isRun()) {
			sms.setContent(ResourceConfig
					.getText("func.nocmpactorcmpactnotbegin"));
			this.sendMsg(sms);
			return null;
		}
		if (act.isEnd()) {
			sms.setContent(ResourceConfig.getText("func.cmpact.expired"));
			this.sendMsg(sms);
			return null;
		}
		if (act.isOnlyMemberJoin()) {
			if (this.cmpActService.getCmpActUserByActIdAndUserId(actId, userId) == null) {
				sms.setContent(ResourceConfig
						.getText("view.cmpact.onlymemberjoin"));
				this.sendMsg(sms);
				return null;
			}
		}
		CmpActUser cmpActUser = new CmpActUser();
		cmpActUser.setActId(actId);
		cmpActUser.setUserId(userId);
		cmpActUser.setCompanyId(act.getCompanyId());
		if (act.isUserNeedCheck()) {
			cmpActUser.setCheckflg(CmpActUser.CHECKFLG_UNCHECKED);
		}
		else {
			cmpActUser.setCheckflg(CmpActUser.CHECKFLG_Y);
		}
		this.cmpActService.createCmpActUser(cmpActUser);
		if (act.isUserNeedCheck()) {
			sms.setContent(ResourceConfig
					.getText("func.cmpact.cmpactuseraddokandwaitcheck"));
		}
		else {
			sms.setContent(ResourceConfig
					.getText("func.cmpact.cmpactuseraddok"));
		}
		this.sendMsg(sms);
		return null;
	}
}