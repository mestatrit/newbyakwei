package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.util.P;
import halo.util.ResourceConfig;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.SinaUtil;
import weibo4j.User;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

@Component("/api/auth")
public class AuthAction extends BaseApiAction {

	public static int COOKIE_MAXAGE = 1209600;

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private Api_user_sinaDao api_user_sinaDao;

	private String getServerUrl(HkRequest req) {
		StringBuffer sb = new StringBuffer("http://");
		sb.append(req.getServerName());
		int port = req.getLocalPort();
		if (port != 80) {
			sb.append(":");
			sb.append(port);
		}
		return sb.toString();
	}

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		if (this.isForwardPage(req)) {
			String back_url = this.getServerUrl(req) + req.getContextPath()
					+ "/api/auth?ch=1&api_type=" + Api_user.API_TYPE_SINA
					+ "&return_url="
					+ DataUtil.urlEncoder(req.getString("back_url"));
			RequestToken requestToken = SinaUtil.getRequestToken(back_url);
			this.setOAuth_sina_Cookie(req, resp, requestToken);
			return "r:" + requestToken.getAuthorizationURL();
		}
		try {
			AccessToken accessToken = SinaUtil.getAccessToken(
					this.getOauth_sina_requestToken(req),
					this.getOauth_sina_requestTokenSecret(req),
					req.getString("oauth_verifier"));
			String uid = accessToken.getUserId() + "";
			User sina_user = SinaUtil.getUser(accessToken.getToken(),
					accessToken.getTokenSecret(), uid);
			if (sina_user == null) {
				resp.sendHtml(this.getErrMsg(Err.API_NO_SINA_USER));
				return null;
			}
			Api_user_sina apiUserSina = this.api_user_sinaDao.getById(sina_user
					.getId());
			if (apiUserSina == null) {
				SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI(
						accessToken.getToken(), accessToken.getTokenSecret(),
						accessToken.getUserId(), sina_user.getScreenName(),
						sina_user.getProfileBackgroundImageUrl().toString());
				tuxiazi.bean.User user = this.userService
						.createUserFromSina(sinaUserFromAPI);
				apiUserSina = user.getApi_user_sina();
			}
			else {
				apiUserSina.setAccess_token(accessToken.getToken());
				apiUserSina.setToken_secret(accessToken.getTokenSecret());
				this.userService.updateApi_user_sina(apiUserSina);
				tuxiazi.bean.User user = this.userDao.getById(apiUserSina
						.getUserid());
				if (user != null) {
					user.setNick(sina_user.getScreenName());
					user.setHead_path(sina_user.getProfileImageURL().toString());
					this.userService.update(user);
				}
			}
			this.clearOauth_sina_cookie(req, resp);
			String return_url = req.getString("return_url");
			String v = "r:" + return_url;
			if (return_url.indexOf("?") != -1) {
				v += "&";
			}
			else {
				v += "?";
			}
			v += "access_token=" + accessToken.getToken() + "&token_secret="
					+ accessToken.getTokenSecret() + "&api_type="
					+ Api_user.API_TYPE_SINA + "&userid="
					+ apiUserSina.getUserid() + "&login_nick="
					+ DataUtil.urlEncoder(sina_user.getScreenName());
			return v;
		}
		catch (WeiboException e) {
			resp.sendHtml(this.getErrMsg(Err.API_SINA_ERR));
			resp.sendHtml(ResourceConfig.getTextFromResource("err",
					String.valueOf(Err.API_SINA_ERR)));
			return null;
		}
	}

	protected void setOAuth_sina_Cookie(HkRequest req, HkResponse resp,
			RequestToken requestToken) {
		Cookie cookie_sina_requestToken = new Cookie("sina_requestToken",
				requestToken.getToken());
		cookie_sina_requestToken.setPath("/");
		cookie_sina_requestToken.setDomain(req.getServerName());
		cookie_sina_requestToken.setMaxAge(COOKIE_MAXAGE);
		resp.addCookie(cookie_sina_requestToken);
		Cookie cookie_sina_requestTokenSecret = new Cookie(
				"sina_requestTokenSecret", requestToken.getTokenSecret());
		cookie_sina_requestTokenSecret.setPath("/");
		cookie_sina_requestTokenSecret.setDomain(req.getServerName());
		cookie_sina_requestTokenSecret.setMaxAge(COOKIE_MAXAGE);
		resp.addCookie(cookie_sina_requestTokenSecret);
	}

	protected void clearOauth_sina_cookie(HkRequest req, HkResponse resp) {
		Cookie cookie_sina_requestToken = new Cookie("sina_requestToken", "");
		cookie_sina_requestToken.setPath("/");
		cookie_sina_requestToken.setDomain(req.getServerName());
		cookie_sina_requestToken.setMaxAge(0);
		resp.addCookie(cookie_sina_requestToken);
		Cookie cookie_sina_requestTokenSecret = new Cookie(
				"sina_requestTokenSecret", "");
		cookie_sina_requestTokenSecret.setPath("/");
		cookie_sina_requestTokenSecret.setDomain(req.getServerName());
		cookie_sina_requestTokenSecret.setMaxAge(0);
		resp.addCookie(cookie_sina_requestTokenSecret);
	}

	protected String getOauth_sina_requestToken(HkRequest req) {
		Cookie cookie_sina_requestToken = req.getCookie("sina_requestToken");
		if (cookie_sina_requestToken != null) {
			return cookie_sina_requestToken.getValue();
		}
		return "";
	}

	protected String getOauth_sina_requestTokenSecret(HkRequest req) {
		Cookie cookie_sina_requestTokenSecret = req
				.getCookie("sina_requestTokenSecret");
		if (cookie_sina_requestTokenSecret != null) {
			return cookie_sina_requestTokenSecret.getValue();
		}
		return "";
	}

	public static void main(String[] args) {
		P.println(DataUtil.urlEncoder("tuxiazi://com.tuxiazi.txz"));
	}
}