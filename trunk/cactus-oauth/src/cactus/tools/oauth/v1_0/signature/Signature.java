package cactus.tools.oauth.v1_0.signature;

public interface Signature {

	String generateSignature(String data, String consumerSecret,
			String tokenSecret);
}