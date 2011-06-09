package com.dev3g.cactus.util.httputil;

public interface HttpHelper {

	byte[] doGet(String url, HttpData httpData) throws HttpHelperException,
			ConnectException;

	byte[] doPost(String url, HttpData httpData) throws HttpHelperException,
			ConnectException;

	String doGetResultString(String url, HttpData httpData)
			throws HttpHelperException, ConnectException;

	String doPostResultString(String url, HttpData httpData)
			throws HttpHelperException, ConnectException;
}