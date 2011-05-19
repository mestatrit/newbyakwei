package cactus.util.oauth.v1_0;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求token
 * 
 * @author akwei
 */
public class RequestToken {

	/**
	 * A value used by the Consumer to obtain authorization from the User, and
	 * exchanged for an Access Token
	 */
	private String token;

	/**
	 * A secret used by the Consumer to establish ownership of a given Token.
	 */
	private String tokenSecret;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	/**
	 * 根据开发者账号与回调url获得授权请求对象
	 * 
	 * @param oauthAppInfo
	 * @param back_url
	 * @return
	 */
	public RequestToken getRequestToken(OAuthAppInfo oauthAppInfo,
			String back_url) {
		// 生成http header请求参数
		List<Parameter> list = new ArrayList<Parameter>();
		return null;
	}
}