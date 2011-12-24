<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.OrderForm"%>
<%@page import="com.hk.bean.OrderForm"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.orderform"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<hk:form method="get" action="/e/op/orderform.do">
	<hk:hide name="companyId" value="${companyId}"/>
	订单编号：
	<hk:text name="oid" clazz="text_short_2" value="${oid}"/>
	查看类型：
	<hk:select name="orderStatus" checkedvalue="${orderStatus}">
	<hk:option value="-1" data="所有订单"/>
	<hk:option value="<%=OrderForm.ORDERSTATUS_UNCHECK %>" data="orderform.orderstatus0" res="true"/>
	<hk:option value="<%=OrderForm.ORDERSTATUS_CHECKEOK %>" data="orderform.orderstatus1" res="true"/>
	<hk:option value="<%=OrderForm.ORDERSTATUS_CHECKOUTOK %>" data="orderform.orderstatus2" res="true"/>
	<hk:option value="<%=OrderForm.ORDERSTATUS_CANCEL %>" data="orderform.orderstatus3" res="true"/>
	<hk:option value="<%=OrderForm.ORDERSTATUS_NEEDCANCEL %>" data="orderform.orderstatus4" res="true"/>
	</hk:select>
	<hk:submit value="搜索" clazz="btn"/>
</hk:form>
<br/>
</div>
<div>
<c:if test="${fn:length(orderformvolist)==0}">
<strong>没有查询到数据</strong>
</c:if>
<c:if test="${fn:length(orderformvolist)>0}">
	<strong>选中编号可以合并订单</strong><br/>
	<a class="split-r" href="javascript:clearallselorder()">清除所有选中</a> <hk:button value="结算合并订单" clazz="btn2" onclick="toaccountorders()"/>
	<ul class="orderlist">
		<c:forEach var="vo" items="${orderformvolist}">
		<fmt:formatNumber var="l_price" value="${vo.orderForm.price}" pattern="####.#"></fmt:formatNumber>
		<li>
			<div class="orderrow-1">
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr>
						<td width="300px">
							<input <c:if test="${vo.checked }">checked="checked"</c:if> onclick="optorder(this,${vo.orderForm.oid })" type="checkbox" name="cols"/> <span class="bold">编号：</span>${vo.orderForm.oid}
						</td>
						<td width="300px">
							<span class="bold">预订时间：</span><fmt:formatDate value="${vo.orderForm.orderTime}" pattern="yyyy-MM-dd HH:mm"/>
						</td>
					</tr>
				</table>
			</div>
			<div>
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px">
							<c:if test="${vo.orderForm.tableId>0}">桌号：${vo.orderForm.tableNum }</c:if>
						</td>
						<td width="200px">
							<a href="<%=path %>/home_web.do?userId=${order.userId}" target="_blank">${vo.orderForm.name }</a><br/>
							${vo.orderForm.mobile }<br/>
						</td>
						<td width="100px">
						${vo.orderForm.totalCount }个<br/>
						<a href="<%=path %>/e/op/orderform_findproduct.do?companyId=${companyId}&oid=${vo.orderForm.oid }&return_url=${return_url}">加菜</a>
						</td>
						<td width="100px">￥${l_price }</td>
						<td width="100px">
							<c:set var="orderform"><%=path %>/e/op/orderform_view.do?oid=${vo.orderForm.oid}&companyId=${companyId}&return_url=${return_url}</c:set>
							<a href="${orderform }">查看订单</a>
							<c:if test="${vo.orderForm.canceled}">
								<div id="unckeckact${vo.orderForm.oid }"><a href="javascript:unckecklOrder(${vo.orderForm.oid })">未审核</a></div>
							</c:if>
							<c:if test="${!vo.orderForm.canceled}">
								<c:if test="${vo.orderForm.unChecked}">
									<div id="checkok${vo.orderForm.oid }"><a href="javascript:checkokOrder(${vo.orderForm.oid })">审核通过</a></div>
								</c:if>
								<c:if test="${!vo.orderForm.checkoutOk}">
									<div id="checkoutokt${vo.orderForm.oid }">
										<a href="${orderform }">结算</a>
									</div>
								</c:if>
								<c:if test="${!vo.orderForm.checkoutOk}">
									<div id="cancel${vo.orderForm.oid }"><a href="javascript:cancelOrder(${vo.orderForm.oid })">取消订单</a></div>
								</c:if>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</li>
		</c:forEach>
	</ul>
	<a class="split-r" href="javascript:clearallselorder()">清除所有选中</a> <hk:button value="结算合并订单" clazz="btn2" onclick="toaccountorders()"/>
	<div>
		<hk:page midcount="10" url="/op/orderform.do&keyst=${keyst}&key=${enc_key}&orderStatus=${orderStatus}"/>
		<div class="clr"></div>
	</div>
</c:if>
</div>
<script type="text/javascript">
function clearallselorder(){
	var objs=getObjsByName('cols');
	for(var i=0;i<objs.length;i++){
		if(objs[i].checked==true){
			objs[i].checked=false;
		}
	}
	var url="<%=path%>/e/op/orderform_clearmerge.do?companyId=${companyId}";
	$.ajax({
		type:"POST",
		url:url,
		cache:false,
    	dataType:"html",
		error:function(data){
			alert('合并订单出错，请重新操作');
		}
	});
}
function toaccountorders(){
	tourl("<%=path %>/e/op/orderform_toaccountorders.do?companyId=${companyId}");
}
function optorder(obj,id){
	var	url="<%=path%>/e/op/orderform_delorderformerge.do?companyId=${companyId}&oid="+id;
	if(obj.checked==true){
		url="<%=path%>/e/op/orderform_addorderformerge.do?companyId=${companyId}&oid="+id;
	}
	$.ajax({
		type:"POST",
		url:url,
		cache:false,
    	dataType:"html",
		error:function(data){
			alert('合并订单出错，请重新操作');
		},
		success:function(data){
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