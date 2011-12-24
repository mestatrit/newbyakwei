<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="html_body_content" scope="request">
<div>
	<c:if test="${fn:length(userlist)>0}">
		<div><h1 class="title3"><hk:data key="view2.user_search_result"/></h1></div>
		<ul class="smallhead48">
			<c:forEach var="u" items="${userlist}">
			<li><a href="/user/${u.userId }/"><img title="${u.nickName }" src="${u.head48Pic }" alt="${u.nickName }" /></a></li>
			</c:forEach>
		</ul>
		<div class="clr"></div>
		<c:set var="page_url" scope="request"><%=path %>/s_nickname.do?key=${enc_key}</c:set>
		<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
	</c:if>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>