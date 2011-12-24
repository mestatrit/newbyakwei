<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="选择图片 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">选择图片</div>
	<div class="hang">
		<hk:form enctype="multipart/form-data" action="/op/coupon_uploadpic.do">
			<hk:hide name="couponId" value="${couponId}"/>
			<hk:file name="f"/>
			<hk:submit value="上传图片"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="p" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<img src="${p.h_0Pic }"/>
			<hk:a href="/op/coupon_selpic.do?couponId=${couponId}&photoId=${p.photoId }">选择</hk:a>
		</div>
		</c:forEach>
		<hk:simplepage2 href="/op/coupon_selcouponpic.do?couponId=${couponId}" />
	</c:if>
	<c:if test="${fn:length(list)==0}">暂时没有图片</c:if>
	<div class="hang"><hk:a href="/op/coupon_update.do?couponId=${couponId}">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>