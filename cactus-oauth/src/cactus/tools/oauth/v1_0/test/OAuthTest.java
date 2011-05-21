package cactus.tools.oauth.v1_0.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cactus.tools.oauth.v1_0.AccessToken;
import cactus.tools.oauth.v1_0.AppOAuthInfo;
import cactus.tools.oauth.v1_0.OAuthConsumer;
import cactus.tools.oauth.v1_0.OAuthHelper;
import cactus.tools.oauth.v1_0.RequestToken;

public class OAuthTest {

	private OAuthHelper oAuthHelper;

	private AppOAuthInfo appOAuthInfo;

	@Before
	public void init() {
		appOAuthInfo = new AppOAuthInfo();
		appOAuthInfo
				.setRequestTokenURL("http://api.t.sina.com.cn/oauth/request_token");
		appOAuthInfo
				.setUserAuthorizationURL("http://api.t.sina.com.cn/oauth/authorize");
		appOAuthInfo
				.setAccessTokenURL("http://api.t.sina.com.cn/oauth/access_token");
		appOAuthInfo.setConsumerKey("2856451852");
		appOAuthInfo.setConsumerSecret("27a6cb8f62ca64b40fe9f3b546728e44");
		appOAuthInfo.setVersion("1.0");
		this.oAuthHelper = new OAuthHelper();
		this.oAuthHelper.setDebug(true);
	}

	// @Test
	public void createUrlForGetRequestToken() {
		OAuthConsumer oAuthConsumer = new OAuthConsumer(oAuthHelper,
				appOAuthInfo);
		String callback_url = "http://www.t.com";
		try {
			RequestToken requestToken = oAuthConsumer.getRequestToken(
					callback_url, null);
			System.out.println(requestToken.toString());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void createUrlForUserAuth() {
		OAuthConsumer oAuthConsumer = new OAuthConsumer(oAuthHelper,
				appOAuthInfo);
		try {
			RequestToken requestToken = oAuthConsumer.getRequestToken(null,
					null);
			System.out.println(requestToken.toString());
			String url = requestToken.getCurrentUserAuthorizationURL(null);
			System.out.println(url);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void createAccessToken() {
		String verifier = "642078";
		try {
			RequestToken requestToken = new RequestToken(
					"2d9d8b9dbacbc34f647490bfe48744d3",
					"f26bd53540309b25069e8a6c08869a89", oAuthHelper,
					appOAuthInfo);
			AccessToken accessToken = requestToken.getAccessToken(verifier);
			System.out.println(accessToken.toString());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
