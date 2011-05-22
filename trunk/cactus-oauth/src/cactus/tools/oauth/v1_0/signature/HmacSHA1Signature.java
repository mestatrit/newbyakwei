package cactus.tools.oauth.v1_0.signature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;
import cactus.tools.oauth.v1_0.OAuthUtil;

public class HmacSHA1Signature implements Signature {

	private final String HMAC_SHA1 = "HmacSHA1";

	@Override
	public String generateSignature(String data, String consumerSecret,
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
}