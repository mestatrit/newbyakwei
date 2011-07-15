package iwant.web.admin.util;

import halo.web.util.ServletUtil;
import iwant.bean.AdminUser;
import iwant.bean.exception.NoAdminUserException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 后台权限相关工具类，从admin.properties文件加载username,pwd信息与后台登录用户的输入进行匹配
 * 
 * @author akwei
 */
public class AdminUtil {

	private static final String mgr_adminuser_cookie_name = "au";

	public static void setLoginAdminUser(HttpServletResponse response,
			AdminUser adminUser) {
		Cookie cookie = new Cookie(mgr_adminuser_cookie_name, adminUser
				.getSecretKey());
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static AdminUser getLoginAdminUser(HttpServletRequest request) {
		AdminUser adminUser = (AdminUser) request.getAttribute("ademinUser");
		if (adminUser == null) {
			adminUser = (AdminUser) request.getSession().getAttribute(
					"adminUser");
			if (adminUser == null) {
				Cookie cookie = ServletUtil.getCookie(request,
						mgr_adminuser_cookie_name);
				if (cookie == null) {
					return null;
				}
				try {
					adminUser = new AdminUser(cookie.getValue());
					request.getSession().setAttribute("adminUser", adminUser);
				}
				catch (NoAdminUserException e) {
					return null;
				}
			}
			request.setAttribute("adminUser", adminUser);
		}
		return adminUser;
	}

	public static void clearLoginAdminUser(HttpServletRequest request,
			HttpServletResponse response) {
		request.removeAttribute("adminUser");
		request.getSession().invalidate();
		Cookie cookie = new Cookie(mgr_adminuser_cookie_name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}