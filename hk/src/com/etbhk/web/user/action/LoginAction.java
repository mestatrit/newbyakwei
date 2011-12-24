package com.etbhk.web.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weibo4j.User;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

import com.etbhk.util.BaseTaoBaoAction;
import com.etbhk.util.TbHkWebUtil;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.SinaUtil;

@Component("/tb/login")
public class LoginAction extends BaseTaoBaoAction {

	@Autowired
	private Tb_UserService tb_UserService;

	public String execute(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			String input = TbHkWebUtil.getLogin_Input(req);
			req.setAttribute("input", input);
			return this.getWebJsp("user/login.jsp");
		}
		Tb_User tbUser = this.tb_UserService.getTb_UserByEmail(req
				.getHtmlRow("input"));
		if (tbUser == null) {
			return this.onError(req, Err.TB_USER_LOGIN_INPUT_ERROR, "loginerr",
					null);
		}
		if (!tbUser.checkPwd(req.getHtmlRow("pwd"))) {
			return this.onError(req, Err.TB_USER_LOGIN_PWD_ERROR, "loginerr",
					null);
		}
		tbUser.setLogin_flg(Tb_User_Api.REG_SOURCE_LOCAL);
		this.tb_UserService.updateLoginInfoUserid(tbUser.getUserid(), tbUser
				.getApi_pic_path(), tbUser.getApiinfo(),
				Tb_User_Api.REG_SOURCE_LOCAL);
		TbHkWebUtil.setLoginUser(req, resp, tbUser, req.getHtmlRow("input"));
		return this.onSuccess(req, "loginok", null);
	}

	public String loginsina(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			String back_url = "http://" + req.getServerName()
					+ "/tb/login_loginsina?ch=1&return_url="
					+ req.getEncodeReturnUrl();
			try {
				RequestToken requestToken = SinaUtil.getRequestToken(back_url);
				this.setOAuth_sina_Cookie(req, resp, requestToken);
				return "r:" + requestToken.getAuthorizationURL();
			}
			catch (WeiboException e) {
				req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
				return "r:/tb/login";
			}
		}
		try {
			AccessToken accessToken = SinaUtil.getAccessToken(this
					.getOauth_sina_requestToken(req), this
					.getOauth_sina_requestTokenSecret(req), req
					.getString("oauth_verifier"));
			req.setSessionValue("accessToken", accessToken);
			String uid = accessToken.getUserId() + "";
			User sina_user = SinaUtil.getUser(accessToken.getToken(),
					accessToken.getTokenSecret(), uid);
			if (sina_user == null) {
				return "r:/tb/login";
			}
			this.clearOauth_sina_cookie(req, resp);
			return "r:/tb/login_bindsina?return_url="
					+ req.getEncodeReturnUrl();
		}
		catch (WeiboException e) {
			req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
			return "r:/tb/login?return_url=" + req.getEncodeReturnUrl();
		}
	}

	public String bindsina(HkRequest req, HkResponse resp) {
		AccessToken accessToken = (AccessToken) req
				.getSessionValue("accessToken");
		if (accessToken == null) {
			return "r:/tb/login?return_url=" + req.getEncodeReturnUrl();
		}
		String uid = accessToken.getUserId() + "";
		User sina_user = null;
		try {
			sina_user = SinaUtil.getUser(accessToken.getToken(), accessToken
					.getTokenSecret(), uid);
			if (sina_user == null) {
				return "r:/tb/login?return_url=" + req.getEncodeReturnUrl();
			}
		}
		catch (WeiboException e) {
			req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
			return "r:/tb/login?return_url=" + req.getEncodeReturnUrl();
		}
		// 查看用户是否已经注册
		Tb_User_Api tbUserApi = this.tb_UserService
				.getTb_User_ApiByUidAndReg_source(sina_user.getId() + "",
						Tb_User_Api.REG_SOURCE_SINA);
		if (tbUserApi != null) {// 已经绑定，登录成功
			Tb_User tbUser = this.tb_UserService.getTb_User(tbUserApi
					.getUserid());
			this.initSina_User(sina_user, tbUser);
			TbHkWebUtil.setLoginUser(req, resp, tbUser, "");
			if (DataUtil.isNotEmpty(req.getReturnUrl())) {
				return "r:" + req.getReturnUrl();
			}
			return "r:/tb/user?userid=" + tbUser.getUserid();
		}
		// 没有账号，创建新账号
		Tb_User tbUser = new Tb_User();
		String nick = sina_user.getScreenName();
		Tb_User db_tbUser = this.tb_UserService.getTb_UserByNick(nick);
		boolean nick_duplicate = false;
		if (db_tbUser != null) {
			nick_duplicate = true;
		}
		if (nick_duplicate) {
			nick = sina_user.getScreenName() + "(新浪微博)";
			db_tbUser = this.tb_UserService.getTb_UserByNick(nick);
			if (db_tbUser != null) {
				nick_duplicate = true;
			}
			else {
				nick_duplicate = false;
			}
		}
		if (nick_duplicate) {
			nick = sina_user.getScreenName() + "(新浪微博)" + DataUtil.getRandom(4);
			db_tbUser = this.tb_UserService.getTb_UserByNick(nick);
			if (db_tbUser != null) {
				nick_duplicate = true;
			}
			else {
				nick_duplicate = false;
			}
		}
		// 再重复，就转为数字id
		tbUser.setNick(nick);
		tbUser.setApi_pic_path(sina_user.getProfileImageURL().toString());
		tbUser.setReg_source(Tb_User_Api.REG_SOURCE_SINA);
		tbUser.setLogin_flg(Tb_User_Api.REG_SOURCE_SINA);
		tbUser.setSinaFans_count(sina_user.getFollowersCount());
		tbUser.setSinaFriend_count(sina_user.getFriendsCount());
		tbUser.setSinaWeibo_count(sina_user.getStatusesCount());
		tbUser.setSinaLocation(sina_user.getLocation());
		if (sina_user.isVerified()) {
			tbUser.setSinaVerified(true);
		}
		tbUserApi = new Tb_User_Api();
		tbUserApi.setAccess_token(accessToken.getToken());
		tbUserApi.setToken_secret(accessToken.getTokenSecret());
		tbUserApi.setReg_source(Tb_User_Api.REG_SOURCE_SINA);
		tbUserApi.setUid(accessToken.getUserId() + "");
		this.tb_UserService.createTb_User(tbUser, tbUserApi);
		TbHkWebUtil.setLoginUser(req, resp, tbUser, "");
		if (DataUtil.isNotEmpty(req.getReturnUrl())) {
			return "r:" + req.getReturnUrl();
		}
		return "r:/tb/user?userid=" + tbUser.getUserid();
	}

	private void initSina_User(User sina_user, Tb_User tbUser) {
		tbUser.setApi_pic_path(sina_user.getProfileImageURL().toString());
		tbUser.setLogin_flg(Tb_User_Api.REG_SOURCE_SINA);
		tbUser.setSinaFans_count(sina_user.getFollowersCount());
		tbUser.setSinaFriend_count(sina_user.getFriendsCount());
		tbUser.setSinaWeibo_count(sina_user.getStatusesCount());
		tbUser.setSinaLocation(sina_user.getLocation());
		tbUser.setSina_nick(sina_user.getScreenName());
		if (sina_user.isVerified()) {
			tbUser.setSinaVerified(true);
		}
		this.tb_UserService.updateTb_User(tbUser);
	}
}