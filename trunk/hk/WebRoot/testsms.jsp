<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.httpclient.HttpMethod"%>
<%@page import="org.apache.commons.httpclient.methods.GetMethod"%>
<%@page import="org.apache.commons.httpclient.HttpClient"%>
<%
//response.sendRedirect("http://61.136.60.100:8030/Submit?CommandId=1eb&GateName=mobile&MsgId=2009120717081300378&ExtData:=30&Name=pxwy&Pwd=pxwy&ItemId=10233001&SpNumber=916025&UserNumber=13552156827&Msg:=B6CCCFA2B7A2CBCDB2E2CAD4&UserNumberType=0&MsgCode=15&ReportFlag=1");

String url="http://61.136.60.100:8030/Submit?CommandId=1eb&GateName=mobile&MsgId=2009120717081300378&ExtData:=30&Name=pxwy&Pwd=pxwy&ItemId=10233001&SpNumber=916025&UserNumber=13552156827&Msg:=B6CCCFA2B7A2CBCDB2E2CAD4&UserNumberType=0&MsgCode=15&ReportFlag=1&freetype=1";
HttpMethod method = new GetMethod(url);
HttpClient client = new HttpClient();
try {
	client.executeMethod(method);
}
catch (Exception e) {
	e.printStackTrace();
}
finally {
	method.releaseConnection();
}
%>
<h1>ok</h1>