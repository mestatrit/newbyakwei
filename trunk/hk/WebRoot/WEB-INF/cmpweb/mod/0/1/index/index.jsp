<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<%EppViewUtil.loadCmpNavForHome(request,"home_cmpNav"); %>
<c:set scope="request" var="html_body_content">
<div class="m_1">
	<jsp:include page="index_mod17.jsp"></jsp:include>
</div>
<div class="m_2">
	<jsp:include page="index_mod18.jsp"></jsp:include>
</div>
<div class="m_3">
	<jsp:include page="index_mod19.jsp"></jsp:include>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>