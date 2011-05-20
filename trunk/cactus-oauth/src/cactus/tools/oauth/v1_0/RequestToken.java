package cactus.tools.oauth.v1_0;

import java.util.List;

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
	 * @param callback_url
	 * @return
	 */
	public RequestToken getRequestToken(OAuthAppInfo info, String callback_url,
			List<Parameter> parameters) {
		// 生成http header请求参数
		List<Parameter> list = OAuthUtil.buildParameterForGetRequestToken(
				OAuthUtil.HTTP_METHOD_GET, callback_url, info, parameters,
				OAuthUtil.createOauthTimestamp(), OAuthUtil.createOauthNonce());
		String url = OAuthUtil.createUrlForGetRequestToken(info, list);
		return null;
	}
}