<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.page.PageSupport"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="com.hk.frame.util.page.SimplePage"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
PageSupport pageSupport=(PageSupport)request.getAttribute(HkUtil.PAGESUPPORT_ATTRIBUTE);
request.setAttribute("pageSupport",pageSupport);
pageSupport.outPageNo(5);%>
<c:if test="${pageSupport!=null}">
<div class="page4">
	<ul class="pagesupport">
	<c:if test="${url_rewrite}">
			<c:if test="${pageSupport.page>1}">
				<li><a href="${page_url }/${pageSupport.page-1}">&lt;<hk:data key="epp.page.pre"/></a></li>
			</c:if>
			<c:if test="${pageSupport.pageCount>10 && pageSupport.beginPage>1}">
				<li><a href="${page_url }/1">&lt;</a></li>
				<li><a class="etc"> ... </a></li>
			</c:if>
			<c:forEach var="i" begin="${pageSupport.beginPage}" end="${pageSupport.endPage}" step="1">
				<li <c:if test="${pageSupport.page==i}">class="now"</c:if>><a href="${page_url }/${i}">${i }</a></li>
			</c:forEach>
			<c:if test="${pageSupport.pageCount>10 && pageSupport.endPage < pageSupport.pageCount}">
				<li><a class="etc"> ... </a></li>
				<li><a href="${page_url }/${pageSupport.pageCount}">&lt;</a></li>
			</c:if>
			<c:if test="${pageSupport.page<pageSupport.pageCount}">
				<li><a href="${page_url }/${pageSupport.page+1}">&gt;</a></li>
			</c:if>
	</c:if>
	<c:if test="${!url_rewrite}">
		<c:if test="${pageSupport.page>1}">
			<li><a href="${page_url }&page=${pageSupport.page-1}">&lt;<hk:data key="epp.page.pre"/></a></li>
		</c:if>
		<c:if test="${pageSupport.pageCount>10 && pageSupport.beginPage>1}">
			<li><a href="${page_url }&page=1">&lt;</a></li>
			<li><a class="etc"> ... </a></li>
		</c:if>
		<c:forEach var="i" begin="${pageSupport.beginPage}" end="${pageSupport.endPage}" step="1">
			<li <c:if test="${pageSupport.page==i}">class="now"</c:if>><a href="${page_url }&page=${i}">${i }</a></li>
		</c:forEach>
		<c:if test="${pageSupport.pageCount>10 && pageSupport.endPage < pageSupport.pageCount}">
			<li><a class="etc"> ... </a></li>
			<li><a href="${page_url }&page=${pageSupport.pageCount}">&lt;</a></li>
		</c:if>
		<c:if test="${pageSupport.page<pageSupport.pageCount}">
			<li><a href="${page_url }&page=${pageSupport.page+1}"><hk:data key="epp.page.next"/>&gt;</a></li>
		</c:if>
	</c:if>
	</ul>
	<div class="clr"></div>
</div>
</c:if>