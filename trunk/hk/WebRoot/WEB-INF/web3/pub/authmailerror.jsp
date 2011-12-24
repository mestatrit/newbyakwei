<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">验证信息</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="text_16" style="text-align: center"><a href="http://<%=HkWebConfig.getWebDomain() %>">返回首页</a>
	</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>