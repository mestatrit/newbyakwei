<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${act.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${act.name}</div>
	<div class="hang even">
		时间：<fmt:formatDate value="${act.beginTime}" pattern="yyyy-MM-dd HH:mm"/>
				到
				<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"/><br/>
		地区：${pcity.name }<br/>
		<c:if test="${not empty act.addr}">地点：${act.addr }<br/></c:if>
		<c:if test="${act.userLimitCount>0}">人数：${act.userLimitCount }<br/></c:if>
		<c:if test="${act.actCost>0}">费用：${act.actCost }<br/></c:if>
		<c:if test="${not empty act.actKey}">短信报名关键字：${act.actKey}<br/></c:if>
		活动类型：${cmpActKind.name }<br/>
		<c:if test="${fn:length(cmpActCostList)>0}">
			分期费用说明：<br/>
			<table>
				<c:forEach var="cost" items="${cmpActCostList}">
					<tr>
					<td width="100px">${cost.name }</td>
					<td width="100px">${cost.actCost }元</td>
					<td>${cost.intro }</td>
					</tr>
				</c:forEach>
		</table>
		</c:if>
		<c:if test="${fn:length(cmpActStepCostList)>0}">
			阶梯费用说明：<br/>
			<table>
				<c:forEach var="cost" items="${cmpActStepCostList}">
					<tr>
					<td width="100px">${cost.userCount }人</td>
					<td width="100px">${cost.actCost }元</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		活动内容：
		${act.intro }<br/>
		注意事项：${act.spintro }
	</div>
	<div class="hang">
		<c:if test="${act.adminPause}">
			<hk:form action="/e/admin/cmpact_setactrun.do">
				<hk:hide name="actId" value="${actId}"/>
				<hk:submit value="运行"/>
			</hk:form>
		</c:if>
		<c:if test="${!act.adminPause}">
			<hk:form action="/e/admin/cmpact_setactadminpause.do">
				<hk:hide name="actId" value="${actId}"/>
				<hk:submit value="暂停"/>
			</hk:form>
		</c:if>
	</div>
	<div class="hang">
	<hk:a href="/e/admin/cmpact.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>