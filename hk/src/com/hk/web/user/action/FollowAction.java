package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Follow;
import com.hk.bean.Followed;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.UserService;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.user.UserVo;
import com.hk.web.user.UserVoBuilder;

@Component("/follow/follow")
public class FollowAction extends BaseAction {

	private int size = 20;

	@Autowired
	private FollowService followService;

	@Autowired
	private UserService userService;

	/**
	 * pc 访问页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		return this.getWeb3Jsp("follow/list.jsp");
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String nickName = req.getString("nickName");
		long userId = req.getLong("userId");
		if (userId == 0 && loginUser != null) {
			userId = loginUser.getUserId();
		}
		User user = this.userService.getUser(userId);
		SimplePage page = req.getSimplePage(20);
		List<Follow> list = null;
		if (isEmpty(nickName)) {
			list = this.followService.getFollowList(userId, page.getBegin(),
					page.getSize() + 1);
		}
		else {
			list = this.followService.getFollowListByNickName(userId, nickName,
					page.getBegin(), page.getSize() + 1);
		}
		this.processListForPage(page, list);
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (Follow o : list) {
			UserVo vo = new UserVo();
			vo.setUser(o.getFollowUser());
			uservolist.add(vo);
		}
		UserVoBuilder builder = new UserVoBuilder();
		builder.setVolist(uservolist);
		builder.setLabaParserCfg(this.getLabaParserCfg(req));
		builder.setForceFriend(true);
		builder.setLoginUser(loginUser);
		builder.setNeedLaba(true);
		UserVo.buildUserVoInfo(builder);
		req.setEncodeAttribute("nickName", nickName);
		req.setAttribute("uservolist", uservolist);
		req.setAttribute("user", user);
		req.setAttribute("userId", userId);
		req.setAttribute("uservobuilder", builder);
		return this.getWapJsp("follow/follow.jsp");
	}

	/**
	 * 被谁关注
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String re(HkRequest req, HkResponse resp) throws Exception {
		String nickName = req.getString("nickName");
		User loginUser = this.getLoginUser(req);
		long userId = req.getLong("userId");
		if (userId == 0 && loginUser != null) {
			userId = loginUser.getUserId();
		}
		User user = this.userService.getUser(userId);
		SimplePage page = req.getSimplePage(size);
		List<Followed> list = null;
		if (isEmpty(nickName)) {
			list = this.followService.getFollowedList(userId, page.getBegin(),
					size);
		}
		else {
			list = this.followService.getFollowedListByNickName(userId,
					nickName, page.getBegin(), size);
		}
		List<UserVo> uservolist = new ArrayList<UserVo>();
		for (Followed o : list) {
			UserVo vo = new UserVo();
			vo.setUser(o.getFollowingUser());
			uservolist.add(vo);
		}
		page.setListSize(list.size());
		UserVoBuilder builder = new UserVoBuilder();
		builder.setVolist(uservolist);
		builder.setLabaParserCfg(this.getLabaParserCfg(req));
		builder.setNeedFriend(true);
		builder.setLoginUser(loginUser);
		builder.setNeedLaba(true);
		UserVo.buildUserVoInfo(builder);
		req.setEncodeAttribute("nickName", nickName);
		req.setAttribute("uservolist", uservolist);
		req.setAttribute("user", user);
		req.setAttribute("userId", userId);
		req.setAttribute("uservobuilder", builder);
		return "/WEB-INF/page/follow/follow_re.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String back(HkRequest req, HkResponse resp) {
		String from = req.getString("from");
		String add = "#user" + req.getLong("userId");
		if (from != null) {
			if (from.equals("userlist")) {
				return "r:/user/list1.do?w=" + req.getString("w") + "&page="
						+ req.getInt("repage") + add;
			}
			if (from.equals("fcuserlist")) {
				return "r:/user/list2.do?page=" + req.getInt("repage") + add;
			}
			if (from.equals("usersearch")) {
				return "r:/user/search.do?sw=" + req.getEncodeString("sw")
						+ "&page=" + req.getInt("repage") + "&sfrom="
						+ req.getString("sfrom") + add;
			}
			if (from.equals("follow")) {
				return "r:/follow/follow.do?userId=" + req.getLong("ouserId")
						+ "&nickName=" + req.getEncodeString("nickName")
						+ "&page=" + req.getInt("repage") + add;
			}
			if (from.equals("follow_re")) {
				return "r:/follow/follow_re.do?userId="
						+ req.getLong("ouserId") + "&page="
						+ req.getInt("repage") + "&nickName="
						+ req.getEncodeString("nickName") + add;
			}
			if (from.equals("gulist")) {
				return "r:/group/gulist.do?gid=" + req.getInt("ogid")
						+ "&page=" + req.getInt("repage") + add;
			}
			if (from.equals("hkuser")) {
				return "r:/user/hkuser.do?page=" + req.getInt("repage") + add;
			}
		}
		return "r:/home.do?userId=" + req.getLong("userId");
	}
}