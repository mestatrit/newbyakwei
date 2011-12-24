<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${coupon.name}</div>
	<div class="hang odd">
		<c:if test="${err==162}"><hk:data key="func.createusercoupon.mobile_not_bind" arg0="yh${coupon.couponId}"/></c:if>
		<c:if test="${err==165}"><hk:data key="func.createusercoupon.mobile_not_cmp_mobile" arg0="yh${coupon.couponId}"/></c:if>
		<br/>
		注：将本优惠券免费发送至手机后或出示手机展示版优惠券才可使用
	</div>
	<div class="hang even"><hk:a href="/coupon.do?couponId=${couponId}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>