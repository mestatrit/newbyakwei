package com.hk.web.bomb.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.Bomber;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.action.PathProcesser;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.BombService;
import com.hk.web.util.HkWebUtil;

public class BombFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = HkWebUtil.getLoginUser(request);
		BombService bombService = (BombService) HkUtil.getBean("bombService");
		Bomber bomber = bombService.getBomber(user.getUserId());
		if (bomber == null) {
			PathProcesser.proccessResult("r:/loginbomb/bomb.do", request,
					response);
			return;
		}
		boolean superAdmin = false;
		boolean admin = false;
		if (bomber.getUserLevel() == Bomber.USERLEVEL_ADMIN) {
			admin = true;
		}
		if (bomber.getUserLevel() == Bomber.USERLEVEL_SUPERADMIN) {
			superAdmin = true;
		}
		request.setAttribute("superAdmin", superAdmin);
		request.setAttribute("admin", admin);
		chain.doFilter(request, response);
	}
}