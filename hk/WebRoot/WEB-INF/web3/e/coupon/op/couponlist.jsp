<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.bean.Coupon"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">优惠券管理</c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css"></c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<div>
<a class="text_14" href="javascript:toadd()">创建优惠券</a>
<table width="500px" class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<hk:form method="get" action="/e/op/auth/coupon.do">
				<hk:hide name="companyId" value="${companyId}"/>
				名称:<hk:text name="name" value="${name}" clazz="text_short_1"/>
				<hk:submit value="查询" clazz="btn"/>
			</hk:form><br/>
		</td>
	</tr>
</table>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr class="tr-title">
		<th width="200px">名称</th>
		<th width="80px">数量</th>
		<th width="80px">下载数量</th>
		<th width="100px">状态</th>
		<th width="150px"></th>
	</tr>
	<c:forEach var="c" items="${list}">
	<tr class="tr-line" onmouseout="this.className='tr-line';" onmouseover="this.className='tr-line bg2';">
		<td>${c.name }</td>
		<td>${c.amount }</td>
		<td>${c.dcount }</td>
		<td><hk:data key="view.company.coupon.useflg${c.useflg}"/></td>
		<td>
			<a id="edit_${c.couponId }" href="javascript:toedit(${c.couponId })">修改</a>
			/
			<c:if test="${c.available}">
				<a id="use_${c.couponId }" href="javascript:setunavailable(${c.couponId })">作废</a>
			</c:if>
			<c:if test="${!c.available}">
				<a id="use_${c.couponId }" href="javascript:setavailable(${c.couponId })">启用</a>
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<div class="pagecon">
	<hk:page midcount="10" url="/e/op/auth/coupon.do?companyId=${companyId}&name=${enc_name }"/>
	<div class="clr"></div>
</div>
</div>
<script type="text/javascript">
var err_code_<%=Err.COUPON_NAME_ERROR %>={objid:"name"};
var err_code_<%=Err.COUPON_CONTENT_LENGTH_TOO_LONG %>={objid:"content"};
var err_code_<%=Err.COUPON_REMARK_LENGTH_TOO_LONG %>={objid:"remark"};
var err_code_<%=Err.COUPON_LIMITDAY_ERROR %>={objid:"time"};
var err_code_<%=Err.COUPON_ENDTIME_ERROR %>={objid:"time"};
function setunavailable(id){
	if(window.confirm("确实要作废？")){
		showSubmitDivForObj('use_'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/coupon_setunavailable.do',
			data:'couponId='+id+"&companyId=${companyId}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function setavailable(id){
	showSubmitDivForObj('use_'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/coupon_setavailable.do',
		data:'couponId='+id+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function toadd(id){
	var html='<hk:form oid="coupon_frm" enctype="multipart/form-data" onsubmit="return subaddfrm(this.id)" action="/e/op/auth/coupon_create.do" target="hideframe"> <hk:hide name="companyId" value="${companyId}"/> <table class="infotable" cellpadding="0" cellspacing="0" width="600px"> <tr> <td width="100px">名称</td> <td> <hk:text name="name" clazz="text"/> <div class="error" id="name_error"></div> </td> </tr> <tr> <td>数量</td> <td> <hk:text name="amount" clazz="text"/> <div class="error" id="amount_error"></div> </td> </tr> <tr> <td>内容</td> <td> <textarea name="content" class="text_area"></textarea> <div class="error" id="content_error"></div> </td> </tr> <tr> <td>期限</td> <td> <div> <div class="pad"> <input type="radio" id="overdueflg_0" name="overdueflg" value="<%=Coupon.OVERDUEFLG_DAY %>"/> 自下载之日计算，<hk:text name="limitDay" clazz="text_short_1"/>天后过期<br/> </div> <div class="pad"> <input type="radio" id="overdueflg_1" name="overdueflg" value="<%=Coupon.OVERDUEFLG_TIME %>"/> 本批优惠券到期时间为：<hk:text name="time" clazz="text_short_1 date-pick1" /> </div> </div> <div class="error" id="time_error"></div> </td> </tr> <tr> <td>图片</td> <td> <hk:file name="f"/> </td> </tr> <tr> <td>备注</td> <td> <textarea name="remark" class="text_area"></textarea> <div class="error" id="remark_error"></div> </td> </tr> <tr> <td></td> <td> <div align="center"><hk:submit value="提交" clazz="btn"/></div> </td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow("add_win",500,650,'创建优惠券',html,"hideWindow('add_win');clearBg();");
	$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:'2009-01-01'});
}
function subaddfrm(frmid){
	validateClear("name");
	validateClear("time");
	validateClear("amount");
	validateClear("content");
	validateClear("remark");
	showSubmitDiv(frmid);
	return true;
}
function toedit(id){
	showSubmitDivForObj('edit_'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/coupon_loadcoupon.do',
		data:'couponId='+id+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("edit_win",500,650,'修改优惠券',data,"hideWindow('edit_win');clearBg();");
			$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:'2009-01-01'});
			hideSubmitDiv();
		}
	})
}
function subeditfrm(frmid){
	validateClear("name");
	validateClear("time");
	validateClear("amount");
	validateClear("content");
	validateClear("remark");
	showSubmitDiv(frmid);
	return true;
}
function couponerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function couponok(error,error_msg,respValue){
	refreshurl();
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>