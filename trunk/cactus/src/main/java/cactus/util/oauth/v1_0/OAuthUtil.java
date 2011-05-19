package cactus.util.oauth.v1_0;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

	private static final String HMAC_SHA1 = "HmacSHA1";

	private final String oauth_consumer_key = "oauth_consumer_key";

	private final String oauth_signature_method = "oauth_signature_method";

	private final String oauth_signature = "oauth_signature";

	private final String oauth_timestamp = "oauth_timestamp";

	private final String oauth_nonce = "oauth_nonce";

	private final String oauth_version = "oauth_version";

	private final static String DEFAULTCHARSET = "utf-8";

	private static Random RAND = new Random();

	public List<Parameter> buildParameterForGetRequestToken(String method,
			OAuthAppInfo info, Parameter[] otherParameters) {
		List<Parameter> list = this
				.buildDefaultParameterForGetRequestToken(info);
		list.addAll(Arrays.asList(otherParameters));
		String norParamString = this.normalizeRequestParameters(list);
		String fmtURL = this.constructRequestURL(info.getRequestTokenURL());
		String data = this.generatingSignatureBaseString(method, fmtURL,
				norParamString);
		String value = this.generateSignature(data, info.getConsumerSecret());
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
			sb.append(urlEncoder(parameter.getName())).append("=").append(
					urlEncoder(parameter.getValue()));
			if (i < lastidx) {
				sb.append("&");
			}
		}
		return sb.toString();
	}

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
		sb.append("&").append(urlEncoder(fmtURL)).append("&").append(
				urlEncoder(norParamString));
		return sb.toString();
	}

	private String generateSignature(String data, String consumerSecret) {
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

	private List<Parameter> buildDefaultParameterForGetRequestToken(
			OAuthAppInfo info) {
		List<Parameter> list = new ArrayList<Parameter>();
		list.add(new Parameter(oauth_consumer_key, info.getConsumerKey()));
		list.add(new Parameter(oauth_signature_method, info
				.getSignatureMethod()));
		list.add(new Parameter(oauth_timestamp, createOauthTimestamp()));
		list.add(new Parameter(oauth_nonce, createOauthNonce()));
		list.add(new Parameter(oauth_version, info.getVersion()));
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
			urlEncoder(value, DEFAULTCHARSET);
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