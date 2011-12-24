<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.mgrsite.coupon"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:data key="view.mgrsite.coupon"/>|<hk:a href="/epp/mgr/coupon_tocreate.do?companyId=${companyId}"><hk:data key="view.mgrsite.coupon.create"/></hk:a></div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				${c.name } ${c.amount } 
				<hk:a href="/epp/mgr/coupon_toupdate.do?companyId=${companyId}&couponId=${c.couponId }"><hk:data key="view.update2"/></hk:a> 
				<c:if test="${c.available}">
					<hk:a href="/epp/mgr/coupon_setunavailable.do?companyId=${companyId}&couponId=${c.couponId }"><hk:data key="view.mgrsite.coupon.setunavailable"/></hk:a>
				</c:if>
				<c:if test="${!c.available}">
					<hk:a href="/epp/mgr/coupon_setavailable.do?companyId=${companyId}&couponId=${c.couponId }"><hk:data key="view.mgrsite.coupon.setavailable"/></hk:a>
				</c:if>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang even"><c:if test="${fn:length(list)==0}">没有优惠券数据</c:if></div>
	<hk:simplepage href="/epp/mgr/coupon_list.do?companyId=${companyId}"/>
	<div class="hang even"><hk:a href="/epp/mgr/mgr.do?companyId=${companyId}"><hk:data key="view.returnmain"/></hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>