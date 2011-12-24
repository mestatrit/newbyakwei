package com.hk.svr;

import com.hk.bean.UserFgtSms;

/**
 * 用户忘记密码，通过短信或者email发送信息的数据记录
 * 
 * @author akwei
 */
public interface UserFgtPwdService {

	/**
	 * 创建用户发送的短信验证码数据，如果数据已经存在，则更新此数据
	 * 
	 * @param userFgtSms
	 *            2010-4-11
	 */
	void createUserFgtSms(UserFgtSms userFgtSms);

	void deleteUserFgtSms(long userId);

	UserFgtSms getUserFgtSms(long userId);
}