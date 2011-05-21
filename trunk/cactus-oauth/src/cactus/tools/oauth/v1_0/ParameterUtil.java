package cactus.tools.oauth.v1_0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class ParameterUtil {

	private static Random RAND = new Random();

	public static final String HMAC_SHA1 = "HmacSHA1";

	public static final String oauth_consumer_key = "oauth_consumer_key";

	public static final String oauth_token = "oauth_token";

	public static final String oauth_signature_method = "oauth_signature_method";

	public static final String oauth_signature = "oauth_signature";

	public static final String oauth_timestamp = "oauth_timestamp";

	public static final String oauth_nonce = "oauth_nonce";

	public static final String oauth_version = "oauth_version";

	public static final String oauth_callback = "oauth_callback";

	public static final String oauth_verifier = "oauth_verifier";

	public static final String HTTP_METHOD_GET = "GET";

	public static final String HTTP_METHOD_POST = "POST";

	public static final String HTTP_METHOD_DELETE = "DELETE";

	public static final String HTTP_METHOD_PUT = "PUT";

	/**
	 * 获得accessToken请求的参数列表
	 * 
	 * @param appOAuthInfo
	 * @param method
	 * @param request_auth_token
	 * @param request_token_secret
	 * @param oauth_verifier
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static List<Parameter> createParametersForAccessToken(
			AppOAuthInfo appOAuthInfo, String method,
			String request_auth_token, String request_token_secret,
			String oauth_verifier, String timestamp, String nonce) {
		List<Parameter> list = buildDefParameterForGetAccessToken(appOAuthInfo,
				request_auth_token, oauth_verifier, timestamp, nonce);
		String norParamString = normalizeRequestParameters(list);
		String fmtURL = constructRequestURL(appOAuthInfo.getAccessTokenURL());
		String data = generatingSignatureBaseString(method, fmtURL,
				norParamString);
		String signatureValue = generateSignature(data, appOAuthInfo
				.getConsumerSecret(), request_token_secret);
		list.add(new Parameter(oauth_signature, signatureValue));
		return list;
	}

	/**
	 * 获得requestToken请求的参数列表
	 * 
	 * @param appOAuthInfo
	 * @param method
	 * @param callback_url
	 * @param otherParameters
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static List<Parameter> createParametersForRequestToken(
			AppOAuthInfo appOAuthInfo, String method, String callback_url,
			List<Parameter> otherParameters, String timestamp, String nonce) {
		List<Parameter> list = buildDefParameterForGetRequestToken(
				appOAuthInfo, timestamp, nonce);
		if (callback_url != null && callback_url.length() > 0) {
			list.add(new Parameter(oauth_callback, callback_url));
		}
		if (otherParameters != null) {
			list.addAll(otherParameters);
		}
		String norParamString = normalizeRequestParameters(list);
		String fmtURL = constructRequestURL(appOAuthInfo.getRequestTokenURL());
		String data = generatingSignatureBaseString(method, fmtURL,
				norParamString);
		String signatureValue = generateSignature(data, appOAuthInfo
				.getConsumerSecret(), null);
		list.add(new Parameter(oauth_signature, signatureValue));
		return list;
	}

	private static List<Parameter> buildDefParameterForGetRequestToken(
			AppOAuthInfo info, String timestamp, String nonce) {
		List<Parameter> list = new ArrayList<Parameter>();
		list.add(new Parameter(oauth_consumer_key, info.getConsumerKey()));
		list.add(new Parameter(oauth_signature_method, info
				.getSignatureMethod()));
		list.add(new Parameter(oauth_timestamp, timestamp));
		list.add(new Parameter(oauth_nonce, nonce));
		list.add(new Parameter(oauth_version, info.getVersion()));
		return list;
	}

	private static List<Parameter> buildDefParameterForGetAccessToken(
			AppOAuthInfo info, String request_auth_token,
			String oauth_verifier, String timestamp, String nonce) {
		List<Parameter> list = new ArrayList<Parameter>();
		list.add(new Parameter(oauth_consumer_key, info.getConsumerKey()));
		list.add(new Parameter(oauth_token, request_auth_token));
		list.add(new Parameter(oauth_signature_method, info
				.getSignatureMethod()));
		list.add(new Parameter(oauth_timestamp, timestamp));
		list.add(new Parameter(oauth_nonce, nonce));
		list.add(new Parameter(oauth_version, info.getVersion()));
		list.add(new Parameter(oauth_verifier, oauth_verifier));
		return list;
	}

	private static String normalizeRequestParameters(List<Parameter> list) {
		Collections.sort(list);
		Parameter parameter = null;
		StringBuilder sb = new StringBuilder();
		int lastidx = list.size() - 1;
		for (int i = 0; i < list.size(); i++) {
			parameter = list.get(i);
			sb.append(OAuthUtil.encoder(parameter.getName())).append("=")
					.append(OAuthUtil.encoder(parameter.getValue()));
			if (i < lastidx) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	private static String constructRequestURL(String url) {
		int index = url.indexOf("?");
		String _url = null;
		if (-1 != index) {
			_url = url.substring(0, index);
		}
		else {
			_url = url;
		}
		int slashIndex = _url.indexOf("/", 8);
		String baseURL = _url.substring(0, slashIndex).toLowerCase();
		int colonIndex = baseURL.indexOf(":", 8);
		if (-1 != colonIndex) {
			if (baseURL.startsWith("http://") && baseURL.endsWith(":80")) {
				baseURL = baseURL.substring(0, colonIndex);
			}
			else if (baseURL.startsWith("https://") && baseURL.endsWith(":443")) {
				baseURL = baseURL.substring(0, colonIndex);
			}
		}
		_url = baseURL + _url.substring(slashIndex);
		return _url;
	}

	private static String generatingSignatureBaseString(String method,
			String fmtURL, String norParamString) {
		StringBuilder sb = new StringBuilder(method);
		sb.append("&").append(OAuthUtil.encoder(fmtURL)).append("&").append(
				OAuthUtil.encoder(norParamString));
		return sb.toString();
	}

	private static String generateSignature(String data, String consumerSecret,
			String tokenSecret) {
		String enc_tokenSecret = null;
		if (tokenSecret == null) {
			enc_tokenSecret = "";
		}
		else {
			enc_tokenSecret = OAuthUtil.encoder(tokenSecret);
		}
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(new SecretKeySpec(
					(OAuthUtil.encoder(consumerSecret) + "&" + enc_tokenSecret)
							.getBytes(), HMAC_SHA1));
			return new BASE64Encoder().encode(mac.doFinal(data.getBytes()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String createOauthTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String createOauthNonce() {
		return String.valueOf(System.currentTimeMillis() + RAND.nextInt());
	}
}
