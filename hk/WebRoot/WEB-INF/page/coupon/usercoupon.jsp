<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		${coupon.name}<br/>
		您的优惠券暗号为：${userCoupon.mcode }<br/>
		<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>到期
	</div>
	<div class="hang even"><hk:a href="/coupon_list.do?cityId=${sys_zone_pcityId}&route=1">返回优惠券列表</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>