<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<div class="mod-1">
	<%=Hkcss2Util.rd_bg%>
	<div class="tit">消息提醒</div>
	<div  class="cont">
		<c:if test="${not_deal_order_count>0}">
			<a href="<%=path %>/e/op/orderform_"><hk:data key="view.company.orderform.notdeal" arg0="${not_deal_order_count}"/></a>
		</c:if>
		<c:if test="${booked_not_meal_count>0}">
			<a href="<%=path %>/e/op/auth/table_"><hk:data key="view.company.cmptable.booked.notdeal" arg0="${booked_not_meal_count}"/></a>
		</c:if>
	</div>
	<%=Hkcss2Util.rd_bg_bottom%>
</div>