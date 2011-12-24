<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="管理优惠券" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">管理优惠券</div>
	<c:if test="${fn:length(list)==0}">
		暂时没有优惠券发布
	</c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/coupon.do?couponId=${c.couponId}">${c.name }</hk:a> 
				${c.amount }/${c.dcount } 
				<c:if test="${!c.available}">已作废</c:if>
				<hk:a href="/op/coupon_update.do?couponId=${c.couponId}">修改</hk:a>
				<c:if test="${c.available}">
					<hk:a href="/op/coupon_setunavailable.do?couponId=${c.couponId}">作废</hk:a>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang">
		<hk:simplepage2 href="/op/coupon_list.do"/>
	</div>
	<div class="hang even"><hk:a href="/op/coupon_create.do">发布优惠券</hk:a></div>
	<div class="hang even"><hk:a href="/more.do"><hk:data key="view2.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>