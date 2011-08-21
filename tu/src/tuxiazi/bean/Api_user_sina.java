package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import tuxiazi.dao.Api_user_sinaDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 新浪用户
 * 
 * @author akwei
 */
@Table(name = "api_user_sina", partitionClass = TuxiaziDbPartitionHelper.class)
public class Api_user_sina {

	public Api_user_sina() {
	}

	public Api_user_sina(long userid, SinaUserFromAPI sinaUserFromAPI) {
		this.userid = userid;
		this.access_token = sinaUserFromAPI.getAccess_token();
		this.token_secret = sinaUserFromAPI.getToken_secret();
	}

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

	public void save() {
		Api_user_sinaDao dao = (Api_user_sinaDao) HaloUtil
				.getBean("api_user_sinaDao");
		dao.save(this);
	}

	public void update() {
		Api_user_sinaDao dao = (Api_user_sinaDao) HaloUtil
				.getBean("api_user_sinaDao");
		dao.update(this);
	}
}