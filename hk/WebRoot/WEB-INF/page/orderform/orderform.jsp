<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="订单编号：${order.oid }" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">订单编号：${order.oid }</div>
	<div class="hang odd">
	订单状态:<hk:data key="orderform.orderstatus${order.orderStatus}"/><br/>
	联系人：<br/>
	${order.name }<br/>
	<c:if test="${not empty order.mobile}">手机号码：<br/>${order.mobile }<br/></c:if>
	<c:if test="${not empty order.tel}">联系电话：<br/>${order.tel }<br/></c:if>
	预订时间：<br/><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"/><br/>
	创建时间：<br/><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/>
	</div>
	<div class="hang">订单商品明细</div>
	<c:forEach var="item" items="${itemlist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/product_wap.do?pid=${item.productId}">${item.name }</hk:a> 
			${item.pcount }份<br/>
			价格：￥${item.price*item.pcount } 
			结算价：￥${item.totalPrice }
		</div>
	</c:forEach>
	<fmt:formatNumber var="l_price" value="${order.price}" pattern="####.#"></fmt:formatNumber>
	<div class="hang">合计：￥${l_price }</div>
	<c:if test="${!order.canceled}">
		<div class="hang">
			<hk:form action="/op/orderform_cancelwap.do?oid=${oid}">
				<c:if test="${order.unChecked}"><hk:submit value="取消订单"/></c:if>
				<c:if test="${order.checkOk}"><hk:submit value="申请取消"/></c:if>
			</hk:form>
		</div>
	</c:if>
	<div class="hang">
	<hk:a href="/op/orderform_wap.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>