<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
<div class="mod">
	<h1 class="s1">${coupon.name }</h1>
	<div class="mod_content">
		<div class="divrow">
			<hk:data key="epp.coupon.usercoupon.cipher"/>：${userCoupon.mcode }
		</div>
		<div class="divrow">
			<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>到期
		</div>
		<div>
			<a class="more2" href="<%=path %>/epp/web/coupon_view.do?companyId=${companyId}&couponId=${coupon.couponId}&navId=${navId}"><hk:data key="epp.return"/></a>
		</div>
	</div>
</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>