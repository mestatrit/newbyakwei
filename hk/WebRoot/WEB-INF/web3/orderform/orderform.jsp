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
				手机号码：${order.mobile }<br/>
				联系电话：${order.tel }
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
				<td>${l_rebate*100 }%</td>
				<td>￥${item.totalPrice }</td>
				<td></td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td colspan="3">
				<div>
				<fmt:formatNumber var="l_price" value="${order.price}" pattern="####.#"></fmt:formatNumber>
				合计：<strong>￥${l_price }</strong></div>
			</td>
		</tr>
	</table>
</div>
<div class="pad text_16">
<hk:button value="返回" clazz="btn" onclick="goback()"/>
</div>
<script type="text/javascript">
function goback(){
	tourl("${denc_return_url}");
}
</script>
</c:set>
<jsp:include page="../inc/usermgr_inc.jsp"></jsp:include>