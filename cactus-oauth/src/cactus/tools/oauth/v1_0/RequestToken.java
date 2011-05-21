package cactus.tools.oauth.v1_0;

import java.util.List;

import cactus.tools.httpclient.HttpData;
import cactus.tools.httpclient.HttpHelperException;
import cactus.tools.oauth.v1_0.exception.AccessTokenException;
import cactus.tools.oauth.v1_0.exception.RequestTokenException;

/**
 * 请求token
 * 
 * @author akwei
 */
public class RequestToken extends Token {

	private OAuthHelper oAuthHelper;

	private AppOAuthInfo appOAuthInfo;

	public RequestToken(String queryString, OAuthHelper oAuthHelper,
			AppOAuthInfo appOAuthInfo) throws RequestTokenException {
		this.queryString = queryString;
		this.oAuthHelper = oAuthHelper;
		this.appOAuthInfo = appOAuthInfo;
		String[] arr = queryString.split("&");
		for (String s : arr) {
			if (s.startsWith(oauth_token_flag)) {
				this.token = s.substring(12);
			}
			if (s.startsWith(oauth_token_secret_flag)) {
				this.tokenSecret = s.substring(19);
			}
		}
		this.validate();
	}

	public RequestToken(String token, String tokenSecret,
			OAuthHelper oAuthHelper, AppOAuthInfo appOAuthInfo)
			throws RequestTokenException {
		super();
		this.token = token;
		this.tokenSecret = tokenSecret;
		this.validate();
		this.oAuthHelper = oAuthHelper;
		this.appOAuthInfo = appOAuthInfo;
	}

	public String getCurrentUserAuthorizationURL(List<Parameter> list) {
		StringBuilder sb = new StringBuilder(this.appOAuthInfo
				.getUserAuthorizationURL());
		if (sb.indexOf("?") != -1) {
			sb.append("&");
		}
		else {
			sb.append("?");
		}
		sb.append("oauth_token=").append(this.getToken());
		if (list != null) {
			for (Parameter o : list) {
				sb.append("&").append(OAuthUtil.encoder(o.getName())).append(
						"=").append(OAuthUtil.encoder(o.getValue()));
			}
		}
		return sb.toString();
	}

	public AccessToken getAccessToken(String verifier)
			throws HttpHelperException, AccessTokenException {
		List<Parameter> list = ParameterUtil.createParametersForAccessToken(
				this.appOAuthInfo, ParameterUtil.HTTP_METHOD_POST, getToken(),
				getTokenSecret(), verifier, ParameterUtil
						.createOauthTimestamp(), ParameterUtil
						.createOauthNonce());
		HttpData httpData = new HttpData();
		for (Parameter o : list) {
			httpData.add(o.getName(), o.getValue());
		}
		String respValue = this.oAuthHelper.getHttpHelper().doPostResultString(
				this.appOAuthInfo.getAccessTokenURL(), httpData);
		return new AccessToken(respValue);
	}

	private void validate() throws RequestTokenException {
		if (OAuthUtil.isEmpty(this.token)) {
			throw new RequestTokenException("oauth_token must be not empty");
		}
		if (OAuthUtil.isEmpty(this.tokenSecret)) {
			throw new RequestTokenException(
					"oauth_token_secret must be not empty");
		}
	}
}