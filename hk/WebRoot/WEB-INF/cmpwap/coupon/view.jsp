<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.CmpUnionSite"%><%@page import="web.pub.util.CmpUnionModuleUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">
<a href="<%=path %>/epp/web/cmpnav_wap.do?companyId=${companyId}&navId=${cmpNav.oid}">${cmpNav.name }</a>
</div>
<div class="row">
${coupon.name }
</div>
<c:if test="${not empty coupon.picpath}">
	<div class="row"><img src="${coupon.h_1Pic }"/></div>
</c:if>
<div class="row">
${coupon.content }
</div>
<c:if test="${not empty coupon.remark}">
<div class="row"><strong><hk:data key="epp.coupon.remark"/>：</strong> ${coupon.remark }</div>
</c:if>
<div class="row">
	<form method="post" action="<%=path %>/epp/web/coupon_wapprvdownload.do?companyId=${companyId}&couponId=${couponId}&navId=${navId}">
		<hk:submit clazz="btn" value="epp.coupon.downloadtoemail" res="true"/>
	</form>
</div>
<div class="row">
<a href="<%=path %>/epp/web/coupon_wap.do?companyId=${companyId}&navId=${navId}"><hk:data key="epp.return"/></a>
</div>
<jsp:include page="../inc/foot_inc.jsp"></jsp:include>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>