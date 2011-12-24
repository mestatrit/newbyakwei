package com.hk.web.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.hk.bean.Followed;
import com.hk.bean.Laba;
import com.hk.bean.User;
import com.hk.bean.UserRecentLaba;
import com.hk.frame.util.HkUtil;
import com.hk.svr.FollowService;
import com.hk.svr.LabaService;
import com.hk.web.pub.action.LabaVo;

public class UserVo {
	private User user;

	private LabaVo labaVo;

	boolean follow;

	boolean followme;

	public boolean isFollowme() {
		return followme;
	}

	public void setFollowme(boolean followme) {
		this.followme = followme;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public static List<UserVo> create(List<User> list, UserVoBuilder builder) {
		List<Long> idList = new ArrayList<Long>();
		List<UserVo> volist = new ArrayList<UserVo>();
		for (User user : list) {
			UserVo o = new UserVo();
			o.setUser(user);
			volist.add(o);
			idList.add(user.getUserId());
		}
		FollowService followService = (FollowService) HkUtil
				.getBean("followService");
		LabaService labaService = (LabaService) HkUtil.getBean("labaService");
		List<Long> followUserIdList = null;
		if (builder.isNeedFriend()) {
			if (!builder.isForceFriend()) {
				if (builder.getLoginUser() != null) {
					followUserIdList = followService.getUserIdList(builder
							.getLoginUser().getUserId(), idList);
				}
			}
		}
		Map<Long, UserRecentLaba> urlabaMap = null;
		Map<Long, Laba> labamap = null;
		if (builder.isNeedLaba()) {
			urlabaMap = labaService.getUserRecentLabaMapInUser(idList);
			List<Long> labaIdList = new ArrayList<Long>();
			Collection<UserRecentLaba> c = urlabaMap.values();
			for (UserRecentLaba o : c) {
				labaIdList.add(o.getLastLabaId());
			}
			labamap = labaService.getLabaMapInId(labaIdList);
		}
		Map<Long, Followed> fmap = null;
		if (builder.isNeedCheckFollowMe() && builder.getLoginUser() != null) {
			fmap = followService.getFollowedMapByfollowingUserIdInUserId(
					builder.getLoginUser().getUserId(), idList);
		}
		for (UserVo vo : volist) {
			if (builder.isNeedCheckFollowMe()) {
				if (fmap != null && fmap.containsKey(vo.getUser().getUserId())) {
					vo.setFollowme(true);
				}
			}
			if (builder.isNeedFriend()) {
				if (builder.isForceFriend()) {
					vo.setFollow(true);
				}
				else {
					if (followUserIdList != null
							&& followUserIdList.contains(vo.getUser()
									.getUserId())) {
						vo.setFollow(true);
					}
				}
			}
			if (builder.isNeedLaba() && urlabaMap != null) {
				UserRecentLaba userRecentLaba = urlabaMap.get(vo.getUser()
						.getUserId());
				if (userRecentLaba != null) {
					long labaId = userRecentLaba.getLastLabaId();
					if (labaId > 0 && labamap != null) {
						Laba laba = labamap.get(labaId);
						if (laba != null) {
							LabaVo labaVo = LabaVo.create(laba, builder
									.getLabaParserCfg());
							vo.setLabaVo(labaVo);
						}
					}
				}
			}
		}
		return volist;
	}

	public static void buildUserVoInfo(UserVoBuilder builder) {
		List<Long> idList = new ArrayList<Long>();
		for (UserVo vo : builder.getVolist()) {
			idList.add(vo.getUser().getUserId());
		}
		FollowService followService = (FollowService) HkUtil
				.getBean("followService");
		LabaService labaService = (LabaService) HkUtil.getBean("labaService");
		List<Long> followUserIdList = null;
		if (builder.isNeedFriend()) {
			if (!builder.isForceFriend()) {
				if (builder.getLoginUser() != null) {
					followUserIdList = followService.getUserIdList(builder
							.getLoginUser().getUserId(), idList);
				}
			}
		}
		Map<Long, UserRecentLaba> urlabaMap = null;
		Map<Long, Laba> labamap = null;
		if (builder.isNeedLaba()) {
			urlabaMap = labaService.getUserRecentLabaMapInUser(idList);
			List<Long> labaIdList = new ArrayList<Long>();
			Collection<UserRecentLaba> c = urlabaMap.values();
			for (UserRecentLaba o : c) {
				labaIdList.add(o.getLastLabaId());
			}
			labamap = labaService.getLabaMapInId(labaIdList);
		}
		for (UserVo vo : builder.getVolist()) {
			if (builder.isNeedFriend()) {
				if (builder.isForceFriend()) {
					vo.setFollow(true);
				}
				else {
					if (followUserIdList != null
							&& followUserIdList.contains(vo.getUser()
									.getUserId())) {
						vo.setFollow(true);
					}
				}
			}
			if (builder.isNeedLaba() && urlabaMap != null) {
				UserRecentLaba userRecentLaba = urlabaMap.get(vo.getUser()
						.getUserId());
				if (userRecentLaba != null) {
					long labaId = userRecentLaba.getLastLabaId();
					if (labaId > 0 && labamap != null) {
						Laba laba = labamap.get(labaId);
						if (laba != null) {
							LabaVo labaVo = LabaVo.create(laba, builder
									.getLabaParserCfg());
							vo.setLabaVo(labaVo);
						}
					}
				}
			}
		}
	}

	public LabaVo getLabaVo() {
		return labaVo;
	}

	public void setLabaVo(LabaVo labaVo) {
		this.labaVo = labaVo;
	}
}