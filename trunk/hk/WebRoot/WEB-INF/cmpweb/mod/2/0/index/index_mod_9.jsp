<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:forEach var="block" items="${cmppageblocklist}">
	<c:if test="${block.pageModId==9}">
		<c:set var="data_blockId" value="${block.blockId}" scope="request"/>
		<%EppViewUtil.loadCmpArticleBlockList(request,false,9); %>
		<c:if test="${fn:length(block_cmparticlelist)>0}">
			<div class="mod column">
				<div class="tit">
					${block.name }
				</div>
				<div class="content" style="padding-top: 10px;">
					<c:forEach var="article" items="${block_cmparticlelist}" end="0">
						<div class="l">
							<c:if test="${not empty article.filepath}">
								<a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html"><img src="${article.cmpFilePic120 }"/></a>
							</c:if>
						</div>
					</c:forEach>
					<div class="r">
						<c:forEach var="article" items="${block_cmparticlelist}" end="0">
							<div class="main">
								<div class="b">
									${article.title }
								</div>
								<div>
									<c:set var="main_articleId" value="${article.oid}" scope="request"/>
									<%EppViewUtil.loadMainCmpArticle(request); %>
									${main_cmpArticleContent.simpleContent80 }
									[<a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">详情</a>]
								</div>
							</div>
						</c:forEach>
						<c:if test="${fn:length(block_cmparticlelist)>1}">
							<div class="">
								<ul class="list3">
									<c:forEach var="article" items="${block_cmparticlelist}" begin="1" varStatus="idx">
										<li<c:if test="${idx.index%2==0}"> class="odd"</c:if>>
											<a target="_blank" href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">${article.title }</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
					<div class="clr"></div>
				</div>
			</div>
		</c:if>
	</c:if>
</c:forEach>