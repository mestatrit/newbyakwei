package com.hk.web.pub.interceptor;

import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.web.util.HkWebUtil;

public class LoginInterceptor extends HkAbstractInterceptor {
	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		User user = HkWebUtil.getLoginUser(invocation.getRequest());
		if (user == null) {
			String uri = invocation.getRequest().getRequestURI();
			String query = invocation.getRequest().getQueryString();
			if (DataUtil.isEmpty(query)) {
				query = "";
			}
			String return_url = uri + "?" + query;
			return "r:/tologin.do?return_url="
					+ DataUtil.urlEncoder(return_url);
		}
		return invocation.invoke();
	}
}