package halo.util.http;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
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

public class HttpHelperImpl implements HttpHelper {

	// private InnerHttpMethodRetryHandler innerHttpMethodRetryHandler = new
	// InnerHttpMethodRetryHandler();
	private MultiThreadedHttpConnectionManager httpManager;

	private boolean proxy;

	private String proxyUrl;

	private int proxyPort;

	private int timeout;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
		httpManager.getParams().setConnectionTimeout(this.timeout);
	}

	@Override
	public HttpResponse doPostResult(String url, HttpData httpData, int retry)
			throws HttpHelperException, ConnectException {
		HttpMethodEnum en;
		if (httpData.getHttpFiles().size() > 0) {
			en = HttpMethodEnum.MULTIPOST;
		}
		else {
			en = HttpMethodEnum.POST;
		}
		HttpMethod method = this.createMethod(en, url, httpData);
		try {
			return this.getHttpResult(method, retry);
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
	}

	@Override
	public HttpResponse doGetResult(String url, HttpData httpData, int retry)
			throws HttpHelperException, ConnectException {
		HttpMethod method = this
				.createMethod(HttpMethodEnum.GET, url, httpData);
		try {
			return this.getHttpResult(method, retry);
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

	public HttpHelperImpl() {
		httpManager = new MultiThreadedHttpConnectionManager();
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

	private void initMethod(HttpMethod method, int retry) {
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new InnerHttpMethodRetryHandler(retry));
	}

	private HttpResponse getHttpResult(HttpMethod method, int retry)
			throws ConnectException, HttpHelperException {
		HttpResponse httpResponse = new HttpResponse();
		this.initMethod(method, retry);
		InputStream is = null;
		BufferedReader reader = null;
		HttpClient client = createHttpClient();
		try {
			client.executeMethod(method);
		}
		catch (HttpException e1) {
			throw new ConnectException(e1);
		}
		catch (IOException e1) {
			throw new HttpHelperException(e1);
		}
		try {
			is = method.getResponseBodyAsStream();
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				sb.append(tmp);
			}
			httpResponse.setStatusCode(method.getStatusCode());
			httpResponse.setStatusText(method.getStatusText());
			httpResponse.setResponseText(sb.toString());
			return httpResponse;
		}
		catch (Exception e) {
			throw new HttpHelperException(e);
		}
		finally {
			method.releaseConnection();
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
				throw new HttpHelperException(e);
			}
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				throw new HttpHelperException(e);
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
			try {
				for (HttpFile httpFile : httpData.getHttpFiles()) {
					list.add(new FilePart(httpFile.getName(), httpFile
							.getFile()));
				}
			}
			catch (FileNotFoundException e1) {
				throw new RuntimeException(e1);
			}
			method.getParams().setBooleanParameter(
					HttpMethodParams.USE_EXPECT_CONTINUE, true);
			MultipartRequestEntity mre = new MultipartRequestEntity(
					list.toArray(new Part[list.size()]), method.getParams());
			method.setRequestEntity(mre);
		}
		return method;
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

	static class InnerHttpMethodRetryHandler extends
			DefaultHttpMethodRetryHandler {

		public InnerHttpMethodRetryHandler(int retry) {
			super(retry, true);
		}
	}
}