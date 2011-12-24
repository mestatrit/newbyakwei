<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.box.create" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<c:if test="${company!=null}">
			<h2 class="line">${company.name }</h2>
		</c:if>
		<c:set var="box_form_action" scope="request"><%=path %>/box/op/op_addweb.do</c:set>
			<jsp:include page="boxform.jsp"></jsp:include>
			<jsp:include page="boxformscript.jsp"></jsp:include>
	</div>
<script type="text/javascript">
var prize=new Array();
function subboxfrm(frmid){
	if(prize.length==0){
		alert("添加开箱物品");
		return false;
	}
	validateClear('box_name');
	validateClear('box_totalCount');
	validateClear('box_beginTime');
	validateClear('box_endTime');
	validateClear('box_boxKey');
	validateClear('box_intro');
	validateClear('box_precount');
	var s="";
	for(var i=0;i<prize.length;i++){
		s+='<input type="hidden" name="pname" value="'+prize[i][0]+'"/>';
		s+='<input type="hidden" name="pcount" value="'+prize[i][1]+'"/>';
		s+='<input type="hidden" name="ptip" value="'+prize[i][2]+'"/>';
	}
	setHtml("hidev",s);
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!=0){
		if(parseInt()==236){
			alert('没有足够的酷币发布宝箱');
			return;
		}
		validateErr(obj_id_param,error_msg);
		hideSubmitDiv();
	}
	else{
		tourl("<%=path %>/box/op/op_my.do");
	}
}
function createprizewin(){
	createBg();
	createCenterWindow('prize_win',500,300,'设置物品','<div><form method="post" onsubmit="return false"><table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">物品名称</td> <td> <div class="f_l"> <input type="text" id="pname" class="text" maxlength="20"/><br/> <div class="error" id="pname_error"></div> </div> <div id="pname_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="90px">物品提示语</td> <td> <div class="f_l"> <input type="text" id="ptip" class="text" maxlength="50"/><br/> <div class="error" id="ptip_error"></div> </div> <div id="ptip_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="90px">物品数量</td> <td> <div class="f_l"> <input type="text" id="pcount" class="text" maxlength="10"/><br/> <div class="error" id="pcount_error"></div> </div> <div id="pcount_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div class="form_btn"><hk:submit onclick="addprize()" value="保存" clazz="btn"/></div> </td> </tr></table></form></div>',"hideWindow('prize_win');clearBg();");
}
function addprize(){
	if(validateprize()){
		prize[prize.length]=new Array(getObj("pname").value,getObj("pcount").value,getObj("ptip").value);
		showprize();
		hideWindow('prize_win');
		clearBg();
	}
}
function validateprize(){
	validateClear('pname');
	validateClear('ptip');
	validateClear('pcount');
	if(getObj('pname').value.length>15 || getObj('pname').value.length==0){
		validateErr('pname','名称不能为空或多于15字');
		return false;
	}
	if(getObj('ptip').value.length>15 || getObj('ptip').value.length==0){
		validateErr('ptip','物品提示语不能为空或多于50字');
		return false;
	}
	if(!isNumber(getObj('pcount').value)){
		validateErr('pcount','数量不是有效数字');
		return false;
	}
	return true;
}
function showprize(){
	var s="";
	for(var i=0;i<prize.length;i++){
		s+='<div id="prize_'+i+'"><span class="split2">'+prize[i][0]+'</span><span class="split2">'+prize[i][1]+'</span><span class="split2">'+prize[i][2]+'</span><span class="split2"><a href="javascript:delprize('+i+')">删除</a></span><span class="split2"><a href="javascript:toeditprize('+i+')">修改</a></span></div>';
	}
	setHtml('prize',s);
}
function delprize(idx){
	prize=prize.del(idx);
	showprize();
}
function editprize(idx){
	if(validateprize()){
		prize[idx]=new Array(getObj("pname").value,getObj("pcount").value,getObj("ptip").value);
		showprize();
		hideWindow('prize_win');
		clearBg();
	}
}
function toeditprize(idx){
	createBg();
	createCenterWindow('prize_win',500,300,'设置物品','<div><form method="post" onsubmit="return false"><table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="90px">物品名称</td> <td> <div class="f_l"> <input type="text" id="pname" class="text" maxlength="20"/><br/> <div class="error" id="pname_error"></div> </div> <div id="pname_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="90px">物品提示语</td> <td> <div class="f_l"> <input type="text" id="ptip" class="text" maxlength="50"/><br/> <div class="error" id="ptip_error"></div> </div> <div id="ptip_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="90px">物品数量</td> <td> <div class="f_l"> <input type="text" id="pcount" class="text" maxlength="10"/><br/> <div class="error" id="pcount_error"></div> </div> <div id="pcount_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div class="form_btn"><hk:submit onclick="editprize('+idx+')" value="保存" clazz="btn"/></div> </td> </tr></table></form></div>',"hideWindow('prize_win');clearBg();");
	getObj('pname').value=prize[idx][0];
	getObj('pcount').value=prize[idx][1];
	getObj('ptip').value=prize[idx][2];
}
$(function()
	{
		$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString()).trigger('change');
		$('.date-pick2').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString()).trigger('change');
	});
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>