<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="title"><hk:data key="view.who's_review" arg0="/e/cmp.do?companyId=${companyId}" arg1="${vo.company.name}"/></c:set>
<hk:wap title="${vo.company.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">${title }</div>
	<c:set var="addlabastr" value="companyId=${companyId}" scope="request"/>
	<jsp:include page="../../inc/companyreviewvo.jsp"></jsp:include>
	<c:if test="${fn:length(reviewvolist)>0}">
		<hk:simplepage href="/review/op/op_my.do?companyId=${companyId}"/>
	</c:if>
	<c:if test="${fn:length(reviewvolist)==0}"><div class="hang"><hk:data key="nocompanyreviewdata"/></div></c:if>
	<div class="hang" onkeydown="submitLaba(event)">
		<c:set var="form_action" value="/review/op/op_add.do" scope="request"/>
		<jsp:include page="../../inc/reviewform.jsp"></jsp:include>
	</div>
	<div class="hang"><hk:a href="/e/cmp.do?companyId=${companyId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>