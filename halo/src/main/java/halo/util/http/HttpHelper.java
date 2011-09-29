package halo.util.http;

public interface HttpHelper {

	HttpResponse doGetResult(String url, HttpData httpData, int retry)
			throws HttpHelperException, ConnectException;

	HttpResponse doPostResult(String url, HttpData httpData, int retry)
			throws HttpHelperException, ConnectException;
}