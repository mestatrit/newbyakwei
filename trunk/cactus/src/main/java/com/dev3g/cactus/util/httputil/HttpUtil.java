package com.dev3g.cactus.util.httputil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {

	private MultiThreadedHttpConnectionManager httpManager;

	private boolean proxy;

	private String proxyUrl;

	private int proxyPort;

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUrl(String proxyUrl) {
		this.proxyUrl = proxyUrl;
	}

	public HttpUtil(int timeout) {
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
				new InnerHttpMethodRetryHandler());
	}

	public String doGet(String url) throws Exception {
		return doGet(url, null, null);
	}

	public String doGet(String url, String headerName, String headerValue)
			throws Exception {
		HttpMethod method = new GetMethod(url);
		if (headerName != null && headerValue != null) {
			method.setRequestHeader(headerName, headerValue);
		}
		return getHttpResult(method);
	}

	public byte[] getByteArrayResult(String url) throws Exception {
		HttpMethod method = new GetMethod(url);
		return getByteArrayHttpResult(method);
	}

	public void doGetWithoutResponse(String url) throws Exception {
		HttpMethod method = new GetMethod(url);
		this.doMethod(method);
	}

	public String doPost(String url, Map<String, String> map,
			BatchBean batchBean) throws Exception {
		PostMethod method = new InnerPostMethod(url);
		if (map == null || map.size() == 0) {
			throw new IllegalArgumentException("map must be not null");
		}
		NameValuePair[] data = new NameValuePair[map.size()
				+ batchBean.getValues().size()];
		Set<Map.Entry<String, String>> set = map.entrySet();
		int i = 0;
		for (Map.Entry<String, String> e : set) {
			data[i++] = new NameValuePair(e.getKey(), e.getValue());
		}
		for (String v : batchBean.getValues()) {
			data[i++] = new NameValuePair(batchBean.getKey(), v);
		}
		method.setRequestBody(data);
		return getHttpResult(method);
	}

	public String doPost(String url, Map<String, String> map) throws Exception {
		return doPost(url, null, null, map);
	}

	public String doPost(String url, String headerName, String headerValue,
			Map<String, String> map) throws Exception {
		PostMethod method = new InnerPostMethod(url);
		if (map == null || map.size() == 0) {
			throw new IllegalArgumentException("map must be not null");
		}
		NameValuePair[] data = new NameValuePair[map.size()];
		Set<Map.Entry<String, String>> set = map.entrySet();
		int i = 0;
		for (Map.Entry<String, String> e : set) {
			data[i++] = new NameValuePair(e.getKey(), e.getValue());
		}
		method.setRequestBody(data);
		if (headerName != null && headerValue != null) {
			method.setRequestHeader(headerName, headerValue);
		}
		return getHttpResult(method);
	}

	public String doMultiPost(String url, HttpFile[] httpFiles,
			Map<String, String> map) throws Exception {
		Part[] parts = new Part[httpFiles.length + map.size()];
		int k = 0;
		for (int i = 0; i < httpFiles.length; i++) {
			parts[k++] = new FilePart(httpFiles[i].getName(),
					httpFiles[i].getFile());
		}
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> e : set) {
			parts[k++] = new StringPart(e.getKey(), e.getValue(), "utf-8");
		}
		PostMethod method = new InnerPostMethod(url);
		method.getParams().setBooleanParameter(
				HttpMethodParams.USE_EXPECT_CONTINUE, true);
		MultipartRequestEntity mre = new MultipartRequestEntity(parts,
				method.getParams());
		method.setRequestEntity(mre);
		return getHttpResult(method);
	}

	private String getHttpResult(HttpMethod method) throws Exception {
		this.initMethod(method);
		InputStream is = null;
		BufferedReader reader = null;
		try {
			HttpClient client = createHttpClient();
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
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
			}
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
			}
		}
	}

	private byte[] getByteArrayHttpResult(HttpMethod method) throws Exception {
		this.initMethod(method);
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			HttpClient client = createHttpClient();
			client.executeMethod(method);
			is = method.getResponseBodyAsStream();
			bis = new BufferedInputStream(is);
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
			}
			try {
				if (bis != null) {
					bis.close();
				}
			}
			catch (IOException e) {
			}
			try {
				if (bos != null) {
					bos.close();
				}
			}
			catch (IOException e) {
			}
		}
	}

	private void doMethod(HttpMethod method) throws Exception {
		this.initMethod(method);
		try {
			HttpClient client = createHttpClient();
			client.executeMethod(method);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			method.releaseConnection();
		}
	}

	static class InnerPostMethod extends PostMethod {

		public InnerPostMethod(String url) {
			super(url);
		}

		@Override
		protected String getContentCharSet(Header contentheader) {
			return "UTF-8";
		}
	}

	static class InnerHttpMethodRetryHandler extends
			DefaultHttpMethodRetryHandler {

		public InnerHttpMethodRetryHandler() {
			super(1, false);
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