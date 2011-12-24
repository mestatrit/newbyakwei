<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="title_value" scope="request">${cmpProduct.name }|${cmpProductSort.name }|${cmpNav.name }</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpProduct.name }|${cmpProductSort.name }|${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpProduct.name }|${cmpProductSort.name }|${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<c:if test="${fn:length(after_other_product_list)>0}">
				<div class="f_r" style="font-weight: bold;font-size: 30px;">
					<a href="<%=path %>/epp/web/product_next.do?companyId=${companyId}&navId=${navId}&productId=${productId }&sortId=${sortId}">&gt;&gt;</a>
				</div>
			</c:if>
			<div style="margin: 20px 20px 20px 0;"><h1><a href="/product/${companyId }/${navId }/${sortId }/${productId }.html">${cmpProduct.name }</a></h1></div>
			<c:if test="${cmpProduct.money>0}">
			<span class="b"><hk:data key="epp.product.price"/></span>： ￥${cmpProduct.money }<br/>
			</c:if>
			<c:if test="${cmpProduct.rebate>0}">
			<span class="b"><hk:data key="epp.product.rebate"/></span>：<fmt:formatNumber value="${cmpProduct.rebate }" pattern="##.#"></fmt:formatNumber><br/>
			</c:if>
			<c:if test="${not empty cmpProduct.headPath}">
				<div style="margin-bottom: 20px">
				<img src="${cmpProduct.head600 }"/>
				</div>
			</c:if>
			${cmpProduct.intro }
			<c:if test="${fn:length(photolist)>1}">
			<div class="page3">
				<a class="p_r" href="<%=path %>/epp/web/product_viewpic.do?companyId=${companyId}&navId=${navId}&productId=${productId }&sortId=${sortId}"><hk:data key="epp.product.photo.next"/></a>
				<div class="clr"></div>
			</div>
			</c:if>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>