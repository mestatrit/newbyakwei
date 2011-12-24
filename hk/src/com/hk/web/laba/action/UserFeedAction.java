package com.hk.web.laba.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Feed;
import com.hk.bean.User;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.impl.FeedServiceWrapper;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.pub.action.BaseAction;

/**
 * 用户的动态
 * 
 * @author akwei
 */
@Component("/userfeed")
public class UserFeedAction extends BaseAction {
	@Autowired
	private UserService userService;

	/**
	 * 好友的动态
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String friend(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		if (loginUser == null) {// 未登录不能查看
			return this.getWebLoginForward(req);
		}
		long userId = loginUser.getUserId();
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		FeedServiceWrapper feedServiceWrapper = new FeedServiceWrapper();
		SimplePage page = req.getSimplePage(20);
		List<Feed> feedlist = feedServiceWrapper.getFriendFeedList(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, feedlist);
		List<FeedVo> feedvolist = FeedVo.createList(req, feedlist, true, false);
		req.setAttribute("feedvolist", feedvolist);
		return this.getWeb3Jsp("user/friendfeedlist.jsp");
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		req.setAttribute("userId", userId);
		req.setAttribute("feedvo_not_show_head", true);// 不显示头像
		User user = this.userService.getUser(userId);
		req.setAttribute("user", user);
		SimplePage page = req.getSimplePage(20);
		FeedServiceWrapper feedServiceWrapper = new FeedServiceWrapper();
		List<Feed> feedlist = feedServiceWrapper.getUserFeedList(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, feedlist);
		List<FeedVo> feedvolist = FeedVo.createList(req, feedlist, true, false);
		req.setAttribute("feedvolist", feedvolist);
		return this.getWeb3Jsp("user/userfeedlist.jsp");
	}
}