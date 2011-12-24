package com.hk.svr.msg.validate;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.msg.exception.MsgFormatErrorException;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.UserNotExistException;

public class MsgValidate {
	private MsgValidate() {//
	}

	public static void validateCreateMsg(long senderId, long userId, String msg)
			throws MsgFormatErrorException, UserNotExistException {
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (userService.getUser(senderId) == null) {
			throw new UserNotExistException("senderId [ " + senderId
					+ " ] is not exist");
		}
		if (userService.getUser(userId) == null) {
			throw new UserNotExistException("user [ " + userId
					+ " ] is not exist");
		}
		if (DataUtil.isEmpty(msg)) {
			throw new MsgFormatErrorException("msg content is empty");
		}
		if (msg.length() > 140) {
			throw new MsgFormatErrorException(
					"msg content's length is out of 140");
		}
	}

	public static int validateMsg(String msg) {
		if (DataUtil.isEmpty(msg)) {
			return Err.MSG_MSG_ERROR;
		}
		if (msg.length() > 140) {
			return Err.MSG_MSG_ERROR;
		}
		return Err.SUCCESS;
	}
}