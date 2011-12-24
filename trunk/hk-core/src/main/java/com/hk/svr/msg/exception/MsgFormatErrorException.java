package com.hk.svr.msg.exception;

import com.hk.svr.exception.HkException;

/**
 * 私信内容不能为空或者内容不能超过140个字符
 * 
 * @author yuanwei
 */
public class MsgFormatErrorException extends HkException {
	private static final long serialVersionUID = 6851250776152527191L;

	public MsgFormatErrorException(String message) {
		super(message);
	}
}