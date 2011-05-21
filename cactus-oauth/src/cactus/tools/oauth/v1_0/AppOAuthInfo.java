package cactus.tools.oauth.v1_0;

/**
 * 开发者账号与密码信息
 * 
 * @author akwei
 */
public class AppOAuthInfo {

	private String consumerKey;

	private String consumerSecret;

	private String signatureMethod = "HMAC-SHA1";

	private String version = "v1.0";

	private String requestTokenURL;

	private String userAuthorizationURL;

	private String accessTokenURL;

	public void setRequestTokenURL(String requestTokenURL) {
		this.requestTokenURL = requestTokenURL;
	}

	public String getRequestTokenURL() {
		return requestTokenURL;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getUserAuthorizationURL() {
		return userAuthorizationURL;
	}

	public void setUserAuthorizationURL(String userAuthorizationURL) {
		this.userAuthorizationURL = userAuthorizationURL;
	}

	public String getAccessTokenURL() {
		return accessTokenURL;
	}

	public void setAccessTokenURL(String accessTokenURL) {
		this.accessTokenURL = accessTokenURL;
	}

	public String getSignatureMethod() {
		return signatureMethod;
	}

	public void setSignatureMethod(String signatureMethod) {
		this.signatureMethod = signatureMethod;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
