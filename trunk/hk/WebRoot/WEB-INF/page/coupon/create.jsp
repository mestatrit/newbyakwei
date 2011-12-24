<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="发布优惠券" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:set var="coupon_form_action" value="/op/coupon_create.do" scope="request"/>
		<jsp:include page="couponform.jsp"></jsp:include>
	</div>
	<div class="hang even">
		<hk:a href="/op/coupon_list.do">优惠券列表</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>