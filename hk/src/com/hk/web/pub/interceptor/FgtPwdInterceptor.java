package com.hk.web.pub.interceptor;

import com.hk.bean.User;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.web.util.HkWebUtil;

public class FgtPwdInterceptor extends HkAbstractInterceptor {
	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		User user = HkWebUtil.getLoginUser(invocation.getRequest());
		if (user != null) {
			return "r:/tologin.do";
		}
		return invocation.invoke();
	}
}