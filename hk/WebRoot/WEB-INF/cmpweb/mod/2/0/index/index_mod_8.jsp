<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==8}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpAdBlockList(request,2); %>
		<c:if test="${fn:length(block_cmpadblocklist)>0}">
		<div class="mod bd0">
			<div class="mod_content inner">
				<div>
					<c:forEach var="adblock" items="${block_cmpadblocklist}">
						<c:if test="${empty adblock.cmpAd.path}">
						<a target="_blank" href="http://${adblock.cmpAd.url }">${adblock.cmpAd.name }</a>
						</c:if>
						<c:if test="${not empty adblock.cmpAd.path}">
						<a target="_blank" href="http://${adblock.cmpAd.url }"><img src="${adblock.cmpAd.picUrl }"/></a>
						</c:if>
					</c:forEach>
				</div>
			</div>
		</div>
		</c:if>
	</c:if>
</c:forEach>