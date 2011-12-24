package com.hk.web.hk4.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.BlockUser;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.Company;
import com.hk.bean.Laba;
import com.hk.bean.Mayor;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.bean.UserCmpTip;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserUpdate;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BadgeService;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpTipService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.LabaService;
import com.hk.svr.UserService;
import com.hk.web.hk4.venue.action.CmpTipVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/h4/user")
public class UserAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private CmpTipService cmpTipService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	/**
	 * pc访问页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String me(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {
			return "r:/login";
		}
		return "r:/user/" + loginUser.getUserId();
	}

	public String execute(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		return this.execute(req, userId);
	}

	public String execute(HkRequest req, long userId) {
		req.setAttribute("to_myhome", true);
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		req.setAttribute("userOtherInfo", userOtherInfo);
		req.setAttribute("user", user);
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (loginUser.getUserId() != userId) {
				req.setAttribute("me", false);
				if (this.followService.getFollow(userId, loginUser.getUserId()) != null) {
					req.setAttribute("followed", true);
				}
				if (this.followService.getFollow(loginUser.getUserId(), userId) != null) {
					req.setAttribute("hasfriend", true);
				}
				else {
					BlockUser blockUser = this.followService.getBlockUser(
							userId, loginUser.getUserId());
					if (blockUser != null) {// 当前登录用户被阻止
						req.setAttribute("blocked", true);
					}
					else {
						BlockUser blockUser2 = this.followService.getBlockUser(
								loginUser.getUserId(), userId);
						if (blockUser2 != null) {// 阻止了对方
							req.setAttribute("blockedUser", true);
						}
					}
				}
			}
			else {
				req.setAttribute("me", true);
			}
		}
		// 好友，排序按照最新活动时间排列
		List<Long> friendIdList = this.followService
				.getFollowFriendIdListByUserId(userId);
		List<UserUpdate> uulsit = this.userService.getUserUpdateListInIdList(
				friendIdList, 0, 40);
		List<User> frienduserlist = new ArrayList<User>();
		List<Long> idList = new ArrayList<Long>();
		for (UserUpdate userUpdate : uulsit) {
			idList.add(userUpdate.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (UserUpdate userUpdate : uulsit) {
			frienduserlist.add(map.get(userUpdate.getUserId()));
		}
		req.setAttribute("frienduserlist", frienduserlist);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<UserCmpTip> usercmptipdonelist = this.cmpTipService
				.getUserCmpTipDoneListByUserId(userId, 0, 13);
		if (usercmptipdonelist.size() == 13) {
			req.setAttribute("more_done", true);
			usercmptipdonelist.remove(12);
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList2(
				usercmptipdonelist, loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		// 有效报到总数
		int checkInCount = this.cmpCheckInService
				.countEffectCmpCheckInUserLogByUserId(userId);
		// 夜晚报到总数
		int nightCheckInCount = this.cmpCheckInService
				.countCmpCheckInUserLogByUserIdForNight(userId);
		// 总共完成的事情
		int doneCount = this.cmpTipService.countUserCmpTipDone(userId);
		req.setAttribute("checkInCount", checkInCount);
		req.setAttribute("nightCheckInCount", nightCheckInCount);
		req.setAttribute("doneCount", doneCount);
		// 历史报到数据
		List<CmpCheckInUserLog> checkinloglist = this.cmpCheckInService
				.getCmpCheckInUserLogListByUserId(userId, 0, 6);
		idList = new ArrayList<Long>();
		for (CmpCheckInUserLog o : checkinloglist) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		for (CmpCheckInUserLog o : checkinloglist) {
			o.setCompany(cmpmap.get(o.getCompanyId()));
		}
		req.setAttribute("checkinloglist", checkinloglist);
		// 用户徽章
		List<UserBadge> userbadgelist = this.badgeService
				.getUserBadgeListByUserId(userId, 0, 15);
		if (userbadgelist.size() > 0) {
			req.setAttribute("firstbadge", userbadgelist.get(0).getOid());
		}
		// 徽章数量
		int hadge_count = this.badgeService.countUserBadgeByUerId(userId);
		req.setAttribute("hadge_count", hadge_count);
		req.setAttribute("userbadgelist", userbadgelist);
		// 用户地主的地方
		List<Mayor> mayorlist = this.cmpCheckInService.getMayorListByUserId(
				userId, 0, 6);
		idList.clear();
		for (Mayor o : mayorlist) {
			idList.add(o.getCompanyId());
		}
		cmpmap = this.companyService.getCompanyMapInId(idList);
		for (Mayor o : mayorlist) {
			o.setCompany(cmpmap.get(o.getCompanyId()));
		}
		req.setAttribute("mayorlist", mayorlist);
		return this.getWeb4Jsp("user/user.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String badge(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		UserBadge b = this.badgeService.getUserBadge(oid);
		if (b == null) {
			return null;
		}
		req.setAttribute("b", b);
		User user = this.userService.getUser(b.getUserId());
		req.setAttribute("user", user);
		if (b.getCompanyId() > 0) {
			Company company = this.companyService.getCompany(b.getCompanyId());
			req.setAttribute("company", company);
		}
		List<UserBadge> userbadgelist = this.badgeService
				.getUserBadgeListByUserId(b.getUserId(), 0, 0);
		req.setAttribute("userbadgelist", userbadgelist);
		return this.getWeb4Jsp("badge/badge.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String findbadge(HkRequest req, HkResponse resp) {
		long badgeId = req.getLong("badgeId");
		long userId = req.getLong("userId");
		UserBadge userBadge = this.badgeService.getUserBadge(userId, badgeId);
		if (userBadge != null) {
			return "r:/userbadge/" + userBadge.getOid();
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String laba(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		List<Laba> list = this.labaService.getLabaListByUserId(userId, 0, 13);
		if (list.size() == 13) {
			req.setAttribute("more_laba", true);
			list.remove(12);
		}
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb4(req));
		req.setAttribute("labavolist", labavolist);
		return this.getWeb4Jsp("inc/laba_user_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String dolist(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User loginUser = this.getLoginUser(req);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<UserCmpTip> usercmptipdonelist = this.cmpTipService
				.getUserCmpTipDoneListByUserId(userId, 0, 13);
		if (usercmptipdonelist.size() == 13) {
			req.setAttribute("more_done", true);
			usercmptipdonelist.remove(12);
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList2(
				usercmptipdonelist, loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		req.setAttribute("user_view", true);
		return this.getWeb4Jsp("inc/todo_do_user_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String donelist(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		User loginUser = this.getLoginUser(req);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		SimplePage page = req.getSimplePage(12);
		List<UserCmpTip> list = this.cmpTipService
				.getUserCmpTipDoneListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList2(list, loginUserId,
				true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWeb4Jsp("user/donelist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String todolist2(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		User loginUser = this.getLoginUser(req);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		SimplePage page = req.getSimplePage(12);
		List<UserCmpTip> list = this.cmpTipService
				.getUserCmpTipToDoListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList2(list, loginUserId,
				true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		return this.getWeb4Jsp("user/todolist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String todolist(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User loginUser = this.getLoginUser(req);
		long loginUserId = 0;
		if (loginUser != null) {
			loginUserId = loginUser.getUserId();
		}
		List<UserCmpTip> usercmptiptodolist = this.cmpTipService
				.getUserCmpTipToDoListByUserId(userId, 0, 13);
		if (usercmptiptodolist.size() == 13) {
			req.setAttribute("more_todo", true);
			usercmptiptodolist.remove(12);
		}
		List<CmpTipVo> cmptipvolist = CmpTipVo.createVoList2(
				usercmptiptodolist, loginUserId, true);
		req.setAttribute("cmptipvolist", cmptipvolist);
		req.setAttribute("todolist", 1);
		return this.getWeb4Jsp("inc/todo_do_user_inc.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String n(HkRequest req, HkResponse resp) {
		String v = req.getString("v");
		User user = this.userService.getUserByNickName(v);
		if (user != null) {
			req.setAttribute("userId", user.getUserId());
			return this.execute(req, user.getUserId());
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String friend(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		// 好友，排序按照最新活动时间排列
		List<Long> friendIdList = this.followService
				.getFollowFriendIdListByUserId(userId);
		List<UserUpdate> list = this.userService.getUserUpdateListInIdList(
				friendIdList, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<User> frienduserlist = new ArrayList<User>();
		List<Long> idList = new ArrayList<Long>();
		for (UserUpdate userUpdate : list) {
			idList.add(userUpdate.getUserId());
		}
		Map<Long, User> map = this.userService.getUserMapInId(idList);
		for (UserUpdate userUpdate : list) {
			frienduserlist.add(map.get(userUpdate.getUserId()));
		}
		// req.setAttribute("frienduserlist", frienduserlist);
		UserVoBuilder userVoBuilder = new UserVoBuilder();
		userVoBuilder.setNeedFansCount(false);
		userVoBuilder.setNeedCheckFollowMe(false);
		userVoBuilder.setNeedFriend(false);
		userVoBuilder.setNeedLaba(true);
		userVoBuilder.setLabaParserCfg(this.getLabaParserCfgWeb4(req));
		userVoBuilder.setLoginUser(this.getLoginUser(req));
		List<UserVo> uservolist = UserVo.create(frienduserlist, userVoBuilder);
		req.setAttribute("uservolist", uservolist);
		return this.getWeb4Jsp("user/friend.jsp");
	}

	// /**
	// * 通过domain访问，用户与可自定义domain
	// *
	// * @param req
	// * @param resp
	// * @return
	// */
	// public String domain(HkRequest req, HkResponse resp) {
	// String domain = req.getString("domain");
	// if (DataUtil.isEmpty(domain)) {
	// return null;
	// }
	// User user = this.userService.getUserByDomain(domain);
	// if (user == null) {
	// return null;
	// }
	// return this.execute(req, user.getUserId());
	// }
	/**
	 * 粉丝列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String fans(HkRequest req, HkResponse resp) {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		// 好友，排序按照最新活动时间排列
		List<Long> fansIdList = this.followService.getFansIdListByUserId(
				userId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, fansIdList);
		Map<Long, User> map = this.userService.getUserMapInId(fansIdList);
		List<User> fanslist = new ArrayList<User>();
		for (Long l : fansIdList) {
			fanslist.add(map.get(l));
		}
		UserVoBuilder userVoBuilder = new UserVoBuilder();
		userVoBuilder.setNeedFansCount(false);
		userVoBuilder.setNeedCheckFollowMe(false);
		userVoBuilder.setNeedFriend(false);
		userVoBuilder.setNeedLaba(true);
		userVoBuilder.setLabaParserCfg(this.getLabaParserCfgWeb4(req));
		userVoBuilder.setLoginUser(this.getLoginUser(req));
		List<UserVo> uservolist = UserVo.create(fanslist, userVoBuilder);
		req.setAttribute("uservolist", uservolist);
		return this.getWeb4Jsp("user/fans.jsp");
	}
}