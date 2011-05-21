package cactus.tools.oauth.v1_0;

import java.util.List;

import cactus.tools.httpclient.HttpHelperException;
import cactus.tools.oauth.v1_0.exception.RequestTokenException;

/**
 * @author akwei
 */
public class OAuthConsumer {

	private OAuthHelper oAuthHelper;

	private AppOAuthInfo appOAuthInfo;

	public OAuthConsumer(OAuthHelper oAuthHelper, AppOAuthInfo appOAuthInfo) {
		this.oAuthHelper = oAuthHelper;
		this.appOAuthInfo = appOAuthInfo;
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
		List<Parameter> list = ParameterUtil.createParametersForRequestToken(
				this.appOAuthInfo, ParameterUtil.HTTP_METHOD_GET, callback_url,
				parameters, ParameterUtil.createOauthTimestamp(), ParameterUtil
						.createOauthNonce());
		String url = OAuthUtil.createUrlForHttpGetMethod(this.appOAuthInfo
				.getRequestTokenURL(), list);
		if (this.oAuthHelper.isDebug()) {
			this.oAuthHelper.p("requestToken url : " + url);
		}
		String respValue = this.oAuthHelper.getHttpHelper().doGetResultString(
				url, null);
		if (this.oAuthHelper.isDebug()) {
			this.oAuthHelper.p("requestToken response from server : "
					+ respValue);
		}
		return new RequestToken(respValue, oAuthHelper, appOAuthInfo);
	}
}