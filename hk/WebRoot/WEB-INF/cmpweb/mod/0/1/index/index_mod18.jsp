<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==18}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,true,10,false); %>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
			<div class="pagemod">
				<h3>${block.name }</h3>
				<c:forEach var="article" items="${block_cmparticlelist}">
					<div class="articlelist" style="overflow: auto; height: auto;">
						<c:if test="${not empty article.filepath}">
							<div class="img"><a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html"><img src="${article.cmpFilePic60 }" width="80" height="80" /></a></div>
						</c:if>
						<div class="txt">
							<h5><a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a></h5>
							${article.cmpArticleContent.firstParagraph }
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</c:if>
</c:forEach>