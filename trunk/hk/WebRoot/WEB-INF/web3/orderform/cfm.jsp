<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.hk.bean.OrderForm"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); 
%>
<c:set var="html_title" scope="request"><hk:data key="view.orderform.confirm" /></c:set>
<c:set var="mgr_content" scope="request">
<style>
.pad{
padding: 5px 0px;
}
</style>

<div class="line">
<c:if test="${fn:length(infolist)==0}">
<span class="h3s">个人信息</span><br/>
<div id="userinfo">
<hk:form oid="userinfofrm" action="/op/orderform_createuserinfo.do" target="hideframe" onsubmit="return subuserinfofrm(this.id)">
<table cellpadding="0" cellspacing="0" class="infotable">
	<tr>
		<td>联系人：</td>
		<td><hk:text name="name" clazz="text" maxlength="20"/></td>
	</tr>
	<tr>
		<td>手机号码：</td>
		<td><hk:text name="mobile" clazz="text" maxlength="15"/></td>
	</tr>
	<tr>
		<td>座机：</td>
		<td><hk:text name="tel" clazz="text" maxlength="15"/></td>
	</tr>
	<tr>
		<td>E-mail：</td>
		<td><hk:text name="email" clazz="text" maxlength="15"/></td>
	</tr>
	<tr>
		<td></td>
		<td><hk:submit value="保存个人信息" clazz="btn"/></td>
	</tr>
	<tr>
		<td></td>
		<td><div class="error" id="userinfo_msg_error"></div></td>
	</tr>
</table>
</hk:form>
</div>
</c:if>
<c:if test="${fn:length(infolist)>0}">
<span class="h3s">个人信息</span> <a id="userinfoact" href="javascript:toedituserinfo()">修改</a> <span id="userinfo_tip"></span> <br/>
<div id="userinfo">
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td>联系人：</td>
			<td>${maininfo.name }</td>
		</tr>
		<tr>
			<td>手机号码：</td>
			<td>${maininfo.mobile }</td>
		</tr>
	</table>
</div>
</c:if>
</div>
<div class="line pad">
<span class="heavy">购物车信息</span> <a href="<%=path %>/shoppingcard.do">返回修改购物车</a>
<c:if test="${fn:length(productvolist)>0}">
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr class="tr-title">
			<th width="260px">名称</th>
			<th width="90px">数量</th>
			<th width="90px">单价</th>
			<th width="90px">小计</th>
		</tr>
		<c:forEach var="vo" items="${productvolist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<tr class="${clazz_var }">
			<td>
			<a href="<%=path %>/product.do?pid=${vo.cmpProduct.productId}" target="_blank">
			<img class="middle" src="${vo.cmpProduct.head60 }" width="32" height="32"/>
			${vo.cmpProduct.name }</a></td>
			<td align="center">${vo.count }</td>
			<td align="center">￥${vo.cmpProduct.money }</td>
			<td align="center">￥<span id="product_total_price${vo.cmpProduct.productId }">${vo.cmpProduct.money*vo.count }</span></td>
		</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${fn:length(productvolist)==0}">
<div class="text_16"><strong><hk:data key="view.shoppingcard.empty"/></strong>
<a href="#">继续购物</a>
</div>
</c:if>
</div>
<hk:form oid="orderformfrm" onsubmit="return suborderform(this.id)" action="/op/orderform_create.do" target="hideframe">
<div class="line pad">
	<span class="h3s">预定时间</span>
	<a href="javascript:seldate()">选择日期</a> &nbsp;&nbsp;<a href="javascript:inputdate()">自行输入时间</a><br/>
	日期：<input type="text" id="date-pick1" name="date" class="text_short_2"/> 
	<span id="seldate">
		时间：<hk:select name="time1">
				<hk:option value="0" data=""/>
				<c:forEach var="t" items="${timelist}">
				<hk:option value="${t}" data="${t}"/>
				</c:forEach>
			</hk:select>
	</span>
	<span id="inputdate" style="display: none">
		自行输入时间：<input type="text" id="id_time2" name="time2" class="text_short_2"/> 时间格式为HH:mm
	</span>
</div>
<div class="line pad">
	<span class="h3s">给店家留言</span><span  class="ruo">(最多150字)</span> <br/>
	<hk:textarea name="content" style="width:700px;height:60px"/>
</div>
<div class="line">
<table cellpadding="0" cellspacing="0" class="infotable">
<tr><td align="right" width="620px"> <strong>金额总计： ￥<span id="totalPrice">${totalPrice }</span></strong></td></tr>
<tr><td>
		<div class="f_l text_16">
		</div>
		<div id="orderformdiv" class="f_r">
			<input id="info_oid" type="hidden" name="info_oid" value="${maininfo.oid }"/>
			<hk:submit value="确认无误，提交订单" clazz="btnlong"/>
		</div>
		<div class="clr"></div>
		</td></tr>
</table>
</div>
</hk:form>
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css">
<script type="text/javascript">
function suborderform(frmid){
	showSubmitDiv(frmid);
	return true;
}
function oncreateordererror(error,error_msg,op_func,obj_id_param,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
function oncreateordersuccess(error,error_msg,op_func,obj_id_param,respValue){
	var s=respValue.split(":");
	tourl("<%=path%>/op/orderform_createok.do?oid="+s[0]+"&companyId="+s[1]);
}
function resetuserinfoact(){
	if(getObj('userinfoact')!=null){
		getObj('userinfoact').href="javascript:toedituserinfo()";
		setHtml('userinfoact','修改');
	}
	else{
	}
}
function canceledituserinfo(){
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/orderform_loadmainuserinfo.do',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('userinfo',data);
			setHtml('userinfo_tip','');
			getObj('userinfoact').href="javascript:toedituserinfo()";
			setHtml('userinfoact','修改');
		}
	});
}
function toedituserinfo(){
	 setHtml('userinfo_tip','正在加载数据 ... ...');
	 $.ajax({
		type:"POST",
		url:'<%=path %>/op/orderform_loaduserinfo.do?needmain=1',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('userinfo',data);
			setHtml('userinfo_tip','');
			getObj('userinfoact').href="javascript:canceledituserinfo()";
			setHtml('userinfoact','取消修改');
		}
	});
}
function subuserinfofrm(frmid){
	setHtml('userinfo_msg_error','');
	showSubmitDiv(frmid);
	return true;
}
function onuserinfoerror(error,error_msg,op_func,obj_id_param,respValue){
	setHtml('userinfo_msg_error',error_msg);
	hideSubmitDiv();
}
function updateuserinfo(html){
	setHtml('userinfo',html);
	hideSubmitDiv();
}
function tocreateuserinfo(){
	setHtml('userinfo','<hk:form oid="userinfofrm" action="/op/orderform_createuserinfo.do" target="hideframe" onsubmit="return subuserinfofrm(this.id)"> <table cellpadding="0" cellspacing="0" class="infotable"> <tr> <td>联系人：</td> <td><hk:text name="name" clazz="text" maxlength="20"/></td> </tr> <tr> <td>手机号码：</td> <td><hk:text name="mobile" clazz="text" maxlength="15"/></td> </tr> <tr> <td></td> <td><hk:submit value="保存个人信息" clazz="btn"/></td> </tr> <tr> <td></td> <td><div class="error" id="userinfo_msg_error"></div></td> </tr> </table> </hk:form>');
}
function setinfo_oid(id){
	getObj('info_oid').value=id;
}
function changeuserinfo(id){
	setHtml('userinfo2_tip','数据加载中 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/orderform_loaduserinfo2.do?oid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('userinfo2',data);
			setHtml('userinfo2_tip','');
		}
	});
}
function seldate(){
	getObj("seldate").style.cssText="display:inline";
	getObj("inputdate").style.cssText="display:none";
	getObj("id_time2").value="";
}
function inputdate(){
	getObj("inputdate").style.cssText="display:inline";
	getObj("seldate").style.cssText="display:none";
}
$(function()
{
	$('#date-pick1').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString()).trigger('change');
});
</script>
</c:set>
<jsp:include page="../inc/usermgr_inc.jsp"></jsp:include>