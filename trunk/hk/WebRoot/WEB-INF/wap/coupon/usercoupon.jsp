<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="[${o.name }]${userCoupon.coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:a href="/epp/index.do?companyId=${companyId }">[${o.name }]</hk:a>${userCoupon.coupon.name}<br/>
		您的优惠券暗号为：${userCoupon.mcode }<br/>
		<fmt:formatDate value="${userCoupon.endTime}" pattern="yyyy-MM-dd"/>到期
	</div>
	<div class="hang even"><hk:a href="/epp/coupon_list.do?companyId=${companyId}">返回优惠券首页</hk:a></div>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>