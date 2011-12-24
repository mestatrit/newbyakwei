<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${company.name }" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">${company.name }</div>
	<div class="hang even"><c:set var="done"><%=CmpTip.DONEFLG_DONE %></c:set><c:set var="todo"><%=CmpTip.DONEFLG_TODO %></c:set>
		<c:if test="${doneflg==todo}"><c:set var="todocss">nn</c:set></c:if>
		<c:if test="${doneflg==done}"><c:set var="donecss">nn</c:set></c:if>
		<hk:a href="/e/cmp_tips.do?companyId=${companyId}&doneflg=${done }" clazz="${donecss}"><hk:data key="view2.people.tips"/></hk:a> 
		|
		<hk:a href="/e/cmp_tips.do?companyId=${companyId}&doneflg=${todo }" clazz="${todocss}"><hk:data key="view2.venue_user_todolist"/></hk:a>
	</div>
	<jsp:include page="../../inc/cmptiplist_inc.jsp"></jsp:include>
	<div class="hang"><hk:simplepage2 href="/e/cmp_tips.do?companyId=${companyId}&doneflg=${doneflg }"/></div>	
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>