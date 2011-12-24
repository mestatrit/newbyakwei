<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.nearby.hkuser"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.nearby.hkuser"/></div>
	<c:if test="${fn:length(uservolist)==0}">没有数据显示</c:if>
	<c:if test="${fn:length(uservolist)>0}">
		<c:set var="addStr" value="from=hkuser" scope="request"/>
		<jsp:include page="../inc/uservo.jsp"></jsp:include>
		<hk:simplepage href="/user/hkuser.do"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>