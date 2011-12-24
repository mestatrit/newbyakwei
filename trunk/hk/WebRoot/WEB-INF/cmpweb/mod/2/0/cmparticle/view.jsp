<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="tagdata"><c:forEach var="ref" items="${tagreflist}">${ref.cmpArticleTag.name }|</c:forEach></c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpArticle.title }|${cmpNav.name }|${o.name}|${tagdata}"/>
<meta name="description" content="${cmpArticle.title }|${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="title_value" scope="request">
${cmpArticle.title }|${cmpNav.name }${tagdata}
</c:set>
<c:set var="html_title" scope="request">
	<hk:data key="epp.login" />
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/leftinc.jsp"></jsp:include>
	<div class="pr">
		<div class="mod">
			<div class="mainnav">
				<a href="http://<%=request.getServerName() %>">首页</a>
				<c:if test="${parent_cmpNav!=null}">
				 &gt; <a href="/column/${companyId }/${parent_cmpNav.oid }/">${parent_cmpNav.name }</a>
				</c:if>
				 &gt; <a href="/column/${companyId }/${cmpNav.oid }/">${cmpNav.name }</a>
			</div>
		</div>
		<div class="mod">
			<div class="mod_tit">${cmpNav.name }</div>
			<div class="content">
				<c:if test="${fn:length(after_cmparticle_list)>0}">
					<div class="fr" style="font-weight: bold;font-size: 30px;">
						<a href="<%=path %>/epp/web/cmparticle_next.do?companyId=${companyId }&oid=${oid }&navId=${navId}">&gt;&gt;</a>
					</div>
				</c:if>
				<c:if test="${!cmpArticle.hideTitle}">
					<div style="margin-right: 50px;text-align: center;"><h1>${cmpArticle.title }</h1></div>
					<div class="divrow" style="text-align: center;"><fmt:formatDate value="${cmpArticle.createTime}" pattern="yyyy-MM-dd"/></div>
				</c:if>
				<c:if test="${not empty cmpInfo.articlead}">${cmpInfo.articlead }</c:if>
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
		</div>
	</div>
	<div class="clr"></div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>