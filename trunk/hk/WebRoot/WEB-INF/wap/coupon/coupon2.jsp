<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="[${o.name }]${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<a href="/epp/index.do?companyId=${companyId }">[${o.name }]</a>${coupon.name}<br/>
	</div>
	<div class="hang even">
		<c:if test="${error==162}"><hk:data key="func.createusercoupon.mobile_not_bind" arg0="yh${coupon.couponId}"/></c:if>
		<c:if test="${error==165}"><hk:data key="func.createusercoupon.mobile_not_cmp_mobile" arg0="yh${coupon.couponId}"/></c:if>
		<br/>
		注：将本优惠券免费发送至手机后或出示手机展示版优惠券才可使用
	</div>
	<div class="hang even"><hk:a href="/epp/coupon_list.do?companyId=${companyId}">返回优惠券首页</hk:a></div>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>