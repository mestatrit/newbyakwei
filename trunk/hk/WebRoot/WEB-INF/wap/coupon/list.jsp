<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="title"><hk:data key="view.site.coupon"/></c:set>
<hk:wap title="${title} - ${o.name }" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even"><hk:data key="view.site.coupon"/></div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="c" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/epp/coupon.do?couponId=${c.couponId}&companyId=${companyId }">${c.name }</hk:a>
		</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(list)==0}"><div class="hang even">目前没有优惠券</div></c:if>
	<div class="hang even"><a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></a></div>
	<hk:simplepage2 href="/epp/coupon_list.do?companyId=${companyId}"/>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>