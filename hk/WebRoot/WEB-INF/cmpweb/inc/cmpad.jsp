<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
EppViewUtil.loadCmpAd(request);%>
<c:if test="${fn:length(cmpadlist)>0}">
	<c:forEach var="cmpad" items="${cmpadlist}">
		<div class="mod">
			<div>
			<a href="http://${cmpad.url }" target="_blank"><img src="${cmpad.picUrl }"/></a>
			</div>
			<c:if test="${not empty cmpad.name}">
				<div style="margin-top: 2px">
					<a href="http://${cmpad.url }" target="_blank">${cmpad.name }</a>
				</div>
			</c:if>
		</div>
	</c:forEach>
</c:if>