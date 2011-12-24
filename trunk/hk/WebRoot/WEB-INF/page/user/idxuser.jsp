<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.web.user.job.IndexSearchUserJob"%>
<%
IndexSearchUserJob job=(IndexSearchUserJob)HkUtil.getBean("indexSearchUserJob");
job.invoke();
%>
<h1>index user ok!</h1>