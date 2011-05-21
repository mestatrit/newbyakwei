package cactus.tools.oauth.v1_0;

public abstract class Token {

	protected static final String oauth_token_flag = "oauth_token=";

	protected static final String oauth_token_secret_flag = "oauth_token_secret=";

	protected String token;

	protected String tokenSecret;

	protected String queryString;

	public String getQueryString() {
		return queryString;
	}

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

	@Override
	public String toString() {
		return "oauth_token=" + this.token + " | oauth_token_secret="
				+ this.tokenSecret;
	}
}