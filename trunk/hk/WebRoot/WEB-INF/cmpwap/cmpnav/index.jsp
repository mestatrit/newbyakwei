<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<c:forEach var="nav" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
	<div class="row ${clazz_var }">
		<c:if test="${nav.urlLink}">
			<c:if test="${not empty nav.url}">
				<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${nav.oid}">${nav.name }</a>
			</c:if>
			<c:if test="${empty nav.url}">${nav.name }</c:if>
		</c:if>
		<c:if test="${!nav.urlLink}">
			<c:if test="${nav.loginAndRefFunc}">
				<c:if test="${loginUser!=null}"><a href="<%=path %>/epp/logout.do?companyId=${companyId}">退出</a></c:if>
				<c:if test="${loginUser==null}"><a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${nav.oid}">${nav.name }</a></c:if>
			</c:if>
			<c:if test="${!nav.loginAndRefFunc}"><a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${nav.oid}">${nav.name }</a></c:if>
		</c:if>
	</div>
</c:forEach>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>