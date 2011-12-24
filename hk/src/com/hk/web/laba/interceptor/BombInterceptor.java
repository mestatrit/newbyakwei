package com.hk.web.laba.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Bomber;
import com.hk.bean.User;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.BombService;
import com.hk.web.util.HkWebUtil;

/**
 * 炸弹使用者的拦截器
 * 
 * @author akwei
 */
public class BombInterceptor extends HkAbstractInterceptor {
	@Autowired
	BombService bombService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		User user = HkWebUtil.getLoginUser(invocation.getRequest());
		Bomber bomber = bombService.getBomber(user.getUserId());
		if (bomber == null) {
			return null;
		}
		return invocation.invoke();
	}
}