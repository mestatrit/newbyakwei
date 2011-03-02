package com.hk.frame.web.interceptor;

import com.hk.frame.web.action.HkActionInvocation;

public class HkAbstractInterceptor implements HkInterceptor {
	public String doAfter(HkActionInvocation invocation) throws Exception {
		return null;
	}

	public String doBefore(HkActionInvocation invocation) throws Exception {
		return invocation.invoke();
	}
}