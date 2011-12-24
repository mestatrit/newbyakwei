<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:if test="${fn:length(children)>0}">
<div class="row even">
	<c:forEach var="child_nav" items="${children}">
		<c:if test="${child_nav.oid!=navId}">
			<c:if test="${child_nav.urlLink}">
				<c:if test="${not empty child_nav.url}">
					<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${child_nav.oid}">${child_nav.name }</a>
				</c:if>
				<c:if test="${empty child_nav.url}">${child_nav.name }</c:if>
			</c:if>
			<c:if test="${!child_nav.urlLink}">
				<c:if test="${child_nav.loginAndRefFunc}">
					<c:if test="${loginUser!=null}"><a href="<%=path %>/epp/logout.do?companyId=${companyId}">退出</a></c:if>
					<c:if test="${loginUser==null}"><a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${child_nav.oid}">${child_nav.name }</a></c:if>
				</c:if>
				<c:if test="${!child_nav.loginAndRefFunc}"><a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${child_nav.oid}">${child_nav.name }</a></c:if>
			</c:if>
			<br/>
		</c:if>
	</c:forEach>
</div>
</c:if>