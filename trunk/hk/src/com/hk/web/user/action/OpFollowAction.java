package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Follow;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.friend.validate.FollowValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.UserNotExistException;
import com.hk.web.pub.action.BaseAction;

@Component("/follow/op/op")
public class OpFollowAction extends BaseAction {
	@Autowired
	private FollowService followService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 取消关注
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		this.followService.removeFollow(loginUser.getUserId(), userId);
		request.setSessionMessage("操作成功");
		return "/follow/follow_back.do";
	}

	/**
	 * 取消关注
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String delweb(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		this.followService.removeFollow(loginUser.getUserId(), userId);
		this.initmsg(userId, loginUser, response);
		return null;
	}

	/**
	 * 判断是否可以对用户发私信
	 * 
	 * @param userId
	 * @param loginUser
	 * @param response
	 */
	private void initmsg(long userId, User loginUser, HkResponse response) {
		Follow follow = this.followService.getFollow(userId, loginUser
				.getUserId());
		if (follow != null) {
			response.sendHtml("-1");// 对方加你为好友
		}
		else {
			response.sendHtml("-2");// 对方没有加你为好友
		}
	}

	/**
	 * 关注user
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		try {
			FollowValidate.validateAddFollow(loginUser.getUserId(), userId);
			this.followService.addFollow(loginUser.getUserId(), userId, request
					.getRemoteAddr(), true);
		}
		catch (AlreadyBlockException e) {
			request.setSessionMessage("对方拒绝你关注");
			return "r:/home.do?userId=" + userId;
		}
		catch (UserNotExistException e) {//
		}
		request.setSessionMessage("操作成功");
		return "/follow/follow_back.do";
	}

	/**
	 * 关注user
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addweb(HkRequest req, HkResponse resp) {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		try {
			FollowValidate.validateAddFollow(loginUser.getUserId(), userId);
			this.followService.addFollow(loginUser.getUserId(), userId, req
					.getRemoteAddr(), true);
			this.initmsg(userId, loginUser, resp);
		}
		catch (AlreadyBlockException e) {
			resp.sendHtml(Err.FOLLOW_USER_BLOCK + "");
		}
		catch (UserNotExistException e) {//
		}
		return null;
	}
}
