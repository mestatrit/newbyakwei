<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
<div class="mod">
	<h1 class="s1">${coupon.name }</h1>
	<div class="mod_content">
		<c:if test="${not empty coupon.picpath}">
			<div class="divrow"><img src="${coupon.h_2Pic }"/></div>
		</c:if>
		<div class="divrow">
			<form method="post" action="<%=path %>/epp/web/coupon_prvinputemail.do">
				<hk:hide name="ch" value="1"/>
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="navId" value="${navId}"/>
				<hk:hide name="couponId" value="${couponId}"/>
				<strong><hk:data key="epp.coupon.inputemail"/>：</strong><br/>
				<hk:text name="email" clazz="text"/>
				<hk:submit clazz="btn" value="epp.submit" res="true"/>
			</form>
		</div>
	</div>
</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>