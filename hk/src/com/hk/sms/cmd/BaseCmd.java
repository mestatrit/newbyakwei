package com.hk.sms.cmd;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpSmsPort;
import com.hk.bean.CmpWatch;
import com.hk.bean.Company;
import com.hk.bean.DefFollowUser;
import com.hk.bean.HkbLog;
import com.hk.bean.Invite;
import com.hk.bean.ScoreLog;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSmsPort;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.sms.ReceivedSms;
import com.hk.sms.Sms;
import com.hk.sms.SmsClient;
import com.hk.sms2.SmsPortProcessAble;
import com.hk.svr.CmpSmsPortService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkLog;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ScoreConfig;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;

public abstract class BaseCmd {

	private CmdConfig cmdConfig;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private CmpSmsPortService cmpSmsPortService;

	@Autowired
	private CompanyService companyService;

	public CmdConfig getCmdConfig() {
		return cmdConfig;
	}

	public void setCmdConfig(CmdConfig cmdConfig) {
		this.cmdConfig = cmdConfig;
	}

	abstract public String execute(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) throws Exception;

	public SmsClient getSmsClient() {
		return smsClient;
	}

	private final Log log = LogFactory.getLog(BaseCmd.class);

	public void sendMsg(Sms sms) {
		try {
			this.smsClient.send(sms);
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public UserSmsMo getUserSmsMo(ReceivedSms receivedSms) {
		UserSmsMo userSmsMo = new UserSmsMo();
		UserOtherInfo userOtherInfo = this.userService
				.getUserOtherInfoByMobile(receivedSms.getMobile());
		if (userOtherInfo == null) {
			userSmsMo.setNewUser(true);
			String mobile = receivedSms.getMobile();
			String password = DataUtil.getRandom(4);
			try {
				long userId = this.userService.createUser(mobile, password,
						null);
				// 添加默认关注
				List<DefFollowUser> list = this.userService
						.getDefFollowUserList(0, 20);
				for (DefFollowUser o : list) {
					try {
						this.followService.addFollow(userId, o.getUserId(),
								null, false);
					}
					catch (AlreadyBlockException e) {
						// TODO Auto-generated catch block
					}
				}
				userOtherInfo = this.userService.getUserOtherInfo(userId);
				this.sendTipNewReg(receivedSms, password);
			}
			catch (EmailDuplicateException e) {
				return null;
			}
			catch (MobileDuplicateException e) {
				return null;
			}
		}
		// 查看用户是否绑定了手机号，如果没有绑定，就设为绑定
		if (!userOtherInfo.isMobileAlreadyBind()) {
			userOtherInfo.setMobileBind(UserOtherInfo.MOBILE_BIND);
			this.userService.updateUserOtherInfo(userOtherInfo);
		}
		// 如果没有短信号码，就分配一个号码
		UserSmsPort port = this.userSmsPortService
				.getUserSmsPortByUserId(userOtherInfo.getUserId());
		if (port == null) {
			this.userSmsPortService.makeAvailableUserSmsPort(userOtherInfo
					.getUserId());
		}
		userSmsMo.setUserOtherInfo(userOtherInfo);
		// 绑定成功后,查询是否被邀请进入,给邀请者积分和火酷币
		long userId = userOtherInfo.getUserId();
		List<Invite> list = this.inviteService
				.getSuccessInviteListByFriendId(userId);
		for (Invite i : list) {
			if (i.getAddhkbflg() == Invite.ADDHKBFLG_Y) {
				continue;
			}
			HkbLog hkbLog = HkbLog.create(i.getUserId(), HkLog.INVITE, userId,
					HkbConfig.getInvite());
			this.userService.addHkb(hkbLog);// 增加火酷币
			ScoreLog scoreLog = ScoreLog.create(i.getUserId(), HkLog.INVITE,
					userId, ScoreConfig.getInvite());
			this.userService.addScore(scoreLog);// 增加积分
			i.setAddhkbflg(Invite.ADDHKBFLG_Y);
			this.inviteService.updateInvite(i);
		}
		return userSmsMo;
	}

	protected void sendTipNewReg(ReceivedSms receivedSms, String password) {
		Sms sms = new Sms();
		sms.setContent(ResourceConfig.getText("sms.reg_ok", password));
		sms.setLinkid(receivedSms.getLinkid());
		sms.setMobile(receivedSms.getMobile());
		sms.setPort("1");
		this.sendMsg(sms);
	}

	protected String getObjPort(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		String port = receivedSms.getPort();
		port = port.replaceFirst(smsPortProcessAble.getBaseSmsPort(), "");
		return port;
	}

	protected CmpSmsPort getCmpSmsPort(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		CmpSmsPort cmpSmsPort = this.cmpSmsPortService.getCmpSmsPortByPort(this
				.getObjPort(receivedSms, smsPortProcessAble));
		return cmpSmsPort;
	}

	protected void processUpdateCompany(ReceivedSms receivedSms, Company company) {
		log.info("process update company ... ... ...");
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setPort(receivedSms.getPort());
		sms.setLinkid(receivedSms.getLinkid());
		String s = ResourceConfig.getText("func.sms.company.mgr.menu");
		s = DataUtil.toText(s);
		UserOtherInfo info = this.userService
				.getUserOtherInfoByMobile(receivedSms.getMobile());
		if (info == null) {// 用户不存在
			sms.setContent(ResourceConfig
					.getText("func.sms.company.mgr.nouser"));
			this.sendMsg(sms);
			return;
		}
		CmpWatch cmpWatch = this.companyService.getCmpWatch(company
				.getCompanyId(), info.getUserId());
		if (cmpWatch == null) {
			sms.setContent(ResourceConfig
					.getText("func.sms.company.mgr.nocmpwatch")
					+ "\n" + s);
			this.sendMsg(sms);
			return;
		}
		int code = company.validate(true);
		if (code != Err.SUCCESS) {// 验证不通过
			sms.setContent(ResourceConfig.getText(code + ""));
		}
		else {
			this.companyService.updateCompany(company);
			sms
					.setContent(ResourceConfig.getText("op.submitinfook")
							+ "\n" + s);
		}
		this.sendMsg(sms);
	}

	protected Company getCompany(ReceivedSms receivedSms,
			SmsPortProcessAble smsPortProcessAble) {
		CmpSmsPort cmpSmsPort = this.getCmpSmsPort(receivedSms,
				smsPortProcessAble);
		if (cmpSmsPort == null) {// 不存在的足迹号码
			return null;
		}
		Company company = this.companyService.getCompany(cmpSmsPort
				.getCompanyId());
		if (company == null) {
			log.warn("no company [ " + cmpSmsPort.getCompanyId() + " ]");
			return null;
		}
		return company;
	}

	protected Sms createReceiveSms(ReceivedSms receivedSms) {
		Sms sms = new Sms();
		sms.setMobile(receivedSms.getMobile());
		sms.setLinkid(receivedSms.getLinkid());
		sms.setPort(receivedSms.getPort());
		return sms;
	}
}