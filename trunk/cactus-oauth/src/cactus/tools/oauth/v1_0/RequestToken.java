package cactus.tools.oauth.v1_0;

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
}