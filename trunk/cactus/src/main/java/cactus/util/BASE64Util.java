package cactus.util;

import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64Util {
	private BASE64Util() {//
	}

	public static String getStringFromBASE64(String s) {
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

	public static String getBASE64(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			return encoder.encode(str.getBytes("iso-8859-1"));
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(BASE64Util.getBASE64("123"));
			System.out.println(BASE64Util.getStringFromBASE64("MTIzzzz"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
