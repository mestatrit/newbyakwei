package cactus.tools.oauth.v1_0;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * @author akwei
 */
public class OAuthUtil {

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

	private final static String DEFAULTCHARSET = "utf-8";

	private static Random RAND = new Random();

	public static String createUrlForGetRequestToken(OAuthAppInfo info,
			List<Parameter> list) {
		StringBuilder sb = new StringBuilder(info.getRequestTokenURL());
		if (info.getRequestTokenURL().indexOf("?") != -1) {
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

	public static List<Parameter> buildParameterForGetRequestToken(
			String method, String callback_url, OAuthAppInfo info,
			List<Parameter> otherParameters, String timestamp, String nonce) {
		List<Parameter> list = buildDefaultParameterForGetRequestToken(info,
				callback_url, timestamp, nonce);
		if (otherParameters != null) {
			list.addAll(otherParameters);
		}
		String norParamString = normalizeRequestParameters(list);
		String fmtURL = constructRequestURL(info.getRequestTokenURL());
		String data = generatingSignatureBaseString(method, fmtURL,
				norParamString);
		String value = generateSignature(data, info.getConsumerSecret());
		list.add(new Parameter(oauth_signature, value));
		return list;
	}

	private static String normalizeRequestParameters(List<Parameter> list) {
		Collections.sort(list);
		Parameter parameter = null;
		StringBuilder sb = new StringBuilder();
		int lastidx = list.size() - 1;
		for (int i = 0; i < list.size(); i++) {
			parameter = list.get(i);
			sb.append(urlEncoder(parameter.getName())).append("=").append(
					urlEncoder(parameter.getValue()));
			if (i < lastidx) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

//	private static String buildRequestHeader(List<Parameter> list) {
//		StringBuilder sb = new StringBuilder("OAuth ");
//		for (Parameter o : list) {
//			sb.append(o.getRequestHeaderString()).append(",");
//		}
//		sb.deleteCharAt(sb.length() - 1);
//		return sb.toString();
//	}

	private static String constructRequestURL(final String url) {
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
		sb.append("&").append(urlEncoder(fmtURL)).append("&").append(
				urlEncoder(norParamString));
		return sb.toString();
	}

	private static String generateSignature(String data, String consumerSecret) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(new SecretKeySpec((urlEncoder(consumerSecret) + "&")
					.getBytes(), HMAC_SHA1));
			return new BASE64Encoder().encode(mac.doFinal(data.getBytes()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static List<Parameter> buildDefaultParameterForGetRequestToken(
			OAuthAppInfo info, String callback_url, String timestamp,
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

	public static String createOauthTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String createOauthNonce() {
		return String.valueOf(System.currentTimeMillis() + RAND.nextInt());
	}

	public static String urlDecoder(String value) {
		if (value != null) {
			return urlDecoder(value, DEFAULTCHARSET);
		}
		return "";
	}

	public static String urlDecoder(String value, String charset) {
		if (value != null) {
			try {
				return URLDecoder.decode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				System.out.println(e);
			}
		}
		return "";
	}

	public static String urlEncoder(String value) {
		if (value != null) {
			return urlEncoder(value, DEFAULTCHARSET);
		}
		return "";
	}

	public static String urlEncoder(String value, String charset) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}
}