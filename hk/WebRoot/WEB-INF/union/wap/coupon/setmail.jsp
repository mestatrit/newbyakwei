<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">[${company.name }]${coupon.name}<br/></div>
<div class="row">
	<hk:form method="get" action="/union/op/coupon_setmailanddownload.do">
		输入E-mail<br/>
		<hk:text name="email"/><br/>
		<hk:hide name="uid" value="${uid}"/> 
		<hk:hide name="couponId" value="${couponId }"/> 
		<hk:submit name="formail" value="提交"/>
	</hk:form>
</div>
<div class="row">
	<hk:a href="/union/coupon.do?uid=${uid}&couponId=${couponId }">返回到优惠券</hk:a><br/>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>