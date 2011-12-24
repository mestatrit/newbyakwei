<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==16}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpAdBlockList(request,7); %>
		<c:if test="${fn:length(block_cmpadblocklist)>0}">
			<div class="hkd3">
				<ul class="adimglist">
					<c:forEach var="adblock" items="${block_cmpadblocklist}">
						<li><a target="_blank" href="http://${adblock.cmpAd.url }"><img src="${adblock.cmpAd.picUrl }" /></a></li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</div>
		</c:if>
	</c:if>
</c:forEach>