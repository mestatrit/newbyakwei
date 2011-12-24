package com.hk.web.hk4.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.User;
import com.hk.bean.UserInviteConfig;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpTipService;
import com.hk.svr.InviteService;
import com.hk.svr.UserService;
import com.hk.svr.processor.InviteProcessor;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/invite")
public class InviteAction extends BaseAction {

	@Autowired
	private InviteService inviteService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private InviteProcessor inviteProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		User user = this.userService.getUser(loginUser.getUserId());
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(10);
		List<Invite> list = this.inviteService.getSuccessList(loginUser
				.getUserId(), page.getBegin(), page.getSize() + 1);
		List<Long> idList = new ArrayList<Long>();
		for (Invite o : list) {
			if (o.getFriendId() > 0) {
				idList.add(o.getFriendId());
			}
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (Invite o : list) {
			o.setFriend(map.get(o.getFriendId()));
		}
		this.processListForPage(page, list);
		req.setAttribute("list", list);
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
		return this.getWeb4Jsp("invite/list.jsp");
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
		return "r:/invite";
	}
}