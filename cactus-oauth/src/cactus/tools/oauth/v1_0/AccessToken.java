package cactus.tools.oauth.v1_0;

/**
 * 用户授权后的访问权限token
 * 
 * @author akwei
 */
public class AccessToken {

	/**
	 * A value used by the Consumer to gain access to the Protected Resources on
	 * behalf of the User, instead of using the User’s Service Provider
	 * credentials
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
}