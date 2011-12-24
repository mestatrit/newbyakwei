<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%>
<%@page import="com.hk.bean.Coupon"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/cmpwebst4/js/date/datePicker.css">
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="couponId" value="${couponId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<c:if test="${not empty coupon.picpath}">
			<tr>
				<td width="160px" align="right">
					已选图片：
				</td>
				<td>
					<a href="<%=path %>/epp/web/op/webadmin/coupon_selcouponpic.do?companyId=${companyId}&couponId=${couponId}&navoid=${navoid }">更新图片</a><br/>
					<img src="${coupon.h_2Pic }"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<td width="160px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${coupon.name}"/> <br/>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				地区：
			</td>
			<td>
				<div style="margin-bottom: 10px">
				<jsp:include page="../inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${coupon.cityId});
				</script>
				</div>
				<div>（不选择默认为全国发布）</div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				内容：
			</td>
			<td>
				<hk:textarea name="content" clazz="text" value="${coupon.content}" style="width:270px;height:100px;"/><br/>
				<div class="infowarn" id="_content"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				总数量：
			</td>
			<td>
				<hk:text name="amount" clazz="text" value="${coupon.amount}"/><br/>
				<div class="infowarn" id="_amount"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				期限：
			</td>
			<td>
				<hk:radioarea name="overdueflg" checkedvalue="${coupon.overdueflg}" forcecheckedvalue="0">
					<hk:radio oid="l_1" value="<%=Coupon.OVERDUEFLG_DAY %>"/>
					<label for="l_1">自下载之日计算，多少天后</label><br/>
					<hk:text name="limitDay" value="${coupon.limitDay}" maxlength="10"/><br/>
					<hk:radio oid="l_2" value="<%=Coupon.OVERDUEFLG_TIME %>"/>
					<label for="l_2">本批优惠券为固定日期</label><br/>
					<div style="padding-left: 20px">
						<fmt:formatDate var="endTime" value="${coupon.endTime}" pattern="yyyy-MM-dd"/>
						<hk:text name="endTime" value="${endTime}" clazz="text2 date-pick1" style="width:100px"/>
					</div>
				</hk:radioarea>
				<div class="infowarn" id="_limitday"></div>
				<div class="infowarn" id="_endtime"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				备注：
			</td>
			<td>
				<hk:textarea name="remark" clazz="text" value="${coupon.remark}" style="width:270px;height:100px;"/><br/>
				<div class="infowarn" id="_remark"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/coupon.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.COUPON_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.COUPON_CONTENT_LENGTH_TOO_LONG %>={objid:"_content"};
var err_code_<%=Err.COUPON_REMARK_LENGTH_TOO_LONG %>={objid:"_remark"};
var err_code_<%=Err.COUPON_LIMITDAY_ERROR %>={objid:"_limitday"};
var err_code_<%=Err.COUPON_ENDTIME_ERROR %>={objid:"_endtime"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_content','');
	setHtml('_remark','');
	setHtml('_limitday','');
	setHtml('_endtime','');
	showGlass(frmid);
	return true;
}
</script>