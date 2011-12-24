<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="row">[${company.name }]${coupon.name}</div>
<div class="row">
	您的优惠券暗号为：${userCoupon.mcode }<br/>
	<fmt:formatDate value="${userCoupon.endTime}" pattern="yyyy-MM-dd"/>到期
</div>
<div class="row">
	<hk:a href="/union/coupon_list.do?uid=${uid}">返回</hk:a><br/>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>