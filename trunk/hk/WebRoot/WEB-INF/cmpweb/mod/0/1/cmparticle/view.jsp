<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<%EppViewUtil.loadCmpPageBlock(request); %>
<c:set scope="request" var="title_value">${cmpArticle.title }</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpArticle.title }|${cmpNav.name }|${o.name}|${tagdata}"/>
<meta name="description" content="${cmpArticle.title }|${cmpNav.name }|${o.name}"/>
<script type="text/javascript" language="javascript" src="<%=path%>/cmpwebst4/mod/pub/js/hovertip.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  window.setTimeout(hovertipInit, 1);
   });
</script>
</c:set>
<c:set scope="request" var="html_body_content">
<div class="pl">
	<div>
		<c:if test="${!cmpArticle.hideTitle}">
			<h1 style="margin-bottom: 20px;">${cmpArticle.title }</h1>
		</c:if>
	</div>
	<c:if test="${topCmpFile!=null}">
		<div class="divrow" style="text-align: center;">
			<c:if test="${topCmpFile.imageShow}">
				<img src="${topCmpFile.cmpFilePic600 }"/>
			</c:if>
			<c:if test="${topCmpFile.flashShow}">
				<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${topCmpFile.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
			</c:if><br/>
		</div>
	</c:if>
	<div>${cmpArticleContent.content }</div>
	<c:forEach var="cf" items="${list}">
		<div class="divrow" style="text-align: center;">
			<c:if test="${cf.imageShow}">
				<img src="${cf.cmpFilePic600 }"/>
			</c:if>
			<c:if test="${cf.flashShow}">
				<embed type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" src="${cf.cmpFileFlash }" play="true" loop="true" menu="true"></embed>
			</c:if><br>
		</div>
	</c:forEach>
</div>
<div class="pr">
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
		<c:forEach var="article" items="${before_cmparticle_list}">
			<li><a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a></li>
		</c:forEach>
		<c:forEach var="article" items="${after_cmparticle_list}">
			<li><a href="/article/${companyId }/${article.cmpNavOid }/${article.oid }.html">${article.title }</a></li>
		</c:forEach>
	</ul>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>