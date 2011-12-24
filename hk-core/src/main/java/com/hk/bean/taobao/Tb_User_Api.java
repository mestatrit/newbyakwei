package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户绑定第三方api应用表
 * 
 * @author akwei
 */
@Table(name = "tb_user_api")
public class Tb_User_Api {

	public static final byte REG_SOURCE_LOCAL = 0;

	public static final byte REG_SOURCE_SINA = 1;

	public static final byte REG_SOURCE_DOUBAN = 2;

	@Id
	private long oid;

	@Column
	private long userid;

	@Column
	private String login_name;

	@Column
	private String login_pwd;

	@Column
	private byte reg_source;

	/**
	 * 请求token
	 */
	@Column
	private String access_token;

	/**
	 * 请求签名
	 */
	@Column
	private String token_secret;

	/**
	 * 用户api源网站的id
	 */
	@Column
	private String uid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String loginName) {
		login_name = loginName;
	}

	public String getLogin_pwd() {
		return login_pwd;
	}

	public void setLogin_pwd(String loginPwd) {
		login_pwd = loginPwd;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public byte getReg_source() {
		return reg_source;
	}

	public void setReg_source(byte regSource) {
		reg_source = regSource;
	}
}