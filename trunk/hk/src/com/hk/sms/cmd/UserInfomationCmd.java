package com.hk.sms.cmd;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Act;
import com.hk.bean.ActUser;
import com.hk.bean.HkbLog;
import com.hk.bean.InfoSmsPort;
import com.hk.bean.Information;
import com.hk.bean.Laba;
import com.hk.bean.LabaTag;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.BatchSms;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.ActService;
import com.hk.svr.InfoSmsPortService;
import com.hk.svr.InformationService;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.web.util.HkWebConfig;

public class UserInfomationCmd extends BaseCmd {
	@Autowired
	private LabaService labaService;

	@Autowired
	private InfoSmsPortService infoSmsPortService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@Autowired
	private InformationService informationService;

	@Autowired
	private CreateLabaCmd createLabaCmd;

	@Autowired
	private ActService actService;

	private final Log log = LogFactory.getLog(UserInfomationCmd.class);

	@Override
	public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		UserOtherInfo userOtherInfo = this.getUserSmsMo(receivedSms)
				.getUserOtherInfo();
		if (userOtherInfo == null) {
			return null;
		}
		String smsport = receivedSms.getPort();
		String infoPort = smsport.replaceFirst(smsPortProcessAble
				.getBaseSmsPort(), "");
		InfoSmsPort smsPort = this.infoSmsPortService.getSmsPort(infoPort);
		if (smsPort == null) {// 空号码
			return null;
		}
		if (smsPort.isOver()) {
			return null;
		}
		if (!smsPort.isUse()) {// 未使用的号码
			return null;
		}
		if (smsPort.getUsetype() == InfoSmsPort.USETYPE_INFO) {
			this.processUserInfomation(receivedSms, smsPortProcessAble,
					userOtherInfo, smsPort);
		}
		else {
			this.processAct(receivedSms, userOtherInfo, smsPort);
		}
		return null;
	}

	public void processAct(ReceivedSms receivedSms,
			UserOtherInfo userOtherInfo, InfoSmsPort smsPort) {
		long actId = smsPort.getActId();
		Act act = this.actService.getAct(actId);
		if (act == null) {
			return;
		}
		String content = receivedSms.getContent();
		if (content.startsWith("群发")
				&& act.getUserId() == userOtherInfo.getUserId()) {
			content = content.substring(2, content.length());
			this.processSendSmsToActUser(actId, content, act, receivedSms,
					userOtherInfo);
		}
		// content = DataUtil.toHtmlRow(content);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setUserId(userOtherInfo.getUserId());
		labaInfo.setSendFrom(Laba.SENDFROM_SMS);
		long labaId = this.labaService.createLaba(labaInfo);// 创建喇叭
		// 自动外挂临时频道，增加者为活动创建者，这样主人可以随时维护
		Tag tag = this.tagService.createTag(act.getName());// 创建喇叭频道
		this.labaService.addTagForLaba(labaId, userOtherInfo.getUserId(), tag
				.getTagId(), LabaTag.ACCESSIONAL_Y);// 添加频道
	}

	public void processSendSmsToActUser(long actId, String content, Act act,
			ReceivedSms receivedSms, UserOtherInfo userOtherInfo) {
		List<ActUser> list = this.actService.getActUserList(actId);
		if (list.size() == 0) {
			return;
		}
		int count = list.size() - 1;// 除去已经上行短信
		int hkb = HkbConfig.getSendSms() * count;
		if (!this.userService.hasEnoughHkb(act.getUserId(), -hkb)) {
			int add = hkb - userOtherInfo.getHkb();
			Sms sms = new Sms();
			sms.setMobile(receivedSms.getMobile());
			sms.setLinkid(receivedSms.getLinkid());
			sms.setPort(receivedSms.getPort());
			sms.setContent(ResourceConfig.getText(
					"func.no_enough_hkb_sendgroupactsms", add));
			this.sendMsg(sms);
			return;
		}
		List<Long> idList = new ArrayList<Long>();
		for (ActUser o : list) {
			idList.add(o.getUserId());
		}
		List<UserOtherInfo> infolist = this.userService
				.getUserOtherInfoListInId(idList);
		List<String> mobileList = new ArrayList<String>();
		for (UserOtherInfo o : infolist) {
			if (act.getUserId() != o.getUserId()) {
				mobileList.add(o.getMobile());
			}
		}
		BatchSms batchSms = new BatchSms();
		batchSms.setContent(content);
		batchSms.setPort(receivedSms.getPort());
		batchSms.setMobile(mobileList);
		this.getSmsClient().sendBatchIgnoreError(batchSms);
		HkbLog hkbLog = HkbLog.create(act.getUserId(),
				HkLog.SEND_GROUP_ACT_SMS, actId, -hkb);
		this.userService.addHkb(hkbLog);
	}

	public void processUserInfomation(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble, UserOtherInfo userOtherInfo,
			InfoSmsPort smsPort) {
		log.info("begin userinformation");
		long userId = smsPort.getUserId();
		if (userId <= 0) {
			return;
		}
		try {
			String content = receivedSms.getContent();
			Information o = this.informationService
					.getInformationByPortId(smsPort.getPortId());
			if (o == null || !o.isRun()) {
				this.createLabaCmd.execute(receivedSms, smsPortProcessAble);
				return;
			}
			User user = this.userService.getUser(userId);
			if (o.getUserId() != userOtherInfo.getUserId()) {
				content = "@" + user.getNickName() + " " + content;// 自动设置为回应主人
			}
			else {
				// content = DataUtil.toHtmlRow(content);
			}
			LabaInPutParser parser = new LabaInPutParser(HkWebConfig
					.getShortUrlDomain());
			LabaInfo labaInfo = parser.parse(content);
			labaInfo.setUserId(userOtherInfo.getUserId());
			labaInfo.setSendFrom(Laba.SENDFROM_SMS);
			long labaId = this.labaService.createLaba(labaInfo);// 创建喇叭
			// 自动外挂临时频道，增加者为信息台主人，这样主人可以随时维护
			Tag tag = this.tagService.createTag(o.getTag());// 创建喇叭频道
			this.labaService.addTagForLaba(labaId, userId, tag.getTagId(),
					LabaTag.ACCESSIONAL_Y);// 添加频道
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}