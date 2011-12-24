<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<c:set scope="request" var="title_value">${cmpNav.name } - <c:if test="${not empty cmpNav.title}">${cmpNav.title }</c:if></c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set scope="request" var="html_body_content">
<ul class="articlelist2">
	<c:forEach var="article" items="${list}">
		<li>
		<a class="split-r" href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a>
		<span><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
		</li>
	</c:forEach>
</ul>
<div>
<c:set scope="request" var="page_url">/articlelist/${companyId }/${navId }</c:set>
<c:set scope="request" var="url_rewrite" value="true"/>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>