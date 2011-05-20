package cactus.tools.oauth.v1_0;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

public class OAuthDataUtil {

	private final static String DEFAULTCHARSET = "utf-8";

	private static Random RAND = new Random();

	private OAuthDataUtil() {
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

	public static String createOauthTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String createOauthNonce() {
		return String.valueOf(System.currentTimeMillis() + RAND.nextInt());
	}
}
