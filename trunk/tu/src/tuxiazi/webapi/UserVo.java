package tuxiazi.webapi;

import tuxiazi.bean.Api_user_sina;
import weibo4j.User;

public class UserVo {

	private weibo4j.User weiboUser;

	private Api_user_sina apiUserSina;

	public UserVo(User weiboUser, Api_user_sina apiUserSina) {
		this.weiboUser = weiboUser;
		this.apiUserSina = apiUserSina;
	}

	public weibo4j.User getWeiboUser() {
		return weiboUser;
	}

	public Api_user_sina getApiUserSina() {
		return apiUserSina;
	}
}