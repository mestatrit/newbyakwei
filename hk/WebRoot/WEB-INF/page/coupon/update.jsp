<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="修改优惠券" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:set var="coupon_form_action" value="/op/coupon_update.do" scope="request"/>
		<jsp:include page="couponform.jsp"></jsp:include>
	</div>
	<div class="hang even">
		<c:if test="${empty coupon.picpath}">
			<hk:a href="/op/coupon_selcouponpic.do?couponId=${couponId}">添加图片</hk:a>
		</c:if>
		<c:if test="${not empty coupon.picpath}">
			<img src="${coupon.h_0Pic }"/><br/>
			<hk:a href="/op/coupon_selcouponpic.do?couponId=${couponId}">更换图片</hk:a>
		</c:if>
		<br/>
		<hk:a href="/op/coupon_list.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>