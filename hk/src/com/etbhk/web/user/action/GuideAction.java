package com.etbhk.web.user.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import weibo4j.Configuration;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;

import com.etbhk.util.BaseTaoBaoAction;
import com.hk.bean.taobao.Tb_Follow;
import com.hk.bean.taobao.Tb_Sina_User;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.Tb_FollowService;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.SinaUtil;

@Component("/tb/op/guide")
public class GuideAction extends BaseTaoBaoAction {

	private final Log log = LogFactory.getLog(GuideAction.class);

	@Autowired
	private Tb_UserService tb_UserService;

	@Autowired
	private Tb_FollowService tb_FollowService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-30
	 */
	public String follow(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			return this.getWebJsp("reg/follow.jsp");
		}
		return this.onSuccess(req, "ok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-30
	 */
	public String bindapp(HkRequest req, HkResponse resp) {
		Tb_User loginTbUser = this.getLoginTb_User(req);
		List<Tb_User_Api> list = this.tb_UserService
				.getTb_User_ApiByUserid(loginTbUser.getUserid());
		for (Tb_User_Api o : list) {
			if (o.getReg_source() == Tb_User_Api.REG_SOURCE_SINA) {
				req.setAttribute("sina_binded", true);
			}
			else if (o.getReg_source() == Tb_User_Api.REG_SOURCE_DOUBAN) {
				req.setAttribute("douban_binded", true);
			}
		}
		return this.getWebJsp("reg/bindapp.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-30
	 */
	public String findsinafriend(HkRequest req, HkResponse resp) {
		Tb_User loginTbUser = this.getLoginTb_User(req);
		Tb_User_Api sina_TbUserApi = this.tb_UserService.getTb_User_Api(
				loginTbUser.getUserid(), Tb_User_Api.REG_SOURCE_SINA);
		if (sina_TbUserApi != null) {
			Weibo weibo = new Weibo();
			weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
					Configuration.getOAuthConsumerSecret());
			weibo.setOAuthAccessToken(sina_TbUserApi.getAccess_token(),
					sina_TbUserApi.getToken_secret());
			try {
				User sina_user = SinaUtil.getUser(sina_TbUserApi
						.getAccess_token(), sina_TbUserApi.getToken_secret(),
						sina_TbUserApi.getUid());
				if (sina_user != null) {
					req.setAttribute("sina_user", sina_user);
					List<Long> idList = SinaUtil.getFriendIdList(sina_TbUserApi
							.getAccess_token(), sina_TbUserApi
							.getToken_secret(), sina_TbUserApi.getUid());
					List<Tb_Sina_User> tb_sina_userlist = this.tb_UserService
							.getTb_Sina_UserListInId(idList, true);
					if (tb_sina_userlist.size() == 0) {
						return "r:/tb/op/guide_bindapp";
					}
					req.setAttribute("tb_sina_userlist", tb_sina_userlist);
				}
			}
			catch (WeiboException e) {
				req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
				log.error("sina app error ======");
				log.error(e.getMessage());
				log.error("sina app error end ======");
			}
		}
		return this.getWebJsp("reg/findsinafriend.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-30
	 */
	public String followsinafriend(HkRequest req, HkResponse resp) {
		Tb_User loginTbUser = this.getLoginTb_User(req);
		long[] userid = req.getLongs("userid");
		if (userid != null) {
			for (long friendid : userid) {
				Tb_Follow tbFollow = new Tb_Follow();
				tbFollow.setUserid(loginTbUser.getUserid());
				tbFollow.setFriendid(friendid);
				this.tb_FollowService.createTb_Follow(tbFollow, false);
			}
		}
		return this.onSuccess(req, "followok", null);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 *         2010-8-30
	 */
	public String bindsina(HkRequest req, HkResponse resp) {
		Weibo weibo = new Weibo();
		weibo.setOAuthConsumer(Configuration.getOAuthConsumerKey(),
				Configuration.getOAuthConsumerSecret());
		String back_url = "http://" + req.getServerName()
				+ "/tb/op/guide_bindsina?ch=1";
		RequestToken requestToken = null;
		try {
			requestToken = weibo.getOAuthRequestToken(back_url);
		}
		catch (Exception e) {
			req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
			log.error("sina app error ======");
			log.error(e.getMessage());
			log.error("sina app error end ======");
			return "r:/tb/op/guide_bindapp";
		}
		if (this.isForwardPage(req)) {
			this.setOAuth_sina_Cookie(req, resp, requestToken);
			return "r:" + requestToken.getAuthorizationURL();
		}
		try {
			String token = this.getOauth_sina_requestToken(req);
			String tokenSecret = this.getOauth_sina_requestTokenSecret(req);
			AccessToken accessToken = weibo.getOAuthAccessToken(token,
					tokenSecret, req.getString("oauth_verifier"));
			this.clearOauth_sina_cookie(req, resp);
			if (accessToken == null) {
				req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
				return "r:/tb/op/guide_bindapp";
			}
			Tb_User loginTbUser = this.getLoginTb_User(req);
			String uid = accessToken.getUserId() + "";
			Tb_Sina_User tbSinaUser = this.tb_UserService
					.getTb_Sina_User(accessToken.getUserId());
			if (tbSinaUser != null) {
				req.setSessionText(String.valueOf(Err.API_SINA_USER_EXIST));
				return "r:/tb/op/guide_bindapp";
			}
			Tb_User_Api tbUserApi = this.tb_UserService.getTb_User_Api(
					loginTbUser.getUserid(), Tb_User_Api.REG_SOURCE_SINA);
			if (tbUserApi == null) {
				tbUserApi = new Tb_User_Api();
			}
			tbUserApi.setUserid(loginTbUser.getUserid());
			tbUserApi.setUid(uid);
			tbUserApi.setReg_source(Tb_User_Api.REG_SOURCE_SINA);
			tbUserApi.setAccess_token(accessToken.getToken());
			tbUserApi.setToken_secret(accessToken.getTokenSecret());
			this.tb_UserService.saveTb_User_Api(tbUserApi);
			return "r:/tb/op/guide_findsinafriend";
		}
		catch (WeiboException e) {
			req.setSessionText(String.valueOf(Err.API_SINA_APP_TIMEOUT));
			log.error("sina app error ======");
			log.error(e.getMessage());
			log.error("sina app error end ======");
			return "r:/tb/op/guide_bindapp";
		}
	}
}