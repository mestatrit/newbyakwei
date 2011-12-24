<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="写新评论 - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">写新评论</div>
	<div class="hang" onkeydown="submitLaba(event)">
		<c:set var="review_form_action" value="/epp/review_create.do" scope="request"/>
		<jsp:include page="reviewform.jsp"></jsp:include>
	</div>
	<div class="hang"><hk:a href="/epp/review_list.do?companyId=${companyId }"><hk:data key="view.return"/></hk:a></div>
	<div class="hang"><hk:a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>