<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.svr.CmpActService"%><%@page import="com.hk.bean.CmpActKind"%>
<%@page import="com.hk.bean.CmpAct"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
CmpActService cmpActService = (CmpActService) HkUtil.getBean("cmpActService");
List<CmpActKind> kindlist = cmpActService.getCmpActKindList();
request.setAttribute("kindlist", kindlist);
request.setAttribute("pcityId", 0);
%>
<c:set var="html_title" scope="request">创建活动</c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css">
<div>
<hk:form oid="actfrm" onsubmit="return subactfrm(this.id)" action="/e/op/auth/act_create.do" target="hideframe">
<div id="hide_cost_html"></div>
<div id="hide_step_html"></div>
	<hk:hide name="companyId" value="${companyId}"/>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px"></td>
			<td>
				<div class="text_14 yzm">*为必填项</div>
			</td>
		</tr>
		<tr>
			<td>名称<span class="needipt">*</span></td>
			<td>
				<hk:text name="name" clazz="text"/>
				<div class="error" id="name_error"></div>
			</td>
		</tr>
		<tr>
			<td>类型<span class="needipt">*</span></td>
			<td>
				<hk:select name="kindId">
					<hk:option value="0" data=""/>
					<c:forEach var="k" items="${kindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
					</c:forEach>
				</hk:select>
				<div class="error" id="name_error"></div>
 			</td>
		</tr>
		<tr>
			<td>地区<span class="needipt">*</span></td>
			<td>
				<jsp:include page="../../../web4/inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(0);
				</script>
				<br/>
				<div id="zone_error" class="error"></div>
			</td>
		</tr>
		<tr>
			<td>人数</td>
			<td>
				<hk:text name="userLimitCount" clazz="text"/>
				<div class="error" id="userLimitCount_error"></div>
			</td>
		</tr>
		<tr>
			<td>地址</td>
			<td>
				<hk:textarea name="addr" clazz="text_area"/>
				<div class="error" id="addr_error"></div>
			</td>
		</tr>
		<tr>
			<td>短信关键字</td>
			<td>
				<span class="ruo"><hk:data key="view.cmddata.name.tip"/></span><br/>
				<hk:text name="actKey" clazz="text"/>
				<div class="error" id="actKey_error"></div>
			</td>
		</tr>
		<tr>
			<td>费用</td>
			<td>
				<hk:text name="actCost" clazz="text_short_1"/>元/每人
				<div class="error" id="actCost_error"></div>
				<a href="javascript:tocreatecost()">添加分期费用说明</a>，没有可不填<br/>
				<div id="cost_html"></div><br/>
				<a href="javascript:tocreatestep()">添加阶梯费用说明</a>，没有可不填<br/>
				<div id="step_html"></div>
			</td>
		</tr>
		<tr>
			<td>审核方式<span class="needipt">*</span></td>
			<td>
				<hk:radioarea name="userNeedCheckflg" checkedvalue="<%=CmpAct.USERNEEDCHECKFLG_N %>">
				<span class="split-r"><hk:radio value="<%=CmpAct.USERNEEDCHECKFLG_N %>" data="view.cmpact.userneedcheckflg0" res="true"/></span>
				<span class="split-r"><hk:radio value="<%=CmpAct.USERNEEDCHECKFLG_Y %>" data="view.cmpact.userneedcheckflg1" res="true"/></span>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td>时间<span class="needipt">*</span></td>
			<td>
				<div>
					开始时间：<br/>
					<hk:text name="bd" clazz="text_short_1 date-pick1"/>日 
					<hk:text name="bt" clazz="text_short_1"/> <span class="ruo">格式为HH:mm</span>
				</div>
				<div>
					结束时间：<br/>
					<hk:text name="ed" clazz="text_short_1 date-pick2"/>日 
					<hk:text name="et" clazz="text_short_1"/> <span class="ruo">格式为HH:mm</span>
				</div>
				<div class="error" id="time_error"></div>
			</td>
		</tr>
		<tr>
			<td>活动内容<span class="needipt">*</span></td>
			<td>
				<span class="ruo">最多3000字</span><br/>
				<hk:textarea name="intro" clazz="text_area" style="width:500px;height:300px"/>
				<div class="error" id="intro_error"></div>
			</td>
		</tr>
		<tr>
			<td>注意事项</td>
			<td>
				<span class="ruo">最多100字</span><br/>
				<hk:textarea name="spintro" clazz="text_area"/>
				<div class="error" id="spintro_error"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center"><hk:submit value="提交" clazz="btn"/></td>
		</tr>
	</table>
</hk:form>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPACT_NAME_ERROR %>={objid:"name"};
var err_code_<%=Err.CMPACT_KINDID_ERROR %>={objid:"kindId"};
var err_code_<%=Err.CMPACT_INTRO_ERROR %>={objid:"intro"};
var err_code_<%=Err.CMPACT_SPINTRO_ERROR %>={objid:"spintro"};
var err_code_<%=Err.CMPACT_BEGINTIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_ENDTIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_TIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_ADDR_ERROR %>={objid:"addr"};
var err_code_<%=Err.CMPACT_PCITYID_ERROR %>={objid:"pcityId"};
var err_code_<%=Err.CMDDATA_NAME_DUPLICATE %>={objid:"actKey"};
var err_code_<%=Err.CMPACT_PCITYID_ERROR %>={objid:"zone"};
$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString()).trigger('change');
$('.date-pick2').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString()).trigger('change');
function tocreatecost(){
	var html='<hk:form oid="cost_frm" onsubmit="return subcostfrm(this.id,-1)" action="#"> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">名称</td> <td> <hk:text name="costname" clazz="text" maxlength="20"/> <div class="error" id="costname_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="costcost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="costcost_error"></div> </td> </tr> <tr> <td>说明</td> <td> <span class="ruo">不能超过100字</span><br/> <textarea name="costintro" class="text_area"></textarea> <div class="error" id="costintro_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('cost_win',500,400,'添加费用说明',html,"hideWindow('cost_win');clearBg();");
}
function toeditcost(idx){
	var html='<hk:form oid="cost_frm" onsubmit="return subcostfrm(this.id,'+idx+')" action="#"> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">名称</td> <td> <hk:text name="costname" clazz="text" maxlength="20"/> <div class="error" id="costname_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="costcost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="costcost_error"></div> </td> </tr> <tr> <td>说明</td> <td> <span class="ruo">不能超过100字</span><br/> <textarea name="costintro" class="text_area"></textarea> <div class="error" id="costintro_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('cost_win',500,400,'修改费用说明',html,"hideWindow('cost_win');clearBg();");
	getObj('cost_frm').costname.value=costarr[idx][0];
	getObj('cost_frm').costcost.value=costarr[idx][1];
	getObj('cost_frm').costintro.value=toText(costarr[idx][2]);
}
function tocreatestep(){
	var html='<hk:form oid="step_frm" onsubmit="return substepfrm(this.id,-1)" action="#"> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">人数</td> <td> <hk:text name="stepusercount" clazz="text" maxlength="20"/> <div class="error" id="stepusercount_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="stepcost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="stepcost_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('step_win',500,300,'添加阶梯费用',html,"hideWindow('step_win');clearBg();");
}
function toeditstep(idx){
	var html='<hk:form oid="step_frm" onsubmit="return substepfrm(this.id,'+idx+')" action="#"> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">人数</td> <td> <hk:text name="stepusercount" clazz="text" maxlength="20"/> <div class="error" id="stepusercount_error"></div> </td> </tr> <tr> <td>费用</td> <td> <hk:text name="stepcost" clazz="text_short_1" maxlength="10"/>元 <div class="error" id="stepcost_error"></div> </td> </tr> <tr> <td></td> <td align="center"><hk:submit value="提交" clazz="btn"/></td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow('step_win',500,300,'修改阶梯费用',html,"hideWindow('step_win');clearBg();");
	getObj('step_frm').stepusercount.value=steparr[idx][0];
	getObj('step_frm').stepcost.value=steparr[idx][1];
}
var costarr=new Array();
var steparr=new Array();
function subcostfrm(frmid,idx){
	validateClear('costname');
	validateClear('costcost');
	validateClear('costintro');
	var frm=getObj(frmid);
	if(isEmpty(frm.costname.value) || frm.costname.length>20){
		setHtml("costname_error",'名称不能为空且不能超过20字');
		return false;
	}
	if(isEmpty(frm.costcost.value) || frm.costcost.length>10){
		setHtml("costcost_error",'费用不能为空且不能超过10字');
		return false;
	}
	if(frm.costintro.length>100){
		setHtml("costintro_error",'说明不能超过100字');
		return false;
	}
	if(idx==-1){
		costarr[costarr.length]=new Array(frm.costname.value,frm.costcost.value,toHtml(frm.costintro.value));
	}
	else{
		costarr[idx]=new Array(frm.costname.value,frm.costcost.value,toHtml(frm.costintro.value));
	}
	showCost();
	hideWindow('step_frm');clearBg();
	return false;
}
function substepfrm(frmid,idx){
	validateClear('stepusercount');
	validateClear('stepcost');
	var frm=getObj(frmid);
	if(isEmpty(frm.stepusercount.value) || frm.stepusercount.length>20){
		setHtml("stepusercount_error",'人数不能为空');
		return false;
	}
	if(isEmpty(frm.stepcost.value) || frm.stepcost.length>10){
		setHtml("stepcost_error",'费用不能为空且不能超过10字');
		return false;
	}
	if(idx==-1){
		steparr[steparr.length]=new Array(frm.stepusercount.value,frm.stepcost.value);
	}
	else{
		steparr[idx]=new Array(frm.stepusercount.value,frm.stepcost.value);
	}
	showStep();
	hideWindow('step_win');clearBg();
	return false;
}
function showCost(){
	s='<table class="infotable" cellpadding="0" cellspacing="0">';
	s+='<tr class="tr-title"><th>名称</th><th>费用</th><th>说明</th><th></th></tr>';
	for(var i=0;i<costarr.length;i++){
		s+='<tr>';
		s+='<td width="80px">'+costarr[i][0]+"</td>";
		s+='<td width="80px">'+costarr[i][1]+"</td>";
		s+='<td width="200px">'+costarr[i][2]+"</td>";
		s+='<td width="200px"><a href="javascript:toeditcost('+i+')">修改</a></td>';
		s+='</tr>';
	}
	s+='</table>';
	setHtml('cost_html',s);
}
function showStep(){
	s='<table class="infotable" cellpadding="0" cellspacing="0">';
	s+='<tr class="tr-title"><th>人数</th><th>费用</th><th></th></tr>';
	for(var i=0;i<steparr.length;i++){
		s+='<tr>';
		s+='<td width="80px">'+steparr[i][0]+"</td>";
		s+='<td width="80px">'+steparr[i][1]+"</td>";
		s+='<td width="200px"><a href="javascript:toeditstep('+i+')">修改</a></td>';
		s+='</tr>';
	}
	s+='</table>';
	setHtml('step_html',s);
}
function subactfrm(frmid){
	showSubmitDiv(frmid);
	validateClear('name');
	validateClear('addr');
	validateClear('zone');
	validateClear('userLimitCount');
	validateClear('actKey');
	validateClear('actCost');
	validateClear('time');
	validateClear('intro');
	validateClear('spintro');
	makeCostValue();
	makeStepValue();
	return true;
}
function makeCostValue(){
	var s='';
	for(var i=0;i<costarr.length;i++){
		s+='<input type="hidden" name="cmpActCost_name" value="'+costarr[i][0]+'"/>';
		s+='<input type="hidden" name="cmpActCost_cost" value="'+costarr[i][1]+'"/>';
		s+='<input type="hidden" name="cmpActCost_intro" value="'+costarr[i][2]+'"/>';
	}
	setHtml('hide_cost_html',s);
}
function makeStepValue(){
	var s='';
	for(var i=0;i<steparr.length;i++){
		s+='<input type="hidden" name="cmpActSetp_userCount" value="'+steparr[i][0]+'"/>';
		s+='<input type="hidden" name="cmpActSetp_cost" value="'+steparr[i][1]+'"/>';
	}
	setHtml('hide_step_html',s);
}
function acterror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function actok(error,error_msg,respValue){
	tourl('<%=path %>/e/op/auth/act.do?companyId=${companyId}&actId='+respValue);
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>