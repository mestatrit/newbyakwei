package cactus.tools.oauth.v1_0.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cactus.tools.oauth.v1_0.AppOAuthInfo;
import cactus.tools.oauth.v1_0.OAuthConsumer;
import cactus.tools.oauth.v1_0.OAuthDataUtil;
import cactus.tools.oauth.v1_0.OAuthHelper;
import cactus.tools.oauth.v1_0.Parameter;
import cactus.tools.oauth.v1_0.RequestToken;

public class OAuthTest {

	private AppOAuthInfo info;

	@Before
	public void init() {
		info = new AppOAuthInfo();
		info.setRequestTokenURL("http://api.t.sina.com.cn/oauth/request_token");
		info.setConsumerKey("2856451852");
		info.setConsumerSecret("27a6cb8f62ca64b40fe9f3b546728e44");
		info.setVersion("1.0");
	}

	@Test
	public void testBuildParameterForGetRequestToken() {
		OAuthConsumer oAuthConsumer = new OAuthConsumer(info);
		String method = OAuthHelper.HTTP_METHOD_GET;
		String callback_url = "http://www.t.com";
		List<Parameter> otherParameters = null;
		String timestamp = OAuthDataUtil.createOauthTimestamp();
		String nonce = OAuthDataUtil.createOauthNonce();
		List<Parameter> list = oAuthConsumer.getParametersForRequestToken(
				method, callback_url, otherParameters, timestamp, nonce);
		for (Parameter p : list) {
			System.out.println(p.getName() + " | " + p.getValue());
		}
	}

	@Test
	public void createUrlForGetRequestToken() {
		OAuthConsumer oAuthConsumer = new OAuthConsumer(info);
		oAuthConsumer.setDebug(true);
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
}
