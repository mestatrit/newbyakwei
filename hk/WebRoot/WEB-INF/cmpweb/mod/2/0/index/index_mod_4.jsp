<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==4}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpAdBlockList(request,4); %>
		<c:if test="${fn:length(block_cmpadblocklist)>0}">
			<div class="mod">
				<div class="hkd1">
					<c:forEach var="adblock" items="${block_cmpadblocklist}">
					<a target="_blank" href="http://${adblock.cmpAd.url }"><img src="${adblock.cmpAd.picUrl }"/></a>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</c:if>
</c:forEach>