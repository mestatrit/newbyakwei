package halo.util.http;

public interface HttpHelper {

	HttpResponse doGetResult(String url, HttpData httpData)
			throws HttpHelperException, ConnectException;

	HttpResponse doPostResult(String url, HttpData httpData)
			throws HttpHelperException, ConnectException;
}