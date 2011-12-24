<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">活动列表</c:set>
<c:set var="mgr_content" scope="request">
<div>
	<ul class="orderlist">
		<li class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="200px">名称</td>
				<td width="100px">状态</td>
				<td></td>
			</tr>
			</table>
		</li>
		<c:if test="${fn:length(list)==0}">
			<li>
				<div class="heavy" align="center"><hk:data key="nodatainthispage"/></div>
			</li>
		</c:if>
		<c:if test="${fn:length(list)>0}">
			<c:forEach var="act" items="${list}">
				<li onmouseover="this.className='bg2';" onmouseout="this.className='';">
					<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
						<td width="200px"><a href="<%=path %>/cmpunion/op/cmpact_act.do?uid=${uid }&actId=${act.actId}">${act.name }</a></td>
						<td width="100px">
							<hk:data key="view.cmpact.actstatus${act.actStatus}"/>
						</td>
						<td>
							<c:if test="${!act.adminPause}">
								<a id="pause${act.actId }" href="javascript:setpause(${act.actId})">暂停</a> 
							</c:if>
							<c:if test="${act.adminPause}">
								<a id="run${act.actId }"  href="javascript:setrun(${act.actId})">运行</a> 
							</c:if>
						</td>
					</tr>
					</table>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div>
		<hk:page midcount="10" url="/cmpunion/op/cmpact.do?uid=${uid}"/>
		<div class="clr"></div>
	</div>
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