<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="优惠券" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:if test="${cityId>0}"><c:set var="city_css">nn</c:set></c:if>
	<c:if test="${cityId==0}"><c:set var="all_css">nn</c:set></c:if>
	<div class="hang"><hk:a clazz="${city_css}" href="/coupon_list.do?cityId=${sys_zone_pcityId}">${sys_zone_pcity.name}</hk:a>|<hk:a clazz="${all_css}" href="/coupon_list.do">全球</hk:a></div>
	<div class="hang">优惠券</div>
	<c:if test="${fn:length(list)==0}">
		暂时没有优惠券发布
	</c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/coupon.do?couponId=${c.couponId}">${c.name }</hk:a> 
				${c.amount }/${c.dcount } 
			</div>
		</c:forEach>
	</c:if>
	<div class="hang">
		<hk:simplepage2 href="/coupon_list.do?cityId=${cityId}"/>
	</div>
	<div class="hang"><hk:a href="/index_h.do">返回首页</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>