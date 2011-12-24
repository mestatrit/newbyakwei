package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Follow;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/friend")
public class FriendAction extends BaseAction {
	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		req.setAttribute("userId", userId);
		SimplePage page = req.getSimplePage(20);
		List<Follow> followlist = this.followService.getFollowList(userId, page
				.getBegin(), page.getSize());
		List<Long> userIdList = new ArrayList<Long>();
		for (Follow f : followlist) {
			userIdList.add(f.getFriendId());
		}
		Map<Long, User> usermap = this.userService.getUserMapInId(userIdList);
		List<User> duserlist = new ArrayList<User>();
		for (Follow f : followlist) {
			duserlist.add(usermap.get(f.getFriendId()));
		}
		UserVoBuilder builder = new UserVoBuilder();
		builder.setNeedCheckFollowMe(true);
		builder.setNeedLaba(true);
		builder.setNeedFriend(true);
		builder.setLoginUser(this.getLoginUser(req));
		builder.setLabaParserCfg(this.getLabaParserCfgWeb(req));
		List<UserVo> uservolist = UserVo.create(duserlist, builder);
		req.setAttribute("uservolist", uservolist);
		// 用户数据
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		// 要过生日的好友
		List<Follow> flist = this.followService.getFollowList(userId);
		List<Long> idList = new ArrayList<Long>();
		for (Follow f : flist) {
			idList.add(f.getFriendId());
		}
		List<User> frienduserblist = this.userService
				.getUserListInIdForBirthday(idList, 0, 6);
		req.setAttribute("frienduserblist", frienduserblist);
		if (frienduserblist.size() == 0) {
			List<User> userblist = this.userService
					.getUserListForBirthday(0, 6);
			req.setAttribute("userblist", userblist);
		}
		return this.getWeb3Jsp("user/friendlist.jsp");
	}
}