package cactus.tools.oauth.v1_0;

import java.util.List;

import cactus.tools.httpclient.HttpHelperException;
import cactus.tools.oauth.v1_0.exception.RequestTokenException;

/**
 * @author akwei
 */
public class OAuthConsumer extends OAuthHelper {

	public OAuthConsumer(AppOAuthInfo appOAuthInfo) {
		this.setAppOAuthInfo(appOAuthInfo);
	}

	/**
	 * 根据开发者账号与回调url获得授权请求对象
	 * 
	 * @param oauthAppInfo
	 * @param callback_url
	 * @return
	 * @throws HttpHelperException
	 * @throws RequestTokenException
	 */
	public RequestToken getRequestToken(String callback_url,
			List<Parameter> parameters) throws RequestTokenException,
			HttpHelperException {
		// 生成http header请求参数
		List<Parameter> list = getParametersForRequestToken(HTTP_METHOD_GET,
				callback_url, parameters, OAuthDataUtil.createOauthTimestamp(),
				OAuthDataUtil.createOauthNonce());
		String url = createUrlForGetRequestToken(list);
		if (this.isDebug()) {
			p("requestToken url : " + url);
		}
		String respValue = this.getAppOAuthInfo().getHttpHelper()
				.doGetResultString(url, null);
		if (this.isDebug()) {
			p("requestToken response from server : " + respValue);
		}
		RequestToken requestToken = new RequestToken(respValue);
		requestToken.setAppOAuthInfo(this.getAppOAuthInfo());
		return requestToken;
	}
}