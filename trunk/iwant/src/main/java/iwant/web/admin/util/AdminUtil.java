package iwant.web.admin.util;

import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cactus.util.DesUtil;
import cactus.util.P;
import cactus.web.util.ServletUtil;

/**
 * 后台权限相关工具类，从admin.properties文件加载username,pwd信息与后台登录用户的输入进行匹配
 * 
 * @author akwei
 */
public class AdminUtil {

	private static final String des_key = "abc";

	private static DesUtil pwddesUtil = new DesUtil(des_key);

	private static String username = null;

	private static String pwd = null;

	private static String mgr_cookie_name = "usermgrcookie";

	private static final String key_admin_login = "dminlogin";
	static {
		ResourceBundle rb = ResourceBundle.getBundle("admin");
		username = rb.getString("username");
		pwd = rb.getString("pwd");
	}

	/**
	 * 判断用户是否登录成功
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	public static boolean isLogined(String username, String pwd) {
		if (AdminUtil.username.equals(username)
				&& AdminUtil.pwd.equals(encodeValue(pwd))) {
			return true;
		}
		return false;
	}

	/**
	 * 设置用户登录状态
	 * 
	 * @param request
	 * @param admin
	 */
	public static void setLoginAdmin(HttpServletRequest request,
			HttpServletResponse response, Admin admin) {
		Cookie cookie = new Cookie(mgr_cookie_name,
				encodeValue(key_admin_login));
		cookie.setMaxAge(24 * 60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void clearLoginAdmin(HttpServletResponse response) {
		Cookie cookie = new Cookie(mgr_cookie_name, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 获得管理员用户状态
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAdminLogined(HttpServletRequest request) {
		Cookie cookie = ServletUtil.getCookie(request, mgr_cookie_name);
		if (cookie == null) {
			return false;
		}
		String v = decodeValue(cookie.getValue());
		if (v == null) {
			return false;
		}
		if (v.equals(key_admin_login)) {
			return true;
		}
		return false;
	}

	public static String encodeValue(String pwd) {
		return pwddesUtil.encrypt(pwd);
	}

	public static String decodeValue(String enc_pwd) {
		return pwddesUtil.decrypt(enc_pwd);
	}

	public static void main(String[] args) {
		P.println(encodeValue("admin"));
		P.println(isLogined("admin", "admin"));
	}
}