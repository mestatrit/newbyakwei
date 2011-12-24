package com.hk.web.user;

import java.util.List;
import com.hk.bean.User;
import com.hk.web.pub.action.LabaParserCfg;

public class UserVoBuilder {
	private User loginUser;// 登录用户

	private List<UserVo> volist;// userVo集合,里面必须有userVo对象

	// private List<Long> idList;// 用户好友id集合
	private boolean needFansCount;// 是否需要粉丝数量

	private boolean forceFriend;// 是否强制都为好友关系

	private boolean needFriend;// 是否需要好友关系

	private boolean needLaba;

	private LabaParserCfg labaParserCfg;

	private boolean needCheckFollowMe;// 查看是否加我为好友(登录用户才能进行)

	public void setNeedCheckFollowMe(boolean needCheckFollowMe) {
		this.needCheckFollowMe = needCheckFollowMe;
	}

	public boolean isNeedCheckFollowMe() {
		return needCheckFollowMe;
	}

	public void setLabaParserCfg(LabaParserCfg labaParserCfg) {
		this.labaParserCfg = labaParserCfg;
	}

	public LabaParserCfg getLabaParserCfg() {
		return labaParserCfg;
	}

	public boolean isNeedLaba() {
		return needLaba;
	}

	public void setNeedLaba(boolean needLaba) {
		this.needLaba = needLaba;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public List<UserVo> getVolist() {
		return volist;
	}

	public void setVolist(List<UserVo> volist) {
		this.volist = volist;
	}

	public boolean isNeedFansCount() {
		return needFansCount;
	}

	public void setNeedFansCount(boolean needFansCount) {
		this.needFansCount = needFansCount;
	}

	public boolean isForceFriend() {
		return forceFriend;
	}

	public void setForceFriend(boolean forceFriend) {
		if (forceFriend) {
			this.setNeedFriend(true);
		}
		this.forceFriend = forceFriend;
	}

	public boolean isNeedFriend() {
		return needFriend;
	}

	public void setNeedFriend(boolean needFriend) {
		this.needFriend = needFriend;
	}
}
