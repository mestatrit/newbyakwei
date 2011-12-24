<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==7}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,false,6); %>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
		<div class="mod bd0">
			<div class="mod_tit">
				${block.name }
			</div>
			<div class="mod_content inner">
				<ul class="list">
					<c:forEach var="article" items="${block_cmparticlelist}">
						<li><a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">${article.title }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		</c:if>
	</c:if>
</c:forEach>
