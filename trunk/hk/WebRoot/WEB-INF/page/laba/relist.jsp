<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="喇叭的回复 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">喇叭的回复</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=relaba&relabaId=${labaId }" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/laba/relist.do?labaId=${labaId}" returndata="返回" returnhref="/laba/back.do?${queryString}"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>