package cactus.tools.oauth.v1_0.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cactus.tools.oauth.v1_0.OAuthAppInfo;
import cactus.tools.oauth.v1_0.OAuthUtil;
import cactus.tools.oauth.v1_0.Parameter;

public class OAuthUtilTest {

	private OAuthAppInfo info;

	@Before
	public void init() {
		info = new OAuthAppInfo();
		info.setRequestTokenURL("http://api.t.sina.com.cn/oauth/request_token");
		info.setConsumerKey("2856451852");
		info.setConsumerSecret("27a6cb8f62ca64b40fe9f3b546728e44");
		info.setVersion("1.0");
	}

	@Test
	public void testBuildParameterForGetRequestToken() {
		String method = OAuthUtil.HTTP_METHOD_GET;
		String callback_url = "http://www.t.com";
		List<Parameter> otherParameters = null;
		String timestamp = OAuthUtil.createOauthTimestamp();
		String nonce = OAuthUtil.createOauthNonce();
		List<Parameter> list = OAuthUtil.buildParameterForGetRequestToken(
				method, callback_url, info, otherParameters, timestamp, nonce);
		for (Parameter p : list) {
			System.out.println(p.getName() + " | " + p.getValue());
		}
	}

	@Test
	public void createUrlForGetRequestToken() {
		String method = OAuthUtil.HTTP_METHOD_GET;
		String callback_url = "http://www.t.com";
		List<Parameter> otherParameters = null;
		String timestamp = OAuthUtil.createOauthTimestamp();
		String nonce = OAuthUtil.createOauthNonce();
		List<Parameter> list = OAuthUtil.buildParameterForGetRequestToken(
				method, callback_url, info, otherParameters, timestamp, nonce);
		String url = OAuthUtil.createUrlForGetRequestToken(info, list);
		System.out.println(url);
	}
}
