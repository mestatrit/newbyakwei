<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="${coupon.name}" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><c:if test="${company!=null}">[<hk:a href="/e/cmp.do?companyId=${coupon.companyId}">${company.name}</hk:a>]</c:if> ${coupon.name}</div>
	<div class="hang odd">
		${coupon.content }<br/>
		${coupon.remark }<br/>
	</div>
	<c:if test="${not empty coupon.picpath}"><img src="${coupon.h_1Pic }"/></c:if>
	<c:if test="${coupon.remain}">
		<div class="hang even">
			<hk:form method="get" action="/op/coupon_download.do">
				<hk:hide name="couponId" value="${couponId }"/> 
				<hk:submit name="tomobile" value="发到手机"/> 
				<hk:submit name="tomail" value="发到邮箱"/>
			</hk:form>
		</div>
	</c:if>
	<c:if test="${!coupon.remain}"><div class="hang"><hk:data key="view2.coupon.remain.no"/></div></c:if>
	<div class="hang even"><hk:a href="/coupon_list.do?cityId=${sys_zone_pcityId}&route=1">返回</hk:a></div>
	<c:if test="${coupon.userId==loginUser.userId}">
		<div class="hang">
			<hk:a href="/op/coupon_update.do?couponId=${couponId}">修改</hk:a><br/>
			<hk:a href="/op/coupon_setunavailable.do?couponId=${couponId}">作废</hk:a><br/>
			<hk:a href="/op/coupon_list.do">管理优惠券</hk:a>
		</div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>