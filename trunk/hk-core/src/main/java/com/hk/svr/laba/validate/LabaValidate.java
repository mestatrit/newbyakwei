package com.hk.svr.laba.validate;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.LabaService;
import com.hk.svr.laba.exception.ContentEmptyException;
import com.hk.svr.laba.exception.ContentLengthOutOfLimitException;
import com.hk.svr.laba.exception.LabaNotExistException;
import com.hk.svr.pub.Err;

public class LabaValidate implements Err {
	public static void validateCreate(String content)
			throws ContentEmptyException, ContentLengthOutOfLimitException {
		if (DataUtil.isEmpty(content)) {
			throw new ContentEmptyException("empty content");
		}
		if (content.length() > 140) {
			throw new ContentLengthOutOfLimitException(
					"content length gt 140 [ " + content.length() + " ]");
		}
	}

	public static void validateLaba(long labaId) throws LabaNotExistException {
		LabaService labaService = (LabaService) HkUtil.getBean("labaService");
		if (labaService.getLaba(labaId) == null) {
			throw new LabaNotExistException("laba [ " + labaId
					+ " ] is not exist");
		}
	}
}