<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">宝箱</div>
<div class="row">
	<c:if test="${prize!=null}">${prize.tip}</c:if>
	<c:if test="${prize==null}"><hk:data key="view.box.openresult.noprize" arg0="${box.name}"/></c:if>
</div>
<div class="row">
	<hk:form method="get" action="/union/box_list.do">
		<hk:hide name="uid" value="${uid}"/>
		<hk:submit value="返回继续开宝箱"/>
	</hk:form>
</div>
<div class="row"><hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a></div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>