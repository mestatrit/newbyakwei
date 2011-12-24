<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.laba.job.IndexSearchLabaJob"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%
IndexSearchLabaJob job=(IndexSearchLabaJob)HkUtil.getBean("indexSearchLabaJob");
job.invoke();
%>
<h1>index laba ok!</h1>