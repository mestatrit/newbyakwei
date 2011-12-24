<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.svr.CmpActService"%><%@page import="com.hk.bean.CmpActKind"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">活动列表</c:set>
<c:set var="mgr_content" scope="request">
<div>
<ul class="orderlist">
	<li class="bg1">
		<table class="infotable" cellpadding="0" cellspacing="0">
			<tr class="tr-title">
				<td width="200px">名称</td>
				<td width="100px">状态</td>
				<td width="200px"></td>
			</tr>
		</table>
	</li>
	<c:forEach var="act" items="${list}">
	<li class="" onmouseout="this.className='';" onmouseover="this.className='bg2';">
		<table class="infotable" cellpadding="0" cellspacing="0">
			<tr class="tr-title">
				<td width="200px">${act.name }</td>
				<td width="100px"><hk:data key="view.cmpact.actstatus${act.actStatus}"/></td>
				<td width="200px">
					<a href="<%=path %>/e/op/auth/act.do?actId=${act.actId}&companyId=${companyId}" class="split-r">详细信息</a>
					<c:if test="${!act.run && !act.adminPause}">
						<a id="run${act.actId }" href="javascript:setrun(${act.actId })" class="split-r">运行</a>
					</c:if>
					<c:if test="${act.run}">
						<a id="pause${act.actId }" href="javascript:setpause(${act.actId })" class="split-r">暂停</a>
						<a id="invalid${act.actId }" href="javascript:setinvalid(${act.actId })" class="split-r">作废</a>
					</c:if>
				</td>
			</tr>
		</table>
	</li>
	</c:forEach>
</ul>
</div>
<div>
<hk:page midcount="10" url="/e/op/auth/act_list.do?companyId=${companyId}"/>
<div class="clr"></div>
</div>
<script type="text/javascript">
function setrun(id){
	showSubmitDivForObj('run'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/act_setrun.do?companyId=${companyId}&actId='+id,
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
		url:'<%=path %>/e/op/auth/act_setpause.do?companyId=${companyId}&actId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function setinvalid(id){
	showSubmitDivForObj('pause'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/act_setinvalid.do?companyId=${companyId}&actId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>