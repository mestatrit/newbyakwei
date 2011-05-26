package cactus.util.httputil;

public interface HttpHelper {

	byte[] doGet(String url, HttpData httpData) throws HttpHelperException;

	byte[] doPost(String url, HttpData httpData) throws HttpHelperException;

	String doGetResultString(String url, HttpData httpData)
			throws HttpHelperException;

	String doPostResultString(String url, HttpData httpData)
			throws HttpHelperException;
}