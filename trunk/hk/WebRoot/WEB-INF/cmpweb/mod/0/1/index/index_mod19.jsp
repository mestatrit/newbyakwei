<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==19}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,true,1); %>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
			<c:forEach var="article" items="${block_cmparticlelist}">
				<div class="pagemod">
					<h3>
						<a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a>
					</h3>
					<div class="txt">
						${article.cmpArticleContent.content } 
					</div>
				</div>
			</c:forEach>
		</c:if>
	</c:if>
</c:forEach>