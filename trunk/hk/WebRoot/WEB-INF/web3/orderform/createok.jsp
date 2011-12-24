<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.orderform.createok" /></c:set>
<c:set var="mgr_content" scope="request">
<div class="text_16">
<strong><hk:data key="view.orderform.createok" /></strong><br/>
<table cellpadding="0" cellspacing="0" class="infotable heavy">
<tr><td>您的订单编号：</td><td><span class="yzm">${orderForm.oid }</span></td></tr>
<tr><td>应付金额：</td><td><span class="yzm">￥${orderForm.price }</span></td></tr>
<tr><td>付款方式：</td><td><span class="yzm"><hk:data key="view.orderform.checkouttype${orderForm.checkoutflg}"/></span></td></tr>
<tr><td>预定时间：</td><td><span class="yzm"><fmt:formatDate value="${orderForm.orderTime }" pattern="yyyy-MM-dd HH:mm"/></span></td></tr>
<c:if test="${orderForm.tableId>0}">
<tr><td>预定餐桌：</td><td><span class="yzm">${orderForm.tableNum }</span></td></tr>
</c:if>
</table>
<strong>您现在还可以:</strong><br/>
<a class="split-r" href="<%=path %>/op/orderform.do">查看订单</a>
<a class="split-r" href="<%=path %>/product_list.do?companyId=${companyId}">继续购物</a>
<c:if test="${orderForm.tableId==0 && company.parentKindId==1}">
<a href="<%=path %>/op/orderform_tablelist.do?companyId=${companyId}&oid=${oid}">选择餐桌</a>
</c:if>
</div>
</c:set>
<jsp:include page="../inc/usermgr_inc.jsp"></jsp:include>