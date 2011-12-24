<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="title"><hk:data key="view.companyreflaba"/></c:set>
<hk:wap title="${vo.company.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${title }</div>
	<c:set var="addlabastr" value="companyId=${companyId}" scope="request"/>
	<jsp:include page="../inc/labavo2.jsp"></jsp:include>
	<c:if test="${fn:length(labavolist)>0}">
		<hk:simplepage href="/e/cmp_reflaba.do?companyId=${companyId}"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0}"><div class="hang"><hk:data key="nodataview"/></div></c:if>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>