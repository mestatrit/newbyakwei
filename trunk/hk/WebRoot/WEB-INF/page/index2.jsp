<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<c:if test="${fn:length(pinkboxlist)>0}">
		<div class="hang even">开宝箱(${sys_zone_pcity.city.city })</div>
		<c:forEach var="box" items="${pinkboxlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }"><hk:a href="/box/box.do?boxId=${box.boxId}">${box.name } ${box.totalCount-box.openCount}/${box.totalCount}</hk:a></div>
		</c:forEach>
		<c:if test="${fn:length(pinkboxlist)==3}">
			<div class="hang"><hk:a href="/box/market.do?cityId=${cityId}&auto=1">更多</hk:a></div>
		</c:if>
	</c:if>
	<c:if test="${fn:length(boxfeedvolist)>0}">
		<div class="hang even">宝箱动态</div>
		<c:forEach var="vo" items="${boxfeedvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">${vo.content } <c:set var="createTime" value="${vo.last.createTime}" scope="request"/><span class="ruo s"><%=JspDataUtil.outStyleTime(request) %></span>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(loglist)>0}">
		<div class="hang even">大家在哪</div>
		<c:forEach var="log" items="${loglist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${log.userId}">${log.user.nickName }</hk:a>在<hk:a href="/e/cmp.do?companyId=${log.companyId}">${log.company.name}</hk:a> <c:set var="createTime" value="${log.createTime}" scope="request"/><span class="ruo s"><%=JspDataUtil.outStyleTime(request) %></span>
			</div>
		</c:forEach>
	</c:if>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>