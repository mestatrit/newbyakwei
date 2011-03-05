package tuxiazi.web.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;

import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ServletUtil;
import com.hk.frame.web.http.HkFilter;
import com.hk.frame.web.http.HkRequest;

public class ApiFilter extends HkFilter {

	private final Log log = LogFactory.getLog(ApiFilter.class);

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("url [ "
				+ request.getRequestURL().append("?").append(
						request.getQueryString()) + " ]");
		proccess(request, response, chain);
	}

	void proccess(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HkRequest req = (HkRequest) request;
		UserService userService = (UserService) HkUtil.getBean("userService");
		long userid = ServletUtil.getLong(request, "userid");
		String access_token = ServletUtil.getString(request, "access_token");
		String token_secret = ServletUtil.getString(request, "token_secret");
		Api_user_sina apiUserSina = null;
		if (userid > 0) {
			apiUserSina = userService.getApi_user_sinaByUserid(userid);
		}
		if (apiUserSina != null) {
			User user = userService.getUser(apiUserSina.getUserid());
			req.setAttribute("user", user);
			req.setAttribute("apiUserSina", apiUserSina);
		}
		if (request.getRequestURI().indexOf("prv") == -1) {// 公开访问
			chain.doFilter(request, response);
			return;
		}
		if (apiUserSina == null) {
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.API_NO_SINA_USER);
			context
					.put("err_msg", APIUtil
							.getErrMsg(req, Err.API_NO_SINA_USER));
			APIUtil.write(response, "vm/sinaerr.vm", context);
			return;
		}
		if (apiUserSina.getAccess_token().equals(access_token)
				&& apiUserSina.getToken_secret().equals(token_secret)) {
			chain.doFilter(request, response);
		}
		else {
			VelocityContext context = new VelocityContext();
			context.put("errcode", Err.API_NO_SINA_USER);
			context.put("err_msg", APIUtil.getErrMsg(req,
					Err.API_SINA_USER_TOKEN_ERR));
			APIUtil.write(response, "vm/sinaerr.vm", context);
		}
		return;
	}
}