<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">${act.name }</div>
<c:if test="${act.userNeedCheck && cmpActUser!=null}">
	<div class="row">
		<c:if test="${!cmpActUser.checkOk}"><hk:data key="view.cmpactuser.waitcheck"/></c:if>
		<c:if test="${cmpActUser.checkOk}"><hk:data key="view.cmpactuser.checkflg3"/></c:if>
	</div>
</c:if>
<div class="row">
地区：${pcity.name }<br/>
时间从 <fmt:formatDate value="${act.beginTime}" pattern="yyyy-MM-dd HH:mm"/> 到 <fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/><br/>
<c:if test="${not empty act.addr}">地点：${act.addr }<br/></c:if>
<c:if test="${act.userLimitCount>0}">人数：${act.userLimitCount }<br/></c:if>
<c:if test="${act.actCost>0}">费用：${act.actCost }元<br/></c:if>
<c:if test="${not empty act.actKey}">发送短信${act.actKey}到1066916025进行报名或者通过网页下面的报名按钮进行报名<br/></c:if>
活动类型：${cmpActKind.name }<br/>
<c:if test="${fn:length(cmpActCostList)>0}">
	分期费用说明：<br/>
	<c:forEach var="cost" items="${cmpActCostList}">
	<p>${cost.name }，${cost.actCost }元，${cost.intro }</p>
	</c:forEach>
</c:if>
<c:if test="${fn:length(cmpActStepCostList)>0}">
	阶梯费用说明：<br/>
	<c:forEach var="cost" items="${cmpActStepCostList}">
	<p>${cost.userCount }人，${cost.actCost }元</p>
	</c:forEach>
</c:if>
活动内容：
${act.intro }<br/>
<span class="alert">注意事项：${act.spintro }</span>
</div>
<div class="row">
<c:if test="${act.onlyMemberJoin}">
	<c:if test="${!usermember || usermember==null}"><hk:data key="view.cmpact.onlymemberjoin"/></c:if>
</c:if>
<c:if test="${(act.onlyMemberJoin && usermember)|| !act.onlyMemberJoin}">
	<c:if test="${cmpActUser!=null}">
		<hk:form action="/union/op/cmpact_unjoin.do">
			<hk:hide name="actId" value="${actId}"/>
			<hk:hide name="uid" value="${uid}"/>
			<hk:submit value="退出活动"/>
		</hk:form>
	</c:if>
	<c:if test="${cmpActUser==null}">
		<hk:form action="/union/op/cmpact_join.do">
			<hk:hide name="actId" value="${actId}"/>
			<hk:hide name="uid" value="${uid}"/>
			<hk:submit value="我要报名"/>
		</hk:form>
	</c:if>
</c:if>
</div>
<c:if test="${moreactuser}">
<div class="row"><hk:a href="/union/cmpact_user.do?uid=${uid}&actId=${actId }">活动报名人员(${userCount })</hk:a></div>
</c:if>
<div class="row">
<hk:a href="/union/cmpact_list.do?uid=${uid}">返回</hk:a><br/>
<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>