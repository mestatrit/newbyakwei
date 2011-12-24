package com.hk.web.box.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.AdminUser;
import com.hk.bean.Box;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.BoxService;
import com.hk.svr.UserService;
import com.hk.web.util.HkWebUtil;

public class OpBoxInterceptor extends HkAbstractInterceptor {
	@Autowired
	BoxService boxService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		long boxId = invocation.getRequest().getLong("boxId");
		User loginUser = HkWebUtil.getLoginUser(invocation.getRequest());
		Box box = boxService.getBox(boxId);
		if (box == null || box.getUserId() != loginUser.getUserId()) {
			UserService userService = (UserService) HkUtil
					.getBean("userService");
			AdminUser adminUser = userService.getAdminUser(loginUser
					.getUserId());
			if (adminUser == null) {
				invocation.getRequest().setSessionMessage("不能操作宝箱");
				return "r:/more.do";
			}
		}
		return invocation.invoke();
	}
}