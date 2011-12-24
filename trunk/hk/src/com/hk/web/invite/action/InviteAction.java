package com.hk.web.invite.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.ProUser;
import com.hk.bean.User;
import com.hk.bean.UserInviteConfig;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserTool;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.mail.MailUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.svr.CmpTipService;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.invite.exception.OutOfInviteLimitException;
import com.hk.svr.processor.InviteProcessor;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/invite/invite")
public class InviteAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private InviteProcessor inviteProcessor;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		SimplePage page = req.getSimplePage(size);
		List<Invite> list = this.inviteService.getSuccessList(userId, page
				.getBegin(), size);
		req.setAttribute("list", list);
		return "/WEB-INF/page/invite/success.jsp";
	}

	/**
	 * 生成邀请码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-4-28
	 */
	public String createinvitecode(HkRequest req, HkResponse resp)
			throws Exception {
		User loginUser = this.getLoginUser(req);
		this.inviteProcessor.createInviteCode(loginUser.getUserId());
		return "r:/invite/invite_toinvite.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toinvite(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		User ouser = this.userService.getUser(userId);
		req.setAttribute("ouser", ouser);
		UserInviteConfig userInviteConfig = this.inviteService
				.getUserInviteConfig(loginUser.getUserId());
		if (userInviteConfig == null) {
			userInviteConfig = new UserInviteConfig();
			userInviteConfig.setUserId(loginUser.getUserId());
			userInviteConfig.setInviteNum(5);
			userInviteConfig.setBatchNum(0);
		}
		List<InviteCode> invitecodelist = this.inviteService
				.getInviteCodeListByUserIdAndUseflg(loginUser.getUserId(),
						InviteCode.USEFLG_N, 0, 10);
		req.setAttribute("invitecodelist", invitecodelist);
		if (userInviteConfig.getInviteNum() == 0 && invitecodelist.size() == 0) {
			userInviteConfig.setBatchNum(userInviteConfig.getBatchNum() + 1);
			// 行动总数
			int doneCount = this.cmpTipService.countUserCmpTipDone(loginUser
					.getUserId());
			if (doneCount >= userInviteConfig.getBatchNum() * 3) {
				userInviteConfig.setInviteNum(5);
				this.inviteService.saveUserInviteConfig(userInviteConfig);
			}
		}
		req.setAttribute("userInviteConfig", userInviteConfig);
		return "/WEB-INF/page/invite/invite.jsp";
	}

	/**
	 * 特殊邀请，进入红地毯
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toproinvite(HkRequest req, HkResponse resp) {
		return this.processToproinvite(req, "invite/proinvite.jsp");
	}

	private String processToproinvite(HkRequest req, String path) {
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.getUserTool(loginUser.getUserId());
		if (userTool != null && userTool.getInviteCount() <= 0) {
			return null;
		}
		return this.getWapJsp(path);
	}

	/**
	 * 高级邀请，可以形成星光大道,查看email是否已经存在
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addproinvite(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getInviteCount() <= 0) {
			req.setSessionText("func.usertool.noenough_invitecount");
			return "r:/invite/invite_toinvite.do";
		}
		String input = req.getString("input");
		String nickName = req.getString("nickName");
		String intro = req.getString("intro");
		ProUser o = new ProUser();
		o.setInput(input);
		o.setIntro(intro);
		o.setNickName(nickName);
		o.setCreaterId(loginUser.getUserId());
		req.setAttribute("o", o);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/invite/invite_toproinvite.do";
		}
		// 查看是否已经注册
		UserOtherInfo info = this.userService.getUserOtherInfoByeEmail(o
				.getInput());
		if (info != null) {
			User user = this.userService.getUser(info.getUserId());
			req.setAttribute("user", user);
			return "/invite/invite_toproinvite.do";
		}
		if (this.userService.createProUser(o)) {
			try {
				long inviteId = this.inviteService.createInvite(loginUser
						.getUserId(), input, Invite.INVITETYPE_EMAIL);
				this.processSendInviteMail(req, inviteId, loginUser
						.getNickName(), input, o.getOid());
				userTool.setInviteCount(userTool.getInviteCount() - 1);
				this.userService.updateuserTool(userTool);
			}
			catch (OutOfInviteLimitException e) {
				req.setSessionMessage(req
						.getText("func.mail.invite.mail.sendfail_limit"));
				return "r:/invite/invite_toproinvite.do";
			}
			req.setSessionText("func.invite.create_ok");// 创建成功
			return "r:/prouser.do?prouserId=" + o.getOid();
		}
		req.setText("func.invite.proinvite_already_exist");// 已经有人邀请过此人，不能再次邀请
		return "/invite/invite_toproinvite.do";
	}

	/**
	 * 高级邀请，可以形成星光大道
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addproinvite2(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getInviteCount() <= 0) {
			req.setSessionText("func.usertool.noenough_invitecount");
			return "r:/invite/invite_toinvite.do";
		}
		long userId = req.getLong("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		ProUser o = new ProUser();
		o.setIntro(info.getIntro());
		o.setNickName(user.getNickName());
		o.setCreaterId(loginUser.getUserId());
		o.setUserId(userId);
		req.setAttribute("o", o);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			req.setText(code + "");
			return "/invite/invite_toproinvite.do";
		}
		if (this.userService.createProUser(o)) {
			userTool.setInviteCount(userTool.getInviteCount() - 1);
			this.userService.updateuserTool(userTool);
			req.setSessionText("func.redcarpet.create_ok");// 创建成功
			return "r:/prouser.do?prouserId=" + o.getOid();
		}
		req.setText("func.invite.proinvite_already_exist");// 已经有人邀请过此人，不能再次邀请
		return "/invite/invite_toproinvite.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toSendInviteSms(HkRequest req, HkResponse resp) {
		long userId = this.getLoginUser(req).getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (info.getMobileBind() == UserOtherInfo.MOBILE_NOT_BIND) {
			req.setSessionMessage("您还没有认证手机号,只有认证手机号的用户才可以使用短信邀请");
			return "r:/";
		}
		return "/WEB-INF/page/invite/smsinvite.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sendInviteSms(HkRequest req, HkResponse resp) {
		User user = this.getLoginUser(req);
		long userId = user.getUserId();
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		if (info.getMobileBind() == UserOtherInfo.MOBILE_NOT_BIND) {
			req.setSessionMessage("您还没有认证手机号,只有认证手机号的用户才可以使用短信邀请");
			return "r:/";
		}
		try {
			this.smsClient.send(info.getMobile(), req.getText(
					"invite.sms_invtecontent", user.getNickName()));
			req.setSessionMessage("短信已经发送到您的手机上,请注意查收");
		}
		catch (Exception e) {
			req.setSessionMessage("短信发送失败");
			return "r:/invite/invite_toinvite.do";
		}
		return "/WEB-INF/page/invite/smssendok.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String finduser(HkRequest req, HkResponse resp) {
		String nickName = req.getString("nickName");
		if (!DataUtil.isEmpty(nickName)) {
			User user = this.userService.getUserByNickName(nickName);
			req.setAttribute("user2", user);
		}
		req.setAttribute("nickName", nickName);
		return this.processToproinvite(req, "invite/proinvite2.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String all(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		SimplePage page = req.getSimplePage(size);
		List<Invite> list = this.inviteService.getInviteList(userId, page
				.getBegin(), size);
		req.setAttribute("list", list);
		return "/WEB-INF/page/invite/all.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String create(HkRequest req, HkResponse resp) {
		String email = req.getString("email");
		if (!DataUtil.isLegalEmail(email)) {
			req.setMessage(req.getText("func.inputmail"));
			return "/invite/invite_toinvite.do";
		}
		UserOtherInfo info = this.userService.getUserOtherInfoByeEmail(email);
		if (info != null) {
			User user = this.userService.getUser(info.getUserId());
			req.setAttribute("user", user);
			return "/invite/invite_toinvite.do";
		}
		try {
			User loginUser = this.getLoginUser(req);
			User ouser = this.userService.getUser(loginUser.getUserId());
			this.inviteService.createInvite(loginUser.getUserId(), email,
					Invite.INVITETYPE_EMAIL);
			this.processSendInviteMail(req, ouser, email);
		}
		catch (OutOfInviteLimitException e) {
			req.setSessionMessage(req
					.getText("func.mail.invite.mail.sendfail_limit"));
			return "r:/invite/invite_toinvite.do";
		}
		req.setSessionMessage(req.getText("func.mail.invite.mail.sendok"));
		return "r:/invite/invite_toinvite.do";
	}

	private void processSendInviteMail(HkRequest req, User user, String email) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String content = null;
		content = req.getText("func.mail.invite.mail.content2", user
				.getNickName(), user.getDomain(), date);
		try {
			mailUtil
					.sendHtmlMail(email, req.getText(
							"func.mail.invite.mail.title", user.getNickName()),
							content);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void processSendInviteMail(HkRequest req, long inviteId,
			String nickName, String email, long prouserId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		String content = null;
		if (prouserId > 0) {
			content = req.getText("func.mail.invite.mail.content_with_prouser",
					nickName, inviteId + "", date, prouserId + "");
		}
		else {
			content = req.getText("func.mail.invite.mail.content", nickName,
					inviteId + "", date);
		}
		try {
			mailUtil.sendHtmlMail(email, req.getText(
					"func.mail.invite.mail.title", nickName), content);
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String resend(HkRequest req, HkResponse resp) {
		String burl = "/invite/invite_all.do?page=" + req.getInt("repage");
		long inviteId = req.getLong("inviteId");
		Invite o = this.inviteService.getInvite(inviteId);
		if (o != null && o.getFriendId() == 0) {
			String email = o.getEmail();
			UserOtherInfo info = this.userService
					.getUserOtherInfoByeEmail(email);
			if (info != null) {
				User user = this.userService.getUser(info.getUserId());
				req.setAttribute("user", user);
				return burl;
			}
			try {
				User loginUser = this.getLoginUser(req);
				User ouser = this.userService.getUser(loginUser.getUserId());
				this.inviteService.createInvite(loginUser.getUserId(), email,
						Invite.INVITETYPE_EMAIL);
				this.processSendInviteMail(req, ouser, email);
			}
			catch (OutOfInviteLimitException e) {
				req.setSessionMessage("每天只能对同一个E-mail发送3次邀请");
				return "r:" + burl;
			}
			req.setSessionMessage("邀请已经成功发送");
			return "r:" + burl;
		}
		return burl;
	}
}