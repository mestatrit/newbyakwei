package cactus.tools.oauth.v1_0;

public abstract class Token {

	private AppOAuthInfo appOAuthInfo;

	protected String token;

	protected String tokenSecret;

	public void setAppOAuthInfo(AppOAuthInfo appOAuthInfo) {
		this.appOAuthInfo = appOAuthInfo;
	}

	public AppOAuthInfo getAppOAuthInfo() {
		return appOAuthInfo;
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
}