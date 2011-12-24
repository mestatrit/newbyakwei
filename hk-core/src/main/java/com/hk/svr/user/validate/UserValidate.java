package com.hk.svr.user.validate;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.IllegalEmailException;
import com.hk.svr.user.exception.IllegalMobileException;
import com.hk.svr.user.exception.ProtectValueErrorException;
import com.hk.svr.user.exception.UserNotExistException;

public class UserValidate implements Err {
	public static void validateUser(long userId) throws UserNotExistException {
		UserService userService = (UserService) HkUtil.getBean("userService");
		if (userService.getUser(userId) == null) {
			throw new UserNotExistException("user [ " + userId
					+ " ] is not exist.");
		}
	}

	public static void validateEmail(String email) throws IllegalEmailException {
		if (!DataUtil.isLegalEmail(email)) {
			throw new IllegalEmailException("email format error");
		}
	}

	public static int validatePassword(String password) {
		if (!DataUtil.isLegalPassword(password)) {
			return PASSWORD_DATA_ERROR;
		}
		return SUCCESS;
	}

	public static void validateMobile(String mobile)
			throws IllegalMobileException {
		if (!DataUtil.isLegalMobile(mobile)) {
			throw new IllegalMobileException("error mobile", mobile);
		}
	}

	public static void validateSetProtect(String protectValue)
			throws ProtectValueErrorException {
		if (protectValue == null || protectValue.length() > 50
				|| protectValue.equals("")) {
			throw new ProtectValueErrorException("protectValue error");
		}
	}

	public static int validateSetProtect2(String protectValue) {
		if (protectValue == null || protectValue.length() > 50
				|| protectValue.equals("")) {
			return Err.PWD_PROTECT_ERROR;
		}
		return Err.SUCCESS;
	}

	public static int validateBirthday(int month, int date) {
		if (month < 1 || month > 12) {
			return BIRTHDAY_MONTH_ERROR;
		}
		if (date < 1 || date > 31) {
			return BIRTHDAY_DATE_ERROR;
		}
		return SUCCESS;
	}
}