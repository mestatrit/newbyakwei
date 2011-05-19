package cactus.util.oauth.v1_0;

/**
 * 请求token
 * 
 * @author akwei
 */
public class RequestToken {

	private String token;

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
		return null;
	}
}