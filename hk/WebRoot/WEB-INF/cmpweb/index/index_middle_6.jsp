<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
EppViewUtil.loadCmpBbsList(request);%>
<c:if test="${fn:length(cmpbbslist)>0}"><!-- 论坛 -->
<div class="innermod">
	<h3 class="home_mod_title">${right_nav.name }</h3>
	<ul class="article">
		<c:forEach var="bbs" items="${cmpbbslist}">
		<li>
			<div class="mid_text">
			<span class="flg0"></span>
			<a href="<%=path %>/epp/web/cmpbbs_view.do?companyId=${companyId}&bbsId=${bbs.bbsId}">${bbs.title }</a>
			</div>
			<div class="time">
				<fmt:formatDate value="${bbs.lastReplyTime}" pattern="yyyy-MM-dd"/>
			</div>
			<div class="clr"></div>
		</li>
		</c:forEach>
	</ul>
</div>
</c:if>