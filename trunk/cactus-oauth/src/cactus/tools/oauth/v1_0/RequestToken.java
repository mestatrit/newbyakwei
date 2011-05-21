package cactus.tools.oauth.v1_0;

import java.util.List;

import cactus.tools.oauth.v1_0.exception.RequestTokenException;

/**
 * 请求token
 * 
 * @author akwei
 */
public class RequestToken extends Token {

	public RequestToken(String queryString) throws RequestTokenException {
		String[] arr = queryString.split("&");
		for (String s : arr) {
			if (s.startsWith(OAuthDataUtil.oauth_token_flag)) {
				this.token = s.substring(11);
			}
			if (s.startsWith(OAuthDataUtil.oauth_token_secret_flag)) {
				this.tokenSecret = s.substring(18);
			}
		}
		this.validate();
	}

	public RequestToken(String token, String tokenSecret)
			throws RequestTokenException {
		super();
		this.token = token;
		this.tokenSecret = tokenSecret;
		this.validate();
	}

	public String getCurrentUserAuthorizationURL(List<Parameter> list) {
		StringBuilder sb = new StringBuilder(this.getAppOAuthInfo()
				.getRequestTokenURL());
		if (sb.indexOf("?") != -1) {
			sb.append("&");
		}
		else {
			sb.append("?");
		}
		sb.append("oauth_token=").append(this.getToken());
		if (list != null) {
			for (Parameter o : list) {
				sb.append("&").append(OAuthDataUtil.encoder(o.getName()))
						.append("=")
						.append(OAuthDataUtil.encoder(o.getValue()));
			}
		}
		return sb.toString();
	}

	private void validate() throws RequestTokenException {
		if (OAuthDataUtil.isEmpty(this.token)) {
			throw new RequestTokenException("oauth_token must be not empty");
		}
		if (OAuthDataUtil.isEmpty(this.tokenSecret)) {
			throw new RequestTokenException(
					"oauth_token_secret must be not empty");
		}
	}

	@Override
	public String toString() {
		return "oauth_token" + this.token + " | oauth_token_secret"
				+ this.tokenSecret;
	}
}