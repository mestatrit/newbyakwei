<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">[${company.name }]${coupon.name}</div>
<div class="row">
	${coupon.content }<br/>
	${coupon.remark }<br/>
</div>
<c:if test="${not empty coupon.picpath}"><img src="${coupon.pic240 }"/></c:if>
<c:if test="${coupon.remain}">
	<div class="row even">
		绿色通道：<br/>
		直接发送yh${couponId }到1066916025自动下载此优惠券.<br/>
		注：将本优惠券免费发送至手机后或出示手机展示版优惠券才可使用<br/>
		<hk:form method="get" action="/union/op/coupon_download.do">
			<hk:hide name="uid" value="${uid}"/> 
			<hk:hide name="couponId" value="${couponId }"/> 
			<hk:submit name="formobile" value="发到手机"/> 
			<hk:submit name="formail" value="发到邮箱"/>
		</hk:form>
	</div>
</c:if>
<c:if test="${!coupon.remain}"><div class="row">很抱歉，优惠券已经发行完毕</div></c:if>
<div class="row">
	<hk:a href="/union/coupon_list.do?uid=${uid}">返回</hk:a><br/>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>