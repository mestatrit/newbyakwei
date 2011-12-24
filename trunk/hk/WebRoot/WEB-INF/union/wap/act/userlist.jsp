<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpActUser"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">活动报名人员(${userCount })</div>
<c:if test="${actadmin}">
	<div class="row">
		<hk:data key="view.cmpactuser.checkflg${checkflg}"/><br/>
		<hk:form method="get" action="/union/cmpact_user.do">
			<hk:hide name="uid" value="${uid}"/>
			<hk:hide name="actId" value="${actId}"/>
			<hk:select name="checkflg" checkedvalue="${checkflg}">
				<hk:option value="-1" data="所有" res="true"/>
				<hk:option value="<%=CmpActUser.CHECKFLG_UNCHECKED %>" data="view.cmpactuser.checkflg0" res="true"/>
				<hk:option value="<%=CmpActUser.CHECKFLG_N %>" data="view.cmpactuser.checkflg1" res="true"/>
				<hk:option value="<%=CmpActUser.CHECKFLG_CANDIDATE %>" data="view.cmpactuser.checkflg2" res="true"/>
				<hk:option value="<%=CmpActUser.CHECKFLG_Y %>" data="view.cmpactuser.checkflg3" res="true"/>
			</hk:select><br/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
</c:if>
<c:if test="${fn:length(list)==0}"><hk:data key="nodataview"/></c:if>
<c:if test="${fn:length(list)>0}">
<c:forEach var="u" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
<div class="row ${clazz_var }">
<hk:a clazz="split-r" href="/union/home.do?uid=${uid }&userId=${u.userId}">${u.user.nickName}</hk:a>
<c:if test="${needcheck && actadmin }">
	<span class="split-r"><hk:data key="view.cmpactuser.checkflg${u.checkflg}"/></span>
	<c:if test="${!u.checkOk}">
		<hk:a href="/union/op/cmpact_checky.do?uid=${uid}&userId=${u.userId }&actId=${actId }">设为通过</hk:a>
	</c:if>
	<c:if test="${!u.checkNo}">
		<hk:a href="/union/op/cmpact_checkn.do?uid=${uid}&userId=${u.userId }&actId=${actId }">设为不通过</hk:a>
	</c:if>
	<c:if test="${!u.candidate}">
		<hk:a href="/union/op/cmpact_checkcandidate.do?uid=${uid}&userId=${u.userId }&actId=${actId }">设为候补</hk:a>
	</c:if>
</c:if>
</div>
</c:forEach></c:if>
<div class="row"><hk:simplepage2 href="/union/cmpact_user.do?uid=${uid}&actId=${actId }"/></div>
<div class="row">
<hk:a href="/union/cmpact.do?uid=${uid}&actId=${actId }">返回${act.name}</hk:a><br/>
<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>