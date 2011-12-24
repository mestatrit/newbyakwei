<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib	uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
	String path = request.getContextPath();
	EppViewUtil.loadCmpArticleList(request);
%>
<c:if test="${fn:length(cmparticlelist)>0}">
	<!-- 文章类型 -->
	<div class="innermod">
		<h3 class="home_mod_title">${right_nav.name }</h3>
		<ul class="article">
			<c:forEach var="article" items="${cmparticlelist}">
				<li>
					<div>
						<div class="flg0"></div>
						<div class="mid_text"><a href="/article/${companyId}/${article.cmpNavOid}/${article.oid}.html">${article.title }</a></div>
						<div class="time">
							<fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/>
						</div>
						<div class="clr"></div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>