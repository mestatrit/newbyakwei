package cactus.tools.oauth.v1_0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * @author akwei
 */
public class OAuthHelper {

	private boolean debug;

	public static final String HMAC_SHA1 = "HmacSHA1";

	public static final String oauth_consumer_key = "oauth_consumer_key";

	public static final String oauth_signature_method = "oauth_signature_method";

	public static final String oauth_signature = "oauth_signature";

	public static final String oauth_timestamp = "oauth_timestamp";

	public static final String oauth_nonce = "oauth_nonce";

	public static final String oauth_version = "oauth_version";

	public static final String oauth_callback = "oauth_callback";

	public static final String HTTP_METHOD_GET = "GET";

	public static final String HTTP_METHOD_POST = "POST";

	public static final String HTTP_METHOD_DELETE = "DELETE";

	public static final String HTTP_METHOD_PUT = "PUT";

	private AppOAuthInfo appOAuthInfo;

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setAppOAuthInfo(AppOAuthInfo appOAuthInfo) {
		this.appOAuthInfo = appOAuthInfo;
	}

	public AppOAuthInfo getAppOAuthInfo() {
		return appOAuthInfo;
	}

	public String createUrlForGetRequestToken(List<Parameter> list) {
		StringBuilder sb = new StringBuilder(this.appOAuthInfo
				.getRequestTokenURL());
		if (this.appOAuthInfo.getRequestTokenURL().indexOf("?") != -1) {
			sb.append("&");
		}
		else {
			sb.append("?");
		}
		for (Parameter o : list) {
			sb.append(o.getQueryString()).append("&");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public List<Parameter> getParametersForRequestToken(String method,
			String callback_url, List<Parameter> otherParameters,
			String timestamp, String nonce) {
		List<Parameter> list = buildDefaultParameterForGetRequestToken(
				this.appOAuthInfo, callback_url, timestamp, nonce);
		if (otherParameters != null) {
			list.addAll(otherParameters);
		}
		String norParamString = normalizeRequestParameters(list);
		String fmtURL = constructRequestURL(this.appOAuthInfo
				.getRequestTokenURL());
		String data = generatingSignatureBaseString(method, fmtURL,
				norParamString);
		String value = generateSignature(data, this.appOAuthInfo
				.getConsumerSecret());
		list.add(new Parameter(oauth_signature, value));
		return list;
	}

	private String normalizeRequestParameters(List<Parameter> list) {
		Collections.sort(list);
		Parameter parameter = null;
		StringBuilder sb = new StringBuilder();
		int lastidx = list.size() - 1;
		for (int i = 0; i < list.size(); i++) {
			parameter = list.get(i);
			sb.append(OAuthDataUtil.encoder(parameter.getName())).append("=")
					.append(OAuthDataUtil.encoder(parameter.getValue()));
			if (i < lastidx) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

	// private static String buildRequestHeader(List<Parameter> list) {
	// StringBuilder sb = new StringBuilder("OAuth ");
	// for (Parameter o : list) {
	// sb.append(o.getRequestHeaderString()).append(",");
	// }
	// sb.deleteCharAt(sb.length() - 1);
	// return sb.toString();
	// }
	private String constructRequestURL(final String url) {
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

	private String generatingSignatureBaseString(String method, String fmtURL,
			String norParamString) {
		StringBuilder sb = new StringBuilder(method);
		sb.append("&").append(OAuthDataUtil.encoder(fmtURL)).append("&")
				.append(OAuthDataUtil.encoder(norParamString));
		return sb.toString();
	}

	private String generateSignature(String data, String consumerSecret) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(new SecretKeySpec(
					(OAuthDataUtil.encoder(consumerSecret) + "&").getBytes(),
					HMAC_SHA1));
			return new BASE64Encoder().encode(mac.doFinal(data.getBytes()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<Parameter> buildDefaultParameterForGetRequestToken(
			AppOAuthInfo info, String callback_url, String timestamp,
			String nonce) {
		List<Parameter> list = new ArrayList<Parameter>();
		list.add(new Parameter(oauth_consumer_key, info.getConsumerKey()));
		list.add(new Parameter(oauth_signature_method, info
				.getSignatureMethod()));
		list.add(new Parameter(oauth_timestamp, timestamp));
		list.add(new Parameter(oauth_nonce, nonce));
		list.add(new Parameter(oauth_version, info.getVersion()));
		list.add(new Parameter(oauth_callback, callback_url));
		return list;
	}

	protected void p(String s) {
		System.out.println(s);
	}
}