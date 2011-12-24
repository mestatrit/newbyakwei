<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.svr.cache.LabaCache"%>
<%
String labaId=request.getParameter("labaId");
LabaCache cache=(LabaCache)HkUtil.getBean("labaCache");
if(labaId!=null && labaId.length()>0){
	cache.removeLaba(Long.parseLong(labaId));
}
%>
<h1>ok</h1>