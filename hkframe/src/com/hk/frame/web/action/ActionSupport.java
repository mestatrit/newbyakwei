package com.hk.frame.web.action;

import javax.servlet.http.Cookie;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.web.http.HkRequest;

/**
 * 默认提供的Action辅助类
 * 
 * @author akwei
 */
public abstract class ActionSupport implements Action {

	/**
	 * 从资源文件加载显示数据
	 * 
	 * @param key
	 * @param args
	 * @return
	 *         2010-11-20
	 */
	protected String getText(String key, Object... args) {
		return ResourceConfig.getText(key, args);
	}

	/**
	 * 验证图片验证码
	 * 
	 * @param request
	 * @return true:验证成功,false:验证失败
	 *         2010-11-20
	 */
	protected boolean legalAccessByAuthImg(HkRequest request) {
		Cookie cookie = request.getCookie(HkUtil.CLOUD_IMAGE_AUTH);
		if (cookie == null) {
			return false;
		}
		if (cookie.getValue().equalsIgnoreCase(
				request.getString(HkUtil.CLOUD_IMAGE_AUTH))) {
			return true;
		}
		return false;
	}
}