<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<c:set scope="request" var="title_value">${cmpNav.name } - <c:if test="${not empty cmpNav.title}">${cmpNav.title }</c:if></c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/mod/pub/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set>
<c:set scope="request" var="html_body_content">
<div class="pl">
	<c:forEach var="pink" items="${notpiclist}">
		<div class="pagemod mod2">
			<h3><a href="/article/${companyId }/${pink.navId }/${pink.articleId }.html">${pink.cmpArticle.title }</a></h3>
			<div class="txt">
				<c:if test="${pink.pflg==0}">${pink.cmpArticle.cmpArticleContent.content }</c:if>
				<c:if test="${pink.pflg>0}">${pink.content } <a href="/article/${companyId }/${pink.navId }/${pink.articleId }.html">&gt;&gt;</a></c:if>
				
			</div>
		</div>
	</c:forEach>
	<br/>
	<c:forEach var="pink" items="${haspiclist}">
		<div class="pagemod mgbtm mod2">
			<h3><a href="/article/${companyId }/${pink.navId }/${pink.articleId }.html">${pink.cmpArticle.title }</a></h3>
			<div class="articlelist nobd">
				<c:if test="${not empty pink.cmpArticle.filepath}">
					<div class="img"><a href="/article/${companyId }/${pink.navId }/${pink.articleId }.html"><img src="${pink.cmpArticle.cmpFilePic60 }" width="80" height="80" /></a></div>
				</c:if>
				<div class="txt">
					${pink.cmpArticle.cmpArticleContent.simpleContent140 }
					<a href="/article/${companyId }/${pink.navId }/${pink.articleId }.html">&gt;&gt;</a>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
<div class="pr">
	<div class="mod">
		
	</div>
	<div class="mod">
		<div class="fr">
		<script type="text/javascript">
		obj_width=265;
		</script>
		<jsp:include page="../inc/share.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
	<ul class="articlelist2">
		<c:forEach var="article" items="${otherlist}">
			<li><a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a></li>
		</c:forEach>
		<c:if test="${more_article}">
		<li><a href="/articlelist/${companyId }/${navId }"><hk:data key="epp.more"/>&gt;&gt;</a></li>
		</c:if>
	</ul>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>