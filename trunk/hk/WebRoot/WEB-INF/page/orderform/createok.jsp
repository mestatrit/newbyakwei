<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="订单生成" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">提交订单成功<br/>
	</div>
	<div class="hang odd">
		您的订单编号：	<br/>${orderForm.oid }<br/>
		应付金额：<br/>
		￥${orderForm.price }<br/>
		付款方式：<br/>
		<hk:data key="view.orderform.checkouttype${orderForm.checkoutflg}"/><br/>
		预定时间：<br/>
		<fmt:formatDate value="${orderForm.orderTime }" pattern="yyyy-MM-dd HH:mm"/>
	</div>
	<div class="hang">
		您现在可以：<br/>
		<hk:a href="/op/orderform_viewwap.do?oid=${orderForm.oid}">查看订单</hk:a><br/>
		<hk:a href="/product_listwap.do?companyId=${companyId}">继续购物</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>