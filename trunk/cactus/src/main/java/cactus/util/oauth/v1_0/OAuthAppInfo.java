package cactus.util.oauth.v1_0;

/**
 * 开发者账号与密码信息
 * 
 * @author akwei
 */
public class OAuthAppInfo {

	private String appKey;

	private String appSecret;

	private String requestTokenURL;

	public void setRequestTokenURL(String requestTokenURL) {
		this.requestTokenURL = requestTokenURL;
	}

	public String getRequestTokenURL() {
		return requestTokenURL;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
}
