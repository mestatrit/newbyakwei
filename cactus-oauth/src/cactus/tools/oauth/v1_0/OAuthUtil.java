package cactus.tools.oauth.v1_0;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class OAuthUtil {

	private final static String DEFAULTCHARSET = "utf-8";

	public static final String HMAC_SHA1 = "HmacSHA1";

	public static final String oauth_token_key = "oauth_token";

	public static final String oauth_token_secret_key = "oauth_token_secret";

	private OAuthUtil() {
	}

	public static String decoder(String value) {
		if (value != null) {
			return decoder(value, DEFAULTCHARSET);
		}
		return "";
	}

	public static String decoder(String value, String charset) {
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

	public static String encoder(String value) {
		if (value != null) {
			return encoder(value, DEFAULTCHARSET);
		}
		return "";
	}

	public static String encoder(String value, String charset) {
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

	public static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String value) {
		if (value != null && value.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static String createUrlForHttpGetMethod(String url,
			List<Parameter> list) {
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") != -1) {
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
}
