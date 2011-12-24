<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.company.job.IndexCompanyJob"%><%@page import="com.hk.frame.util.HkUtil"%>
<%
IndexCompanyJob indexCompanyJob=(IndexCompanyJob)HkUtil.getBean("indexCompanyJob");
indexCompanyJob.invoke();
%>
<h1>ok</h1>