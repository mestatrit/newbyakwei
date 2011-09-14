package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.util.P;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.UserDao;
import tuxiazi.svr.iface.UserService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;
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
			req.setSessionValue("sina_requestToken", requestToken);
			return "r:" + requestToken.getAuthorizationURL();
		}
		try {
			RequestToken requestToken = (RequestToken) req
					.getSessionValue("sina_requestToken");
			if (requestToken == null) {
				APIUtil.writeErr(resp, Err.API_REQUESTTOKEN_NOT_EXIST);
				return null;
			}
			AccessToken accessToken = SinaUtil.getAccessToken(
					requestToken.getToken(), requestToken.getTokenSecret(),
					req.getString("oauth_verifier"));
			String uid = accessToken.getUserId() + "";
			User sina_user = SinaUtil.getUser(accessToken.getToken(),
					accessToken.getTokenSecret(), uid);
			if (sina_user == null) {
				APIUtil.writeErr(resp, Err.API_NO_SINA_USER);
				return null;
			}
			Api_user_sina apiUserSina = this.api_user_sinaDao.getById(sina_user
					.getId());
			if (apiUserSina == null) {
				SinaUserFromAPI sinaUserFromAPI = new SinaUserFromAPI(
						accessToken.getToken(), accessToken.getTokenSecret(),
						accessToken.getUserId(), sina_user.getScreenName(),
						sina_user.getProfileBackgroundImageUrl().toString());
				tuxiazi.bean.User user = this.userService.createUserFromSina(
						sinaUserFromAPI, true);
				apiUserSina = this.api_user_sinaDao.getByUserid(user
						.getUserid());
			} else {
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
			req.removeSessionvalue("sina_requestToken");
			String return_url = req.getString("return_url");
			String v = "r:" + return_url;
			if (return_url.indexOf("?") != -1) {
				v += "&";
			} else {
				v += "?";
			}
			// 认证成功后，返回userid,nick,headpic
			v += "access_token="
					+ accessToken.getToken()
					+ "&token_secret="
					+ accessToken.getTokenSecret()
					+ "&api_type="
					+ Api_user.API_TYPE_SINA
					+ "&userid="
					+ apiUserSina.getUserid()
					+ "&login_nick="
					+ DataUtil.urlEncoder(sina_user.getScreenName())
					+ "&head="
					+ DataUtil.urlEncoder(sina_user.getProfileImageURL()
							.toString());
			return v;
		} catch (WeiboException e) {
			APIUtil.writeErr(resp, Err.API_SINA_ERR);
			return null;
		}
	}

	public static void main(String[] args) {
		P.println(DataUtil.urlEncoder("tuxiazi://com.tuxiazi.txz"));
	}
}