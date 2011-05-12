package unittest;

import cactus.util.P;
import cactus.util.httputil.HttpUtil;

public class HttpTest {

	public static void main(String[] args) throws Exception {
		String url = "http://maps.google.com/maps/api/geocode/xml?sensor=false&latlng=39.903,116.452&language=zh-CN";
		HttpUtil httpUtil = new HttpUtil();
		String s = httpUtil.executeGetMethod(url);
		P.println(s);
	}
}