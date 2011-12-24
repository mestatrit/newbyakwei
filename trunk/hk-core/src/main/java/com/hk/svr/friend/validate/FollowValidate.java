package com.hk.svr.friend.validate;

import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.user.exception.UserNotExistException;

public class FollowValidate {
	public static void validateAddFollow(long userId, long friendId)
			throws UserNotExistException {
		UserService userService = (UserService) HkUtil.getBean("userService");
		/** ************** 检查用户是否存在 ****************** */
		User user = userService.getUser(userId);
		if (user == null) {
			throw new UserNotExistException("user is not exist", userId);
		}
		User friendUser = userService.getUser(friendId);
		if (friendUser == null) {
			throw new UserNotExistException("user is not exist", friendId);
		}
	}
}