<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.svr.CmpActService"%><%@page import="com.hk.bean.CmpActKind"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
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
	分期费用说明：<br/>
	<c:if test="${fn:length(cmpActCostList)>0}">
	<ul class="orderlist">
		<c:forEach var="cost" items="${cmpActCostList}">
			<li onmouseout="this.className='';" onmouseover="this.className='bg2';">
				<table class="infotable" cellpadding="0" cellspacing="0">
					<tr>
					<td width="100px">${cost.name }</td>
					<td width="100px">${cost.actCost }元</td>
					<td width="300px">${cost.intro }</td>
					<td width="100px">
						<a id="costedit${cost.costId }" href="javascript:toeditcost(${cost.costId })" class="split-r">修改</a>
						<a id="costdel${cost.costId }" href="javascript:delcost(${cost.costId })">删除</a>
					</td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
	</c:if>
	<div><a href="javascript:tocreatecost()">添加分期费用说明</a></div>
	阶梯费用说明：<br/>
	<c:if test="${fn:length(cmpActStepCostList)>0}">
		<ul class="orderlist">
			<c:forEach var="cost" items="${cmpActStepCostList}">
				<li onmouseout="this.className='';" onmouseover="this.className='bg2';">
					<table class="infotable" cellpadding="0" cellspacing="0">
						<tr>
						<td width="100px">${cost.userCount }人</td>
						<td width="100px">${cost.actCost }元</td>
						<td width="100px">
							<a id="stepedit${cost.costId }" href="javascript:toeditstep(${cost.costId })" class="split-r">修改</a>
							<a id="stepdel${cost.costId }" href="javascript:delstep(${cost.costId })">删除</a>
						</td>
						</tr>
					</table>
				</li>
			</c:forEach>
		</ul>
	</c:if>
	<div><a href="javascript:tocreatestep()">添加阶梯费用说明</a></div>
	活动内容：
	${act.intro }<br/>
	注意事项：
	<span class="yzm">${act.spintro }</span>
</div>
<div class="text_14" align="center">
<hk:button value="修改活动信息" clazz="btn2 split-r" onclick="toedit()"/>
<a href="<%=path %>/e/op/auth/act_list.do?companyId=${companyId}">返回</a>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPACTCOST_NAME_ERROR %>={objid:"name"};
var err_code_<%=Err.CMPACTCOST_INTRO_ERROR %>={objid:"intro"};
var err_code_<%=Err.CMPACTCOST_ACTCOST_ERROR %>={objid:"actCost"};
var err_code_<%=Err.CMPACTSTEPCOST_USERCOUNT_ERROR %>={objid:"userCount"};
var err_code_<%=Err.CMPACTSTEPCOST_ACTCOST_ERROR %>={objid:"actCost"};
function tocreatecost(){
	var html='<hk:form oid="cost_frm" target="hideframe" onsubmit="return subcostfrm(this.id)" action="/e/op/auth/act_createcost.do"><hk:hide name="companyId" value="${companyId}"/><hk:hide name="actId" value="${actId}"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">名称</td> <td> <hk:text name="name" clazz="text" maxlength="20"/> <div class="error" id="name_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="actCost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="actCost_error"></div> </td> </tr> <tr> <td>说明</td> <td> <span class="ruo">不能超过100字</span><br/> <textarea name="intro" class="text_area"></textarea> <div class="error" id="intro_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('cost_win',500,400,'添加费用说明',html,"hideWindow('cost_win');clearBg();");
}
function toeditcost(id){
	showSubmitDivForObj('costedit'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/act_loadcost.do?companyId=${companyId}&costId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow('cost_win',500,400,'修改分期费用',data,"hideWindow('cost_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
function tocreatestep(){
	var html='<hk:form oid="step_frm" target="hideframe" onsubmit="return substepfrm(this.id)" action="/e/op/auth/act_createstep.do"><hk:hide name="companyId" value="${companyId}"/><hk:hide name="actId" value="${actId}"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">人数</td> <td> <hk:text name="userCount" clazz="text" maxlength="20"/> <div class="error" id="userCount_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="actCost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="actCost_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('step_win',500,300,'添加阶梯费用',html,"hideWindow('step_win');clearBg();");
}
function toeditstep(id){
	showSubmitDivForObj('stepedit'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/act_loadstep.do?companyId=${companyId}&costId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow('step_frm',500,300,'修改阶梯费用',data,"hideWindow('step_frm');clearBg();");
			hideSubmitDiv();
		}
	});
}
function toedit(){
	tourl("<%=path%>/e/op/auth/act_toupdate.do?companyId=${companyId}&actId=${actId}");
}
function subcostfrm(frmid){
	showSubmitDiv(frmid);
	validateClear('name');
	validateClear('actCost');
	validateClear('intro');
	return true;
}
function substepfrm(frmid){
	showSubmitDiv(frmid);
	validateClear('userCount');
	validateClear('actCost');
	return true;
}
function costok(error,error_msg,respValue){
	refreshurl();
}
function stepok(error,error_msg,respValue){
	refreshurl();
}
function costerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
	clearBg();
}
function steperror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
	clearBg();
}
function delcost(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj('costdel'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/act_delcost.do?companyId=${companyId}&costId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function delstep(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj('stepdel'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/act_delstep.do?companyId=${companyId}&costId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>