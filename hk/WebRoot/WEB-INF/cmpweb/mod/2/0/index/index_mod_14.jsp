<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==14}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpAdBlockList(request,6); %>
		<ul class="adlist">
			<c:forEach var="adblock" items="${block_cmpadblocklist}">
				<li><a target="_blank" href="http://${adblock.cmpAd.url }">${adblock.cmpAd.name }</a></li>
			</c:forEach>
		</ul>
		<div class="clr"></div>
	</c:if>
</c:forEach>