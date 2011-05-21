package cactus.tools.oauth.v1_0;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;

public class OAuthDataUtil {

	private final static String DEFAULTCHARSET = "utf-8";

	private static Random RAND = new Random();

	public static final String oauth_token_key = "oauth_token";

	public static final String oauth_token_secret_key = "oauth_token_secret";

	public static final String oauth_token_flag = "oauth_token=";

	public static final String oauth_token_secret_flag = "oauth_token_secret=";

	private OAuthDataUtil() {
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

	public static String createOauthTimestamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	public static String createOauthNonce() {
		return String.valueOf(System.currentTimeMillis() + RAND.nextInt());
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
}
