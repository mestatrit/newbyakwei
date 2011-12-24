package com.hk.web.bomb.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hk.bean.Bomber;
import com.hk.bean.User;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.svr.BombService;
import com.hk.web.util.HkWebUtil;

public class AdminBombFilter extends HkFilter {
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		User user = HkWebUtil.getLoginUser(request);
		BombService bombService = (BombService) HkUtil.getBean("bombService");
		Bomber bomber = bombService.getBomber(user.getUserId());
		if (bomber == null || bomber.getUserLevel() == Bomber.USERLEVEL_NORMAL) {
			return;
		}
		chain.doFilter(request, response);
	}
}