package com.hk.web.pub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.frame.web.action.HkActionInvocation;
import com.hk.frame.web.interceptor.HkAbstractInterceptor;
import com.hk.svr.UserService;

public class MobileBindInterceptor extends HkAbstractInterceptor {
	@Autowired
	private UserService userService;

	@Override
	public String doBefore(HkActionInvocation invocation) throws Exception {
		User loginUser = (User) invocation.getRequest().getAttribute(
				"loginUser");
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		if (!info.isMobileAlreadyBind()) {
			invocation.getRequest().setSessionMessage(
					ResourceConfig.getText("func.mobilenotbind"));
			String noac_method = invocation.getRequest().getString(
					"noac_method");
			if (!DataUtil.isEmpty(noac_method)) {
				if (noac_method.equals("set")) {
					return "r:/user/set/set.do";
				}
			}
			return "r:/more.do";
		}
		return super.doBefore(invocation);
	}
}