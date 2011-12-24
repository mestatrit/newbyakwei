<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="喇叭搜索 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/laba/search.do">
			<hk:hide name="sfrom" value="${sfrom}"/>
			<hk:text name="sw" value="${sw}"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=labasearch&sw=${enc_sw }&sfrom=${sfrom }" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/laba/search.do?sw=${enc_sw}&sfrom=${sfrom }"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0 && not empty sw}">没有数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>