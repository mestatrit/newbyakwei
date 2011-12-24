<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<c:forEach var="coupon" items="${list}">
				<div class="divrow" onmouseover="this.className='divrow bg3'" onmouseout="this.className='divrow'">
					<a class="split-r" href="<%=path %>/epp/web/coupon_view.do?companyId=${companyId}&couponId=${coupon.couponId}&navId=${navId}">${coupon.name }</a>
					<c:if test="${coupon.amount>coupon.dcount}">
						<hk:data key="epp.coupon.remain" arg0="${coupon.amount-coupon.dcount }"/>
					</c:if>
				</div>
			</c:forEach>
			<c:if test="${fn:length(list)==0}">
				<hk:data key="epp.nocoupondata"/>
			</c:if>
			<div>
				<c:set var="page_url" scope="request"><%=path%>/epp/web/coupon.do?companyId=${companyId}&navId=${navId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>