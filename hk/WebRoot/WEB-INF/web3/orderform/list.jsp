<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.OrderForm"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.order" /></c:set>
<c:set var="mgr_content" scope="request">
<div>
<hk:form method="get" action="/op/orderform.do">
订单编号：
<hk:text name="key" clazz="text_short_2" value="${key}"/> 
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
<ul class="orderlist">
	<c:forEach var="order" items="${orderformlist}">
	<fmt:formatNumber var="l_price" value="${order.price}" pattern="####.#"></fmt:formatNumber>
		<li>
			<div class="orderrow-1">
				<table class="infotable cellinfo" cellpadding="0" cellspacing="0">
					<tr>
						<td width="300px">
							<input type="checkbox" name="cols"/> <span class="bold">编号：</span>${order.oid}
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
							<c:if test="${order.company.parentKindId==1}">
								<c:if test="${order.tableId>0}">
									桌号：${order.tableNum }<br/>
								</c:if>
								<c:if test="${!order.checkOk && !order.checkoutOk}">
									<c:if test="${order.tableId==0}">
										<a href="<%=path %>/op/orderform_tablelist.do?oid=${order.oid }&companyId=${order.companyId}">选择餐桌</a>
									</c:if>
								</c:if>
							</c:if>
						</td>
						<td width="200px">
							<a href="<%=path %>/home_web.do?userId=${order.userId}" target="_blank">${order.name }</a><br/>
							${order.mobile }<br/>
						</td>
						<td width="100px">
						${order.totalCount }个
						</td>
						<td width="100px">￥${l_price }</td>
						<td width="100px">
							<c:set var="orderform"><%=path %>/op/orderform_view.do?oid=${order.oid}&return_url=${return_url}</c:set>
							<a href="${orderform }">查看订单</a>
							<c:if test="${!order.canceled}">
								<c:if test="${order.unChecked}">
									<div id="order_act${order.oid }"><a href="javascript:cancelOrder(${order.oid })">取消订单</a></div>
								</c:if>
								<c:if test="${order.checkOk}">
									<div id="order_act${order.oid }"><a href="javascript:cancelOrder(${order.oid })">申请取消</a></div>
								</c:if>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</li>
	</c:forEach>
</ul>
</div>
<div>
<hk:page midcount="10" url="/op/orderform.do&keyst=${keyst}&key=${enc_key}&orderStatus=${orderStatus}"/>
<div class="clr"></div>
</div>
<script type="text/javascript">
function cancelOrder(oid){
	if(window.confirm("确认取消这个订单？")){
		setHtml('order_act'+oid,"操作中 ...");
		$.ajax({
			type:"POST",
			url:'<%=path %>/op/orderform_cancel.do?oid='+oid,
			cache:false,
	    	dataType:"html",
			success:function(data){
				if(data=="1"){
					delObj('order_act'+oid);
					getObj('order'+oid).className="canceled";
				}
				else if(data=="2"){
					delObj('order_act'+oid);
					getObj('order'+oid).className="reqcancel";
				}
			}
		});
	}
}
</script>
</c:set>
<jsp:include page="../inc/usermgr_inc.jsp"></jsp:include>