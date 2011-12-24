<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row">
${coupon.name }
</div>
<div class="row">
<hk:data key="epp.coupon.usercoupon.cipher"/>：${userCoupon.mcode }
</div>
<div class="row"><fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>到期</div>
<div class="row">
<a href="<%=path %>/epp/web/coupon_wapview.do?companyId=${companyId}&couponId=${coupon.couponId }&navId=${navId}"><hk:data key="epp.return"/></a>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>