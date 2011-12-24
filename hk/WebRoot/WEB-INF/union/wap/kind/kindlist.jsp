<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2"><c:if test="${fn:length(list2)>0}">所有分类&gt;<hk:rmstr value="&gt;"><c:forEach var="k" items="${list2}"><c:if test="${k.kindId!=cmpUnionKind.kindId}"><hk:a href="/union/kind.do?uid=${uid}&kindId=${k.kindId }&p=${p }">${k.name }</hk:a></c:if><c:if test="${k.kindId==cmpUnionKind.kindId}">${k.name }</c:if>&gt;</c:forEach></hk:rmstr></c:if></div>
<c:if test="${fn:length(kindlist)==0}"><hk:data key="nodataview"/></c:if>
<c:if test="${fn:length(kindlist)>0}">
	<c:forEach var="k" items="${kindlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="row ${clazz_var }"><hk:a href="/union/kind.do?uid=${uid }&kindId=${k.kindId}&p=${p }">${k.name}</hk:a></div>
	</c:forEach>
	<div class="row"><hk:simplepage2 href="/union/kind.do?uid=${uid}&kindId=${cmpUnionKind.kindId }&p=${p }"/></div>
</c:if>
<div class="row">
	<c:if test="${fn:length(list2)>0}">所有分类&gt;<hk:rmstr value="&gt;"><c:forEach var="k" items="${list2}"><c:if test="${k.kindId!=cmpUnionKind.kindId}"><hk:a href="/union/kind.do?uid=${uid}&kindId=${k.kindId }&p=${p }">${k.name }</hk:a></c:if><c:if test="${k.kindId==cmpUnionKind.kindId}">${k.name }</c:if>&gt;</c:forEach></hk:rmstr><br/></c:if>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>