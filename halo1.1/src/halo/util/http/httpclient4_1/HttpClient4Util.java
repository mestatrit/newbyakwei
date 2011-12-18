package halo.util.http.httpclient4_1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient4Util {

	private HttpClient httpclient;

	public static HttpClient4Util createDefault() {
		HttpClient4Util httpClient4Util = new HttpClient4Util();
		httpClient4Util.setHttpclient(new DefaultHttpClient());
		return httpClient4Util;
	}

	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}

	public HttpClient getHttpclient() {
		return httpclient;
	}

	public HttpResponse doGet(String url) throws ClientProtocolException,
			IOException {
		HttpGet httpGet = new HttpGet(url);
		return httpclient.execute(httpGet);
	}

	public HttpResponse doPost(String url, HttpParameter httpData,
			String encodeing) throws ClientProtocolException, IOException {
		HttpPost httpPost = new HttpPost(url);
		if (httpData.isFileParameterEmpty()) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (BasicParameter e : httpData.getBasicParameters()) {
				nameValuePairs.add(new BasicNameValuePair(e.getName(), e
						.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
					encodeing));
		}
		else {
			MultipartEntity reqEntity = new MultipartEntity();
			for (FileParameter e : httpData.getFileParameters()) {
				reqEntity.addPart(e.getName(), new FileBody(e.getFile()));
			}
			for (BasicParameter e : httpData.getBasicParameters()) {
				reqEntity.addPart(e.getName(), new StringBody(e.getValue(),
						Charset.forName(encodeing)));
			}
			httpPost.setEntity(reqEntity);
		}
		return httpclient.execute(httpPost);
	}

	public void close(HttpResponse httpResponse) throws IOException {
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			EntityUtils.consume(entity);
		}
		this.httpclient.getConnectionManager().shutdown();
	}
}