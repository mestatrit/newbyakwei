package tuxiazi.webapi.test;

import halo.util.P;
import halo.util.http.ConnectException;
import halo.util.http.HttpData;
import halo.util.http.HttpFile;
import halo.util.http.HttpHelperException;
import halo.util.http.HttpHelperImpl;
import halo.util.http.HttpResponse;

import java.io.File;

import org.junit.Test;

public class PhotoApiTest {

	@Test
	public void uploadPhoto() throws HttpHelperException, ConnectException {
		HttpHelperImpl helper = new HttpHelperImpl();
		helper.setTimeout(120);
		HttpData httpData = new HttpData();
		httpData.addFile(new HttpFile("f", new File("d:/test/test0.jpg")));
		httpData.add("userid", "3");
		httpData.add("api_type", "1");
		httpData.add("access_token", "fa70c08f889270a4007d3bc3955deda7");
		httpData.add("token_secret", "5035cbe48f4220ee757d76571b992b50");
		HttpResponse httpResponse = helper.doPostResult(
				"http://www.tuxiazi.com/tu/api/photo_prvupload", httpData);
		P.println(httpResponse.getResponseText());
	}
}
