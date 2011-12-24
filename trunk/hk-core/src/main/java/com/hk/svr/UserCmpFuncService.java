package com.hk.svr;

import com.hk.bean.UserCmpFunc;

/**
 * 用户企业功能的操作
 * 
 * @author akwei
 */
public interface UserCmpFuncService {

	/**
	 * 创建或者更新对象
	 * 
	 * @param userCmpFunc
	 *            2010-4-28
	 */
	void saveUserCmpFunc(UserCmpFunc userCmpFunc);

	UserCmpFunc getUserCmpFunc(long userId);
}