<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:if test="${sortId==current_each_sortId && (fn:length(before_other_product_list)>0 || fn:length(after_other_product_list)>0)}">
	<div style="padding-left: 20px;">
		<c:forEach var="other_product" items="${before_other_product_list}">
		<c:set var="product_url">/product/${companyId}/${navId}/${current_each_sortId}/${other_product.productId }.html</c:set>
		<div class="nav2_content">
			<a href="${product_url }">${other_product.name }</a>
		</div>
		</c:forEach>
		<c:forEach var="other_product" items="${after_other_product_list}">
		<c:set var="product_url">/product/${companyId}/${navId}/${current_each_sortId}/${other_product.productId }.html</c:set>
		<div class="nav2_content">
			<a href="${product_url }">${other_product.name }</a>
		</div>
		</c:forEach>
	</div>
</c:if>