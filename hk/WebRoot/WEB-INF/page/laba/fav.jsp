<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${user.nickName}收藏的喇叭 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:rmBlankLines rm="true">
		<hk:a href="/home.do?userId=${user.userId}">主页</hk:a>|
		<hk:a href="/laba/userlabalist.do?userId=${userId}">喇叭</hk:a>|
		<hk:a href="/${userId}/replies">回应</hk:a>|
		<hk:a href="/tag/utlist.do?userId=${userId}">频道</hk:a>
	</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=fav&ouserId=${userId }" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/laba/fav.do?userId=${userId}"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0}">没有数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>