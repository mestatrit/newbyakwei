<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<div class="mod">
	<div class="tit">${cmpOrgNav.name }</div>
	<div class="content">
		<c:if test="${adminorg}">
			<div class="divrow">
				<a class="split-r" href="<%=path %>/epp/web/org/article_create.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}">发布新文章</a>
				<a class="split-r" href="<%=path %>/epp/web/org/article.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&listflg=1">列表浏览</a>
				<c:if test="${cmpOrgNav.articleListWithImgShow}">
					<a class="split-r" href="<%=path %>/epp/web/org/article.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&listflg=0">图片浏览</a>
				</c:if>
				<c:if test="${cmpOrgNav.articleListWithImgShow}">
					<div class="b">注：文章图片设为头图后才可以使用图片方式浏览</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmparticle.nodatalist"/></c:if>
		<c:if test="${fn:length(list)>0}">
			<c:if test="${listflg==0 && cmpOrgNav.articleListWithImgShow}">
				<ul class="ulimglist">
					<c:forEach var="article" items="${list}">
						<li>
							<c:if test="${not empty article.path}">
								<a href="/edu/${companyId }/${orgId }/article/${orgnavId }/${article.oid }.html" title="${article.title }"><img title="${article.title }" src="${article.pic120 }" alt="${article.title }"/></a>
							</c:if>
							<c:if test="${empty article.path}">
								<a href="/edu/${companyId }/${orgId }/article/${orgnavId }/${article.oid }.html" title="${article.title }"><span class="nopic">暂无图片</span></a>
							</c:if>
						</li>
					</c:forEach>
				</ul>
				<div class="clr"></div>
			</c:if>
			<c:if test="${listflg==1 || !cmpOrgNav.articleListWithImgShow}">
				<ul class="datalist">
					<c:forEach var="article" items="${list}">
						<li>
							<span class="title"><a href="/edu/${companyId }/${orgId }/article/${orgnavId }/${article.oid }.html">${article.title }</a></span>
							<span class="time"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
							<div class="clr"></div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
		</c:if>
		<div class="fr">
			<c:if test="${listflg==1}"><c:set var="page_url" scope="request"><%=path %>/epp/web/org/article.do?companyId=${companyId}&orgId=${orgId}&orgnavId=${orgnavId}&listflg=1</c:set></c:if>
			<c:if test="${listflg!=1}"><c:set var="page_url" scope="request">/edu/${companyId }/${orgId }/articles/${orgnavId }</c:set></c:if>
			<c:set var="url_rewrite" scope="request" value="true"/>
			<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
		</div>
		<div class="clr"></div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#e5e6e8');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>