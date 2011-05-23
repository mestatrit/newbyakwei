package cactus.util;

import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64Util {

	private BASE64Util() {//
	}

	public static String decode(String s) {
		if (s == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		}
		catch (Exception e) {
			return null;
		}
	}

	public static String encode(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			return encoder.encode(str.getBytes("utf-8"));
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(BASE64Util.encode("123"));
			System.out.println(BASE64Util.decode("MTIz"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
