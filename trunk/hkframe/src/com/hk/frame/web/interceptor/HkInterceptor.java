package com.hk.frame.web.interceptor;

import com.hk.frame.web.action.HkActionInvocation;

public interface HkInterceptor {
	String doBefore(HkActionInvocation invocation) throws Exception;

	String doAfter(HkActionInvocation invocation) throws Exception;
}