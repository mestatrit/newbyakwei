package cactus.util.oauth.v1_0;

/**
 * 用户授权后的访问权限token
 * 
 * @author akwei
 */
public class AccessToken {

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
}