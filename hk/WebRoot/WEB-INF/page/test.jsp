<%@ page language="java" pageEncoding="UTF-8"%>
<h1><%=request.getHeader("x-forwarded-for") %></h1>
<h1><%=request.getHeader("WL-Proxy-Client-IP") %></h1>
<h1><%=request.getRemoteAddr() %></h1>
<%
String ip = request.getHeader("x-forwarded-for");
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	ip = request.getHeader("Proxy-Client-IP");
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	ip = request.getHeader("WL-Proxy-Client-IP");
if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	ip = request.getRemoteAddr();
%>
<h1><%=ip %></h1>