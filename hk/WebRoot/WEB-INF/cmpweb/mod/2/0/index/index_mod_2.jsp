<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==2}">
	<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,false,4); %>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
			<div class="mod hcenter">
				<div id="jiaodian_${block.blockId }" class="jiaodian">
					<ul class="pic">
						<c:forEach var="article" items="${block_cmparticlelist}">
							<li><a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html"><img src="${article.cmpFilePic320 }" /></a><span class="title"><a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">${article.title }</a></span></li>
						</c:forEach>
					</ul>
					<ul class="num">
						<c:forEach var="article" items="${block_cmparticlelist}" varStatus="idx">
							<li><a href="#">${idx.index+1 }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<script type="text/javascript">
				$(document).ready(function(){
				    init('jiaodian_${block.blockId }');
				    autoRun('jiaodian_${block.blockId }', 3000);
				});
			</script>
		</c:if>
	</c:if>
</c:forEach>