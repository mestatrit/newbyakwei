<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${cmpProduct.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang even">${cmpProduct.name}的图片</div>
	<div class="odd">
	<c:forEach var="photo" items="${photolist}">
		<img src="${photo.pic240 }"/>
	</c:forEach>
	</div>
	<div class="hang"><hk:simplepage2 href="/product_photo.do?pid=${pid}"/></div>
	<div class="hang">
	<hk:a href="/product_wap.do?pid=${pid}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>