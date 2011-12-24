<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
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
			<div style="margin-bottom: 20px">
			<c:forEach var="photo" items="${photolist}">
				<img src="${photo.pic640 }"/>
			</c:forEach>
			</div>
			<c:set var="page_url" scope="request"><%=path %>/epp/web/product_viewpic.do?companyId=${companyId}&navId=${navId}&productId=${productId }&sortId=${sortId}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>