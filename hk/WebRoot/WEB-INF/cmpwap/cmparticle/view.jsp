<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
	<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row even">
${cmpArticle.title }<br/>
<fmt:formatDate value="${cmpArticle.createTime}" pattern="yyyy-MM-dd HH:mm"/>
</div>
<div class="row">
	<c:if test="${topCmpFile!=null}">
	<div>
		<c:if test="${topCmpFile.imageShow}">
			<img src="${topCmpFile.cmpFilePic240 }"/>
		</c:if>
	</div>
	</c:if>
${cmpArticleContent.content }
<c:forEach var="cf" items="${list}">
	<div class="row">
		<c:if test="${cf.imageShow}">
			<img src="${cf.cmpFilePic240 }"/>
		</c:if>
	</div>
</c:forEach>
</div>
<c:if test="${cmpNav.articleList}">
<div class="row even">
<a href="<%=path %>/epp/web/cmparticle_wap.do?companyId=${companyId}&navId=${navId}&needlist=1"><hk:data key="epp.return"/></a>
</div>
</c:if>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>