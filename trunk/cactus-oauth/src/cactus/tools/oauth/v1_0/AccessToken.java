package cactus.tools.oauth.v1_0;

import cactus.tools.oauth.v1_0.exception.AccessTokenException;

/**
 * 用户授权后的访问权限token
 * 
 * @author akwei
 */
public class AccessToken extends Token {

	public AccessToken(String queryString) throws AccessTokenException {
		this.queryString = queryString;
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

	private void validate() throws AccessTokenException {
		if (OAuthUtil.isEmpty(this.token)) {
			throw new AccessTokenException("oauth_token must be not empty");
		}
		if (OAuthUtil.isEmpty(this.tokenSecret)) {
			throw new AccessTokenException(
					"oauth_token_secret must be not empty");
		}
	}
}