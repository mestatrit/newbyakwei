<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();%>
<c:if test="${fn:length(list)>0}">
	<ul class="product-list2">
		<c:forEach var="product" items="${list}">
		<li class="hasimg"><a href="<%=path %>/product.do?pid=${product.productId}"><img src="${product.head60 }"/>
		${product.name }</a></li>
		</c:forEach>
	</ul>
	<div>
		<c:if test="${hasmore}"><a class="apage" href="javascript:loadproductforpage(${page+1 })">下一页</a></c:if>
		<c:if test="${haspre}"><a class="apage" href="javascript:loadproductforpage(${page-1 })">上一页</a></c:if> 
	</div>
</c:if>