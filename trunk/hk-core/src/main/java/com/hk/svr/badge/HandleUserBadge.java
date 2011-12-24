package com.hk.svr.badge;

import java.util.Map;

import com.hk.bean.Badge;
import com.hk.bean.HandleCheckInUser;

/**
 * 根据报到的信息来计算用户获得的徽章
 * 
 * @author akwei
 */
public interface HandleUserBadge {
	/**
	 * @param handleCheckInUser 预处理的用户信息
	 * @param badge 徽章
	 */
	void execute(Map<String, Object> paramMap,
			HandleCheckInUser handleCheckInUser, Badge badge);
}