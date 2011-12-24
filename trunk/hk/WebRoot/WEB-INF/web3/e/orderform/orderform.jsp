<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.OrderForm"%>
<%@page import="com.hk.bean.OrderForm"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">订单${order.oid}</c:set>
<c:set var="mgr_content" scope="request">
<style>
.pad{
padding: 5px 0px;
}
</style>
<div class="line pad text_16">
	<span class="h3s">订单信息</span><br/>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				订单编号：${order.oid}
			</td>
			<td>
				订单状态：<hk:data key="orderform.orderstatus${order.orderStatus}"/>
			</td>
		</tr>
		<tr>
			<td width="300px">
				会员：<a href="<%=path %>/home_web.do?userId=${order.userId}" target="_blank">${order.name }</a>
			</td>
			<td width="300px">
				手机号码：${order.mobile }
			</td>
		</tr>
		<tr>
			<td>
				预订时间：<fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
			<td>
				创建时间：<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
	</table>
</div>
<c:if test="${not empty order.content}">
	<div class="line pad text_16">
		<span class="h3s">留言</span><br/>
		${order.content }
	</div>
</c:if>
<div class="line pad text_16">
	<span class="h3s">订单商品明细</span><br/>
	<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
		<tr class="tr-title">
			<th width="200px">名称</th>
			<th width="100px">数量</th>
			<th width="100px">价格</th>
			<th width="100px">优惠</th>
			<th width="100px">结算价</th>
			<th width="100px"></th>
		</tr>
		<c:forEach var="item" items="${itemlist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<fmt:formatNumber var="l_rebate" value="${item.rebate}" pattern="####.#"></fmt:formatNumber>
			<input type="hidden" id="hiderebate${item.itemId }" value="${l_rebate*100 }"/>
			<tr class="tr-line ${clazz_var }">
				<td><a target="_blank" href="<%=path %>/product.do?pid=${item.productId}">
				<img src="${item.cmpProduct.head60 }" class="small"/> ${item.name }</a></td>
				<td>${item.pcount }</td>
				<td>￥${item.price*item.pcount }</td>
				<td><input id="rebate${item.itemId }" onblur="changeprice(${item.itemId },this.value)" type="text" value="${l_rebate*100 }" class="text_short_4"/>%</td>
				<td>￥<span id="totalPrice${item.itemId }">${item.totalPrice }</span></td>
				<td><span id="itemtip${item.itemId }" class="error"></span></td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td colspan="3">
				<div>
					<hk:form target="hideframe" oid="rebatefrm" onsubmit="return subrebatefrm(this.id)" action="/e/op/orderform_updaterebate.do">
						<hk:hide name="companyId" value="${companyId}"/>
						<hk:hide name="oid" value="${oid}"/>
						<fmt:formatNumber var="l_price" value="${rebate}" pattern="####.#"></fmt:formatNumber>
						优惠：<input name="rebate" type="text" value="${l_price }" class="text_short_4"/>%
						<hk:submit value="修改整体优惠" clazz="btn2"/>
					</hk:form>
				</div>
				<div>
				<fmt:formatNumber var="l_price" value="${order.price}" pattern="####.#"></fmt:formatNumber>
				合计：<strong>￥<span id="totalPrice">${l_price }</span></strong></div>
			</td>
		</tr>
	</table>
</div>
<div class="pad text_16">
<c:if test="${order.canceled}">
	<span id="unckeckact${order.oid }" class="split-r">
	<hk:button value="未审核" clazz="btn" onclick="unckecklOrder(${order.oid })"/>
	</span>
</c:if>
<c:if test="${!order.canceled}">
	<c:if test="${order.unChecked}">
		<span id="checkok${order.oid }" class="split-r">
		<hk:button value="审核通过" clazz="btn" onclick="checkokOrder(${order.oid })"/>
		</span>
	</c:if>
	<c:if test="${!order.checkoutOk}">
		<span id="checkoutok${order.oid }" class="split-r">
		<hk:button value="结算" clazz="btn" onclick="checkoutokOrder(${order.oid })"/>
		</span>
	</c:if>
	<c:if test="${!order.checkoutOk}">
		<span id="cancel${order.oid }" class="split-r">
		<hk:button value="取消订单" clazz="btn" onclick="cancelOrder(${order.oid })"/>
		</span>
	</c:if>
</c:if>
<span class="split-r">
	<hk:button value="返回" clazz="btn" onclick="goback()"/>
</span>
</div>
<script type="text/javascript">
function goback(){
	tourl("${denc_return_url}");
}
function changeprice(itemId,v){
	if(parseFloat(getObj('hiderebate'+itemId).value)!=parseFloat(v)){
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/orderform_updateitemrebate.do?companyId=${companyId}&itemId='+itemId+"&rebate="+v,
			cache:false,
	    	dataType:"html",
			success:function(data){
				var s=data.split(";");
				setHtml('totalPrice'+itemId,s[0]);
				setHtml('totalPrice',s[1]);
				setHtml('itemtip'+itemId,'更新成功');
				getObj('hiderebate'+itemId).value=v;
				delay("clearItemTip("+itemId+")",2000);
			}
		});
	}
}
function changetotalprice(itemId,v){
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_cancel.do?companyId=${companyId}&oid='+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			var s=data.split(";");
			setHtml('totalPrice'+itemId,s[0]);
			setHtml('totalPrice',s[1]);
		}
	});
}
function clearItemTip(itemId){
	setHtml('itemtip'+itemId,'');
}
function cancelOrder(oid){
	if(window.confirm("确认取消这个订单？")){
		setHtml('cancel'+oid,"操作中 ...");
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/orderform_cancel.do?companyId=${companyId}&oid='+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function checkokOrder(oid){
	setHtml('checkok'+oid,"操作中 ...");
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_checkok.do?companyId=${companyId}&oid='+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function checkoutokOrder(oid){
	if(window.confirm("确认要结算？")){
		setHtml('checkoutok'+oid,"操作中 ...");
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/orderform_checkoutok.do?companyId=${companyId}&oid='+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function uncheckOrder(oid){
	setHtml('uncheck'+oid,"操作中 ...");
	if(window.confirm("确认要设置为未审核？")){
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/orderform_unckeck.do?companyId=${companyId}&oid='+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function subrebatefrm(frmid){
	showSubmitDivForObj(frmid);
	return true;
}
function onupdaterebatesuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl('<%=path%>/e/op/orderform_view.do?oid=${oid}&companyId=${companyId}&rebate='+respValue+"&return_url=${return_url}");
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>