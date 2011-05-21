package cactus.tools.httpclient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientImpl implements HttpHelper {

	private InnerHttpMethodRetryHandler innerHttpMethodRetryHandler = new InnerHttpMethodRetryHandler();

	private MultiThreadedHttpConnectionManager httpManager;

	private boolean proxy;

	private String proxyUrl;

	private int proxyPort;

	@Override
	public byte[] doGet(String url, HttpData httpData)
			throws HttpHelperException {
		HttpMethod method = this
				.createMethod(HttpMethodEnum.GET, url, httpData);
		try {
			return this.getByteArrayHttpResult(method);
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
	}

	@Override
	public byte[] doPost(String url, HttpData httpData)
			throws HttpHelperException {
		HttpMethod method = this.createMethod(HttpMethodEnum.POST, url,
				httpData);
		try {
			return this.getByteArrayHttpResult(method);
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
	}

	@Override
	public String doGetResultString(String url, HttpData httpData)
			throws HttpHelperException {
		HttpMethod method = this
				.createMethod(HttpMethodEnum.GET, url, httpData);
		try {
			return this.getHttpResult(method);
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
	}

	@Override
	public String doPostResultString(String url, HttpData httpData)
			throws HttpHelperException {
		HttpMethod method = this.createMethod(HttpMethodEnum.POST, url,
				httpData);
		try {
			return this.getHttpResult(method);
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}

	public HttpClientImpl(int timeout) {
		httpManager = new MultiThreadedHttpConnectionManager();
		httpManager.getParams().setConnectionTimeout(timeout);
	}

	private HttpConnectionManager getHttpManager() {
		return httpManager;
	}

	private HttpClient createHttpClient() {
		HttpClient client = new HttpClient(getHttpManager());
		if (proxy) {
			HostConfiguration hcf = new HostConfiguration();
			hcf.setProxy(this.proxyUrl, this.proxyPort);
			client.setHostConfiguration(hcf);
		}
		return client;
	}

	private void initMethod(HttpMethod method) {
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				this.innerHttpMethodRetryHandler);
	}

	private String getHttpResult(HttpMethod method) throws Exception {
		this.initMethod(method);
		InputStream is = null;
		try {
			HttpClient client = createHttpClient();
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"));
			StringBuilder builder = new StringBuilder();
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				builder.append(tmp);
			}
			return builder.toString();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			method.releaseConnection();
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	private byte[] getByteArrayHttpResult(HttpMethod method) throws Exception {
		this.initMethod(method);
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		HttpClient client = createHttpClient();
		client.executeMethod(method);
		try {
			is = method.getResponseBodyAsStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bos = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = -1;
			while ((len = bis.read(by)) != -1) {
				bos.write(by, 0, len);
			}
			bos.flush();
			return bos.toByteArray();
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			method.releaseConnection();
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
			try {
				if (bos != null) {
					bos.close();
				}
			}
			catch (IOException e) {
				throw e;
			}
		}
	}

	private HttpMethod createMethod(HttpMethodEnum httpMethodEnum, String url,
			HttpData httpData) {
		HttpMethod method = null;
		if (httpMethodEnum == HttpMethodEnum.GET) {
			method = this.createGetMethod(url);
		}
		else if (httpMethodEnum == HttpMethodEnum.POST) {
			method = this.createPostMethod(url, httpData);
		}
		else {
			method = this.createMultiPostMethod(url, httpData);
		}
		if (httpData != null) {
			if (httpData.getHttpHeader() != null) {
				method.setRequestHeader(httpData.getHttpHeader().getName(),
						httpData.getHttpHeader().getValue());
			}
		}
		return method;
	}

	private HttpMethod createGetMethod(String url) {
		return new GetMethod(url);
	}

	private HttpMethod createPostMethod(String url, HttpData httpData) {
		PostMethod method = new InnerPostMethod(url);
		if (httpData != null) {
			List<NameValuePair> datalist = new ArrayList<NameValuePair>(
					httpData.getPatameterCount());
			Set<Map.Entry<String, String>> set = httpData.getParameterMap()
					.entrySet();
			for (Map.Entry<String, String> e : set) {
				datalist.add(new NameValuePair(e.getKey(), e.getValue()));
			}
			Set<Entry<String, List<String>>> bset = httpData
					.getBatchParameterMap().entrySet();
			for (Entry<String, List<String>> e : bset) {
				for (String s : e.getValue()) {
					datalist.add(new NameValuePair(e.getKey(), s));
				}
			}
			method.setRequestBody(datalist.toArray(new NameValuePair[datalist
					.size()]));
		}
		return method;
	}

	private HttpMethod createMultiPostMethod(String url, HttpData httpData) {
		PostMethod method = new InnerPostMethod(url);
		if (httpData != null) {
			List<Part> list = new ArrayList<Part>(httpData.getPatameterCount());
			Set<Map.Entry<String, String>> set = httpData.getParameterMap()
					.entrySet();
			for (Map.Entry<String, String> e : set) {
				list.add(new StringPart(e.getKey(), e.getValue(), "utf-8"));
			}
			Set<Entry<String, List<String>>> bset = httpData
					.getBatchParameterMap().entrySet();
			for (Entry<String, List<String>> e : bset) {
				for (String s : e.getValue()) {
					list.add(new StringPart(e.getKey(), s, "utf-8"));
				}
			}
			method.getParams().setBooleanParameter(
					HttpMethodParams.USE_EXPECT_CONTINUE, true);
			MultipartRequestEntity mre = new MultipartRequestEntity(list
					.toArray(new Part[list.size()]), method.getParams());
			method.setRequestEntity(mre);
		}
		return method;
	}

	class InnerPostMethod extends PostMethod {

		public InnerPostMethod(String url) {
			super(url);
		}

		@Override
		protected String getContentCharSet(Header contentheader) {
			return "UTF-8";
		}
	}

	enum HttpMethodEnum {
		GET(0), POST(1), MULTIPOST(2);

		int value;

		private HttpMethodEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	class InnerHttpMethodRetryHandler extends DefaultHttpMethodRetryHandler {

		public InnerHttpMethodRetryHandler() {
			super(1, true);
		}
	}

	public static void main(String[] args) throws Exception {
		HttpUtil httpUtil = new HttpUtil(3000);
		byte[] by = httpUtil
				.getByteArrayResult("http://img05.taobaocdn.com/bao/uploaded/i5/T1LE8GXdBLXXaOHGk._110940.jpg");
		FileOutputStream fos = new FileOutputStream(new File(
				"d:/00aaaaaaaaahttp.jpg"));
		fos.write(by);
		fos.flush();
		fos.close();
	}
}