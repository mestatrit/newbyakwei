<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.frame.util.ServletUtil"%>
<%@page import="com.hk.svr.CompanyService"%>
<%@page import="com.hk.frame.util.HkUtil"%>
<%@page import="com.hk.bean.Company"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
String path=request.getContextPath();
long companyId=ServletUtil.getLong(request,"companyId");
request.setAttribute("companyId",companyId);
CompanyService companyService=(CompanyService)HkUtil.getBean("companyService");
Company company=companyService.getCompany(companyId);
if(company==null){
	return;
}
request.setAttribute("company",company);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<script src="http://maps.google.com/maps?file=api&v=2&sensor=false&hl=zh-CN&key=<%=HkWebConfig.getGoogleApiKey() %>" type="text/javascript"></script>
	<style type="text/css">
	body { font-family: Arial, Helvetica, sans-serif;font-size: 14px;color: #5A5858;line-height: 25px;width: auto;margin:0;padding:3px;}
	</style>
	</head>
	<body>
	<div>
	<img src="http://ditu.google.cn/staticmap?center=${company.markerX },${company.markerY }&zoom=14&size=240x240&markers=${company.markerX },${company.markerY }&format=jpg&maptype=mobile&key=<%=HkWebConfig.getGoogleApiKey() %>&sensor=false"/>
	</div>
	</body>
</html>