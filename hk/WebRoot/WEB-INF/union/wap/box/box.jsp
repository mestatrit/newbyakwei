<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">${box.name }</div>
<div class="row">
	${box.intro}<br/>
	${box.name}的奖品有:<br/>
	<c:forEach var="p" items="${prizelist}" varStatus="idx"><div class="row">${p.name} ${p.pcount}个</div></c:forEach>
	<c:if test="${not empty cmpProduct.intro}">${cmpProduct.intro }</c:if>
</div>
<div class="row">
	<c:if test="${onlysmsopen}">
		<div class="info"><hk:data key="view.box.smstip"/></div>
	</c:if>
	<c:if test="${!onlysmsopen && begin && !stop}">
		<hk:form action="/union/op/box_open.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="uid" value="${uid}"/>
			<hk:submit value="开箱子"/>
		</hk:form>
	</c:if>
</div>
<c:if test="${smsandweb && not empty box.boxKey}">
<c:set var="tipinfo"><span class="info">${box.boxKey }</span></c:set>
<div class="row"><hk:data key="view.box.smsopenboxtip" arg0="${tipinfo}"/></div>
</c:if>
<div class="row">
	<hk:a href="/union/box_list.do?uid=${uid}">返回</hk:a><br/>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>