package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.BlockUser;
import com.hk.bean.City;
import com.hk.bean.CmpCheckInUserLog;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductFav;
import com.hk.bean.CmpProductReview;
import com.hk.bean.Company;
import com.hk.bean.CompanyUserStatus;
import com.hk.bean.Feed;
import com.hk.bean.Follow;
import com.hk.bean.HkGroup;
import com.hk.bean.Information;
import com.hk.bean.Laba;
import com.hk.bean.Mayor;
import com.hk.bean.ProUser;
import com.hk.bean.Province;
import com.hk.bean.RecentVisitor;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.bean.UserCmpProductReview;
import com.hk.bean.UserCmpReview;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserSmsPort;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.SmsClient;
import com.hk.svr.BadgeService;
import com.hk.svr.CmpCheckInService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CompanyService;
import com.hk.svr.FollowService;
import com.hk.svr.GroupService;
import com.hk.svr.InformationService;
import com.hk.svr.LabaService;
import com.hk.svr.RecentVisitorService;
import com.hk.svr.UserService;
import com.hk.svr.UserSmsPortService;
import com.hk.svr.impl.FeedServiceWrapper;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.company.action.CmpProductReviewVo;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebUtil;

@Component("/home")
public class HomeAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private InformationService informationService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserSmsPortService userSmsPortService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private SmsClient smsClient;

	@Autowired
	private RecentVisitorService recentVisitorService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private CmpCheckInService cmpCheckInService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		req.setAttribute("user", user);
		req.setAttribute("canSms", this.isCanSms(req, userId));
		UserSmsPort userSmsPort = this.userSmsPortService
				.getUserSmsPortByUserId(userId);
		if (userSmsPort != null) {
			String userport = this.smsClient.getSmsConfig().getSpNumber()
					+ HkWebUtil.getUserPort(userId);
			req.setAttribute("userport", userport);
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser != null) {
			if (userId != loginUser.getUserId()) {
				if (this.followService.getFollow(userId, loginUser.getUserId()) != null) {
					req.setAttribute("followed", true);
				}
			}
		}
		// 历史报到数据
		List<CmpCheckInUserLog> checkinloglist = this.cmpCheckInService
				.getCmpCheckInUserLogListByUserId(userId, 0, 21);
		if (checkinloglist.size() == 21) {
			req.setAttribute("more_log", true);
			checkinloglist.remove(20);
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpCheckInUserLog o : checkinloglist) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		for (CmpCheckInUserLog o : checkinloglist) {
			o.setCompany(cmpmap.get(o.getCompanyId()));
		}
		// 徽章数量
		int hadge_count = this.badgeService.countUserBadgeByUerId(userId);
		int mayor_count = this.cmpCheckInService.countMayorByUserId(userId);
		req.setAttribute("hadge_count", hadge_count);
		req.setAttribute("mayor_count", mayor_count);
		req.setAttribute("checkinloglist", checkinloglist);
		// 关注信息
		boolean me = false;
		boolean blocked = false;// 是否被user阻止
		boolean areFriend = false;// 是否是关注user
		boolean blockedUser = false;// 是否阻止user
		boolean followed = false;// 是否被关注
		if (loginUser != null) {
			if (loginUser.getUserId() == userId) {
				me = true;
			}
			else {
				Follow friend = this.followService.getFollow(loginUser
						.getUserId(), userId);
				if (friend == null) {
					BlockUser blockUser = this.followService.getBlockUser(
							userId, loginUser.getUserId());
					if (blockUser != null) {
						blocked = true;
					}
					else {
						BlockUser blockUser2 = this.followService.getBlockUser(
								loginUser.getUserId(), userId);
						if (blockUser2 != null) {
							blockedUser = true;
						}
					}
				}
				else {
					areFriend = true;
					if (friend.getBothFollow() == Follow.FOLLOW_BOOTH) {
						followed = true;
					}
				}
				if (!followed) {
					if (this.followService.getFollow(userId, loginUser
							.getUserId()) != null) {
						followed = true;
					}
				}
			}
		}
		req.setAttribute("followed", followed);
		req.setAttribute("blockedUser", blockedUser);
		req.setAttribute("blocked", blocked);
		req.setAttribute("areFriend", areFriend);
		req.setAttribute("me", me);
		req.setAttribute("user", user);
		req.setAttribute("home", true);
		return this.getWapJsp("user/home.jsp");
	}

	/**
	 * 踪迹更多
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String checkincmp(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<CmpCheckInUserLog> list = this.cmpCheckInService
				.getCmpCheckInUserLogListByUserId(userId, page.getBegin(), page
						.getSize() + 1);
		this.processListForPage(page, list);
		List<Long> idList = new ArrayList<Long>();
		for (CmpCheckInUserLog o : list) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		for (CmpCheckInUserLog o : list) {
			o.setCompany(cmpmap.get(o.getCompanyId()));
		}
		req.setAttribute("list", list);
		return this.getWapJsp("user/checkincmp.jsp");
	}

	/**
	 * pc访问页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String my(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {
			return "r:/login";
		}
		return "r:/home_web.do?userId=" + loginUser.getUserId();
	}

	/**
	 * pc访问页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		if (loginUser != null && userId <= 0) {
			userId = loginUser.getUserId();
		}
		else if (loginUser == null && userId <= 0) {
			return null;
		}
		req.setAttribute("userId", userId);
		if (loginUser != null) {
			Follow follow = this.followService.getFollow(loginUser.getUserId(),
					userId);
			if (follow != null) {// 是好友
				req.setAttribute("areFriend", true);
				if (follow.isAllFollow()) {// 被此用户关注了
					req.setAttribute("followedByUser", true);
				}
			}
			else {
				BlockUser blockUser = this.followService.getBlockUser(loginUser
						.getUserId(), userId);
				if (blockUser != null) {// 阻止了此用户
					req.setAttribute("blockedUser", true);
				}
				blockUser = this.followService.getBlockUser(userId, loginUser
						.getUserId());
				if (blockUser != null) {// 被此用户阻止
					req.setAttribute("blockedByUser", true);
				}
			}
		}
		User user = this.userService.getUser(userId);
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		req.setAttribute("userOtherInfo", userOtherInfo);
		req.setAttribute("user", user);
		// 足迹
		List<CompanyUserStatus> companyUserStatusList = this.companyService
				.getCompanyUserStatusListByUserIdAndUserStatus(userId,
						CompanyUserStatus.USERSTATUS_DONE, 0, 5);
		if (companyUserStatusList.size() == 5) {
			req.setAttribute("more_cmplist", true);
			companyUserStatusList.remove(4);
		}
		List<Long> idList = new ArrayList<Long>();
		for (CompanyUserStatus status : companyUserStatusList) {
			idList.add(status.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		List<Company> cmplist = new ArrayList<Company>();
		for (CompanyUserStatus status : companyUserStatusList) {
			cmplist.add(cmpmap.get(status.getCompanyId()));
		}
		req.setAttribute("cmplist", cmplist);
		// 最新评论的足迹
		List<UserCmpReview> usercmpreivewlist = this.companyService
				.getUserCmpReviewList(userId, 0, 5);
		if (usercmpreivewlist.size() == 5) {
			req.setAttribute("more_usercmpreivewlist", true);
			usercmpreivewlist.remove(4);
		}
		List<UserCmpReviewVo> usercmpreviewvolist = UserCmpReviewVo.makeList(
				usercmpreivewlist, req);
		req.setAttribute("usercmpreviewvolist", usercmpreviewvolist);
		// 最新10条喇叭
		List<Laba> list = labaService.getLabaListByUserId(userId, 0, 11);
		if (list.size() == 11) {
			req.setAttribute("more_laba", true);
			list.remove(10);
		}
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getLabaParserCfgWeb(req));
		req.setAttribute("labavolist", labavolist);
		// 好友
		List<Follow> followlist = this.followService.getFollowList(userId, 0,
				10);
		if (followlist.size() == 10) {
			req.setAttribute("more_frienduserlist", true);
			followlist.remove(9);
		}
		List<Long> userIdList = new ArrayList<Long>();
		for (Follow f : followlist) {
			userIdList.add(f.getFriendId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(userIdList);
		List<User> frienduserlist = new ArrayList<User>();
		for (Follow f : followlist) {
			frienduserlist.add(usermap.get(f.getFriendId()));
		}
		req.setAttribute("frienduserlist", frienduserlist);
		// 用户动态
		FeedServiceWrapper feedServiceWrapper = new FeedServiceWrapper();
		List<Feed> feedlist = feedServiceWrapper.getUserFeedList(userId, 0, 15);
		if (feedlist.size() == 15) {
			req.setAttribute("more_feedlist", true);
			feedlist.remove(14);
		}
		List<FeedVo> feedvolist = FeedVo.createList(req, feedlist, true, false);
		req.setAttribute("feedvolist", feedvolist);
		// 用户简单介绍
		makeIntro(userOtherInfo, user, req);
		// 最近访问操作
		List<RecentVisitor> visitorlist = this.recentVisitorService
				.getVisitorByUserId(userId, 9);
		idList = new ArrayList<Long>();
		for (RecentVisitor visitor : visitorlist) {
			idList.add(visitor.getVisitorId());
		}
		Map<Long, User> visitormap = this.userService.getUserMapInId(idList);
		List<User> visitoruserlist = new ArrayList<User>();
		for (RecentVisitor visitor : visitorlist) {
			visitoruserlist.add(visitormap.get(visitor.getVisitorId()));
		}
		req.setAttribute("visitoruserlist", visitoruserlist);
		if (loginUser != null && loginUser.getUserId() != userId) {
			this.recentVisitorService.addVisitor(userId, loginUser.getUserId(),
					9);
		}
		// 产品的评论
		List<UserCmpProductReview> usercmpproductreviewlist = this.cmpProductService
				.getUserCmpProductReviewList(userId, 0, 5);
		idList = new ArrayList<Long>();
		for (UserCmpProductReview review : usercmpproductreviewlist) {
			idList.add(review.getLabaId());
		}
		List<CmpProductReview> cmpproductreviewlist = this.cmpProductService
				.getCmpProductReviewListInId(idList);
		List<CmpProductReviewVo> cmpproductreviewvolist = CmpProductReviewVo
				.createVoList(cmpproductreviewlist, this.getUrlInfoWeb(req),
						false, true);
		req.setAttribute("cmpproductreviewvolist", cmpproductreviewvolist);
		// 收藏的产品
		List<CmpProductFav> productfavlist = this.cmpProductService
				.getCmpProductFavListByUserId(userId, 0, 5);
		if (productfavlist.size() == 5) {
			req.setAttribute("morefavproduct", true);
			productfavlist.remove(4);
		}
		idList = new ArrayList<Long>();
		for (CmpProductFav fav : productfavlist) {
			idList.add(fav.getProductId());
		}
		Map<Long, CmpProduct> promap = this.cmpProductService
				.getCmpProductMapInId(idList);
		List<CmpProduct> favproductlist = new ArrayList<CmpProduct>();
		for (CmpProductFav fav : productfavlist) {
			favproductlist.add(promap.get(fav.getProductId()));
		}
		req.setAttribute("favproductlist", favproductlist);
		return this.getWeb3Jsp("user/home.jsp");
	}

	/**
	 * z个人档案
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String info(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		if (userId == 0) {
			Long i = (Long) req.getAttribute("userId");
			if (i != null) {
				userId = i;
			}
		}
		User loginUser = this.getLoginUser(req);
		if (loginUser != null && userId == 0) {
			userId = loginUser.getUserId();
		}
		else if (loginUser == null && userId == 0) {
			return null;
		}
		User user = this.userService.getUser(userId);
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(userId);
		boolean me = false;
		boolean blocked = false;// 是否被user阻止
		boolean areFriend = false;// 是否是关注user
		boolean blockedUser = false;// 是否阻止user
		boolean followed = false;// 是否被关注
		if (loginUser != null) {
			if (loginUser.getUserId() == userId) {
				me = true;
			}
			else {
				Follow friend = this.followService.getFollow(loginUser
						.getUserId(), userId);
				if (friend == null) {
					BlockUser blockUser = this.followService.getBlockUser(
							userId, loginUser.getUserId());
					if (blockUser != null) {
						blocked = true;
					}
					else {
						BlockUser blockUser2 = this.followService.getBlockUser(
								loginUser.getUserId(), userId);
						if (blockUser2 != null) {
							blockedUser = true;
						}
					}
				}
				else {
					areFriend = true;
					if (friend.getBothFollow() == Follow.FOLLOW_BOOTH) {
						followed = true;
					}
				}
				if (!followed) {
					if (this.followService.getFollow(userId, loginUser
							.getUserId()) != null) {
						followed = true;
					}
				}
			}
		}
		int friendCount = this.followService.countFollow(userId);
		int followCount = this.followService.countFollowed(userId);
		req.setAttribute("followed", followed);
		req.setAttribute("userOtherInfo", userOtherInfo);
		req.setAttribute("blockedUser", blockedUser);
		req.setAttribute("friendCount", friendCount);
		req.setAttribute("followCount", followCount);
		req.setAttribute("blocked", blocked);
		req.setAttribute("areFriend", areFriend);
		req.setAttribute("userId", userId);
		req.setAttribute("me", me);
		req.setAttribute("user", user);
		req.setAttribute("home", true);
		req.setAttribute("canSms", this.isCanSms(req, userId));
		req.setAttribute(HkWebUtil.SHOW_MENU, true);
		List<HkGroup> glist = this.groupService.getGroupListByUserId(userId, 0,
				3);
		req.setAttribute("glist", glist);
		List<Information> infolist = this.informationService
				.getInformationList(userId, 0, 1);
		boolean hasinfo = false;
		if (infolist.size() > 0) {
			hasinfo = true;
		}
		req.setAttribute("hasinfo", hasinfo);
		UserSmsPort userSmsPort = this.userSmsPortService
				.getUserSmsPortByUserId(userId);
		if (userSmsPort != null) {
			String userport = this.smsClient.getSmsConfig().getSpNumber()
					+ HkWebUtil.getUserPort(userId);
			req.setAttribute("userport", userport);
		}
		// 查看是否有红地毯
		ProUser proUser = this.userService.getProUserByUserID(userId);
		req.setAttribute("proUser", proUser);
		int replyLabaCount = this.labaService
				.countUserLabaReplyByUserId(userId);
		req.setAttribute("replyLabaCount", replyLabaCount);
		return this.getWapJsp("user/info.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String viewbynickname(HkRequest req, HkResponse resp)
			throws Exception {
		String nickName = req.getString("v");
		if (DataUtil.isEmpty(nickName)) {
			return null;
		}
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			return null;
		}
		return "r:/home.do?userId=" + user.getUserId();
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String webn(HkRequest req, HkResponse resp) throws Exception {
		String nickName = req.getString("v");
		User user = this.userService.getUserByNickName(nickName);
		if (user == null) {
			return null;
		}
		return "r:/home_web.do?userId=" + user.getUserId();
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String fromdomain(HkRequest req, HkResponse resp) throws Exception {
		String domain = req.getString("domain");
		User user = this.userService.getUserByDomain(domain);
		if (user == null) {
			return null;
		}
		req.setAttribute("userId", user.getUserId());
		return this.execute(req, resp);
	}

	private void makeIntro(UserOtherInfo userOtherInfo, User user, HkRequest req) {
		StringBuilder sb = new StringBuilder();
		if (user.isHasSex()) {
			sb.append(req.getText("userotherinfo.sex_" + user.getSex()))
					.append("，");
		}
		sb.append(req.getText("view.userinfo.regtimeintro", DataUtil
				.getFormatTimeData(userOtherInfo.getCreateTime(), "yy-MM-dd")));
		if (user.getCityId() > 0) {
			City city = ZoneUtil.getCity(user.getCityId());
			if (city != null) {
				Province province = ZoneUtil.getProvince(city.getProvinceId());
				if (province != null) {
					sb.append("，").append(
							req.getText("view.userinfo.cityintro", province
									.getProvince(), city.getCity()));
				}
			}
		}
		sb.append("，").append(
				req.getText("view.userinfo.scoreintro", userOtherInfo
						.getScore()));
		req.setAttribute("userinfointro", sb.toString());
	}

	/**
	 *用户地主的足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mayor(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<Mayor> list = this.cmpCheckInService.getMayorListByUserId(userId,
				page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		List<Long> idList = new ArrayList<Long>();
		for (Mayor o : list) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> cmpmap = this.companyService
				.getCompanyMapInId(idList);
		for (Mayor o : list) {
			o.setCompany(cmpmap.get(o.getCompanyId()));
		}
		req.setAttribute("list", list);
		return this.getWapJsp("user/mayor.jsp");
	}

	/**
	 * 徽章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String userbadge(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLongAndSetAttr("userId");
		User user = this.userService.getUser(userId);
		if (user == null) {
			return null;
		}
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		List<UserBadge> list = this.badgeService.getUserBadgeListByUserId(
				userId, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("user/userbadge.jsp");
	}
}
