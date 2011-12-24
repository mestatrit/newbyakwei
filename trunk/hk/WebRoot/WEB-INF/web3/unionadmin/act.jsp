<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">${act.name}</c:set>
<c:set var="mgr_content" scope="request">
<div class="text_14">
	<h3>${act.name}</h3><br/>
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
		<table class="infotable" cellpadding="0" cellspacing="0">
			<c:forEach var="cost" items="${cmpActCostList}">
				<tr>
				<td width="100px">${cost.name }</td>
				<td width="100px">${cost.actCost }元</td>
				<td width="400px">${cost.intro }</td>
				</tr>
			</c:forEach>
	</table>
	</c:if>
	<c:if test="${fn:length(cmpActStepCostList)>0}">
		阶梯费用说明：<br/>
		<table class="infotable" cellpadding="0" cellspacing="0">
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
	注意事项：
	<span class="yzm">${act.spintro }</span>
</div>
<div class="text_14" align="center">
<c:if test="${!act.adminPause}">
	<hk:button clazz="btn split-r" value="暂停" onclick="setpause(${act.actId})"/>
</c:if>
<c:if test="${act.adminPause}">
	<hk:button clazz="btn split-r" value="运行" onclick="setrun(${act.actId})"/>
</c:if>
<a href="<%=path %>/cmpunion/op/cmpact.do?uid=${uid}">返回</a>
</div>
<script type="text/javascript">
function setrun(id){
	showSubmitDivForObj('run'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/cmpact_setrun.do?uid=${uid}&actId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setpause(id){
	showSubmitDivForObj('pause'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/cmpunion/op/cmpact_setpause.do?uid=${uid}&actId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>