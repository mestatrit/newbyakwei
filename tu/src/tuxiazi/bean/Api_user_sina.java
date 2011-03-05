package tuxiazi.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 新浪用户
 * 
 * @author Administrator
 */
@Table(name = "api_user_sina")
public class Api_user_sina {

	/**
	 * 新浪用户id
	 */
	@Id
	private long sina_userid;

	/**
	 * 授权token,oauth认证使用
	 */
	@Column
	private String access_token;

	/**
	 * 授权token_secret,oauth认证使用
	 */
	@Column
	private String token_secret;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	private User user;

	public long getSina_userid() {
		return sina_userid;
	}

	public void setSina_userid(long sinaUserid) {
		sina_userid = sinaUserid;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public String getToken_secret() {
		return token_secret;
	}

	public void setToken_secret(String tokenSecret) {
		token_secret = tokenSecret;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}