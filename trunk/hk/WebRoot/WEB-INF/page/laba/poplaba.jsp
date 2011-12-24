<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="view.poplaba"/></c:set>
<hk:wap title="${view_title} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.poplaba"/></div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=poplaba" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/laba/poplaba.do"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>