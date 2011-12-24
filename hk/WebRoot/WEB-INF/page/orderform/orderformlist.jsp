<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="订单列表" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">订单列表</div>
	<div class="hang">
	<hk:form method="get" action="/op/orderform_wap.do">
		订单编号：<br/>
		<hk:text name="key" value="${key}"/><hk:submit value="搜索"/>	
		</hk:form>
	</div>
	<c:if test="${fn:length(orderformlist)>0}">
		<c:forEach var="order" items="${orderformlist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/op/orderform_viewwap.do?oid=${order.oid}">${order.company.name}</hk:a><br/>
			<fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"/>
		</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(orderformlist)==0}"><div class="hang"><hk:data key="nodataview"/></div></c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>