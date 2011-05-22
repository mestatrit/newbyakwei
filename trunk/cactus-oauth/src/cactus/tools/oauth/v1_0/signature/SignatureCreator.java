package cactus.tools.oauth.v1_0.signature;

import java.util.HashMap;
import java.util.Map;

public class SignatureCreator {

	private final static String HMACSHA1_KEY = "HMAC-SHA1";

	private final static Map<String, Signature> map = new HashMap<String, Signature>(
			1);
	static {
		map.put("HMAC-SHA1", new HmacSHA1Signature());
	}

	public static HmacSHA1Signature getHmacSHA1Signature() {
		return (HmacSHA1Signature) map.get(HMACSHA1_KEY);
	}
}