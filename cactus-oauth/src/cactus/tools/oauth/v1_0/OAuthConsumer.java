package cactus.tools.oauth.v1_0;

import java.util.List;

import cactus.tools.httpclient.HttpUtil;

/**
 * @author akwei
 */
public class OAuthConsumer {

	private OAuthAppInfo oAuthAppInfo;

	private HttpUtil httpUtil;

	public void setHttpUtil(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}

	public HttpUtil getHttpUtil() {
		return httpUtil;
	}

	public void setoAuthAppInfo(OAuthAppInfo oAuthAppInfo) {
		this.oAuthAppInfo = oAuthAppInfo;
	}

	public OAuthAppInfo getoAuthAppInfo() {
		return oAuthAppInfo;
	}

	/**
	 * 根据开发者账号与回调url获得授权请求对象
	 * 
	 * @param oauthAppInfo
	 * @param callback_url
	 * @return
	 * @throws Exception
	 */
	public RequestToken getRequestToken(OAuthAppInfo info, String callback_url,
			List<Parameter> parameters) throws Exception {
		// 生成http header请求参数
		List<Parameter> list = OAuthUtil.buildParameterForGetRequestToken(
				OAuthUtil.HTTP_METHOD_GET, callback_url, info, parameters,
				OAuthUtil.createOauthTimestamp(), OAuthUtil.createOauthNonce());
		String url = OAuthUtil.createUrlForGetRequestToken(info, list);
		String respValue = httpUtil.doGet(url);
		return null;
	}
}
