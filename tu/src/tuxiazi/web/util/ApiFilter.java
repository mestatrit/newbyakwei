package tuxiazi.web.util;

import halo.util.HaloUtil;
import halo.web.action.HaloFilter;
import halo.web.action.HkRequest;
import halo.web.util.ServletUtil;

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
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
import tuxiazi.util.Err;

public class ApiFilter extends HaloFilter {

	private final Log log = LogFactory.getLog(ApiFilter.class);

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("url [ "
				+ request.getRequestURL().append("?")
						.append(request.getQueryString()) + " ]");
		proccess(request, response, chain);
	}

	void proccess(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HkRequest req = (HkRequest) request;
		long userid = ServletUtil.getLong(request, "userid");
		String access_token = ServletUtil.getString(request, "access_token");
		String token_secret = ServletUtil.getString(request, "token_secret");
		Api_user_sina apiUserSina = null;
		if (userid > 0) {
			Api_user_sinaDao api_user_sinaDao = (Api_user_sinaDao) HaloUtil
					.getBean("api_user_sinaDao");
			apiUserSina = api_user_sinaDao.getByUserid(userid);
		}
		if (apiUserSina != null) {
			UserDao userDao = (UserDao) HaloUtil.getBean("userDao");
			User user = userDao.getById(apiUserSina.getUserid());
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
			context.put("err_msg", APIUtil.getErrMsg(Err.API_NO_SINA_USER));
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
			context.put("err_msg",
					APIUtil.getErrMsg(Err.API_SINA_USER_TOKEN_ERR));
			APIUtil.write(response, "vm/sinaerr.vm", context);
		}
		return;
	}
}