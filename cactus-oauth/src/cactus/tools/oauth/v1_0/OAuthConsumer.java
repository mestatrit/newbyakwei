package cactus.tools.oauth.v1_0;

import java.util.List;

/**
 * @author akwei
 */
public class OAuthConsumer extends OAuthHelper {

	/**
	 * 根据开发者账号与回调url获得授权请求对象
	 * 
	 * @param oauthAppInfo
	 * @param callback_url
	 * @return
	 * @throws Exception
	 */
	public RequestToken getRequestToken(String callback_url,
			List<Parameter> parameters) throws Exception {
		// 生成http header请求参数
		List<Parameter> list = getParametersForRequestToken(
				OAuthHelper.HTTP_METHOD_GET, callback_url, parameters,
				OAuthDataUtil.createOauthTimestamp(), OAuthDataUtil
						.createOauthNonce());
		String url = createUrlForRequestToken(this.getAppOAuthInfo(), list);
		String respValue = this.getHttpUtil().doGet(url);
		return null;
	}
}
