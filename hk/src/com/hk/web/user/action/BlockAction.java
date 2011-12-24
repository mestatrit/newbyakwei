package com.hk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.User;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.web.pub.action.BaseAction;

@Component("/user/block/op/op")
public class BlockAction extends BaseAction {
	@Autowired
	private FollowService followService;

	public String execute(HkRequest request, HkResponse response)
			throws Exception {
		return null;
	}

	/**
	 * 屏蔽user
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
			this.followService.blockUser(loginUser.getUserId(), userId);
		}
		catch (AlreadyBlockException e) {
			request.setSessionMessage("对方已经屏蔽你");
			return "r:/home.do?userId=" + userId;
		}
		request.setSessionMessage("屏蔽成功");
		return "r:/home.do?userId=" + userId;
	}

	/**
	 * 取消屏蔽
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest request, HkResponse response) {
		long userId = request.getLong("userId");
		User loginUser = this.getLoginUser(request);
		this.followService.removeBlockUser(loginUser.getUserId(), userId);
		request.setSessionMessage("取消屏蔽成功");
		return "r:/home.do?userId=" + userId;
	}
}