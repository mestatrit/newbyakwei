<%@page import="halo.util.HaloUtil"%>
<%@page import="iwant.svr.ProjectSvr"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
ProjectSvr projectSvr=(ProjectSvr)HaloUtil.getBean("projectSvr");
projectSvr.tempupdate();
%>
ok