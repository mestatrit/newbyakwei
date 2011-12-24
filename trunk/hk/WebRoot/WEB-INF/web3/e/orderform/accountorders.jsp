<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.OrderForm"%>
<%@page import="com.hk.bean.OrderForm"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.orderform"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
	<ul class="orderlist">
		<c:forEach var="order" items="${orderformlist}">
		<fmt:formatNumber var="l_price" value="${order.price}" pattern="####.#"></fmt:formatNumber>
		<li>
			<div class="orderrow-1">
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr>
						<td width="300px">
							<span class="bold">编号：</span>${order.oid}
						</td>
						<td width="300px">
							<span class="bold">预订时间：</span><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"/>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px">
							餐桌编号：${order.tableNum }
						</td>
						<td width="200px">
							<a href="<%=path %>/home_web.do?userId=${order.userId}" target="_blank">${order.name }</a><br/>
							${order.mobile }<br/>
						</td>
						<td width="100px">
						${order.totalCount }个<br/>
						</td>
						<td width="100px">￥${l_price }</td>
						<td width="100px">
							<div id="remove${order.oid }"><a href="javascript:removefromlist(${order.oid })">从列表中移除</a></div>
							<c:set var="orderform"><%=path %>/e/op/orderform_view.do?oid=${order.oid}&companyId=${companyId}&return_url=${return_url}</c:set>
							<a href="${orderform }">查看订单</a>
							<c:if test="${order.canceled}">
								<div id="unckeckact${order.oid }"><a href="javascript:unckecklOrder(${order.oid })">未审核</a></div>
							</c:if>
							<c:if test="${!order.canceled}">
								<c:if test="${order.unChecked}">
									<div id="checkok${order.oid }"><a href="javascript:checkokOrder(${order.oid })">审核通过</a></div>
								</c:if>
								<c:if test="${!order.checkoutOk}">
									<div id="checkoutokt${order.oid }">
										<a href="${orderform }">结算</a>
									</div>
								</c:if>
								<c:if test="${!order.checkoutOk}">
									<div id="cancel${order.oid }"><a href="javascript:cancelOrder(${order.oid })">取消订单</a></div>
								</c:if>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</li>
		</c:forEach>
		<li>
			<div class="orderrow-1">
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr><td>
					<hk:form oid="accountorderfrm" onsubmit="return subaccountorderfrm(this.id)" action="/e/op/orderform_checkoutok2.do" target="hideframe">
						合计：<strong>￥${totalResult }</strong>
						<hk:hide name="companyId" value="${companyId}"/>
						<c:forEach var="order" items="${orderformlist}">
							<hk:hide name="oid" value="${order.oid}"/>
						</c:forEach>
						<hk:submit value="结算" clazz="btn split-r"/>
						<hk:button value="返回" clazz="btn" onclick="toorderformlist()"/>
					</hk:form>
					</td>
					</tr>
				</table>
			</div>
		</li>
	</ul>
</div>
<div>
	<hk:page midcount="10" url="/op/orderform.do&keyst=${keyst}&key=${enc_key}&orderStatus=${orderStatus}"/>
	<div class="clr"></div>
</div>
<script type="text/javascript">
function toorderformlist(){
	tourl("<%=path%>/e/op/orderform.do?companyId=${companyId}");
}
function subaccountorderfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onaccountordersuccess(error,error_msg,respValue){
	tourl("<%=path%>/e/op/orderform.do?companyId=${companyId}");
}
function removefromlist(oid){
	showSubmitDivForObj("remove"+oid);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/orderform_removefromaccountorderlist.do?companyId=${companyId}&oid='+oid,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
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
	setHtml('checkoutok'+oid,"操作中 ...");
	if(window.confirm("确认要结算？")){
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
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>