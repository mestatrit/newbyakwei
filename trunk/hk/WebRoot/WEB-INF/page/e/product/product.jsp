<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${cmpProduct.name}" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang reply">
		${cmpProduct.name}
		<c:if test="${cmpProduct.money>0}">￥<fmt:formatNumber value="${cmpProduct.money}" pattern="####.#"></fmt:formatNumber></c:if><br/>
		<c:if test="${cmpProduct.score>0}"><img class="imgd" src="<%=request.getContextPath() %>/st/img/st${cmpProduct.starsRate }.gif"/></c:if><br/>
		<c:if test="${not empty cmpProduct.intro}">${cmpProduct.intro}<br/></c:if>
	</div>
	<div class="reply">
	<c:if test="${not empty cmpProduct.headPath}"><img src="${cmpProduct.head240 }"/><br/></c:if>
	</div>
	<div class="hang">
		<c:if test="${morephoto}"><hk:a href="/product_photo.do?pid=${pid}&skip=1" clazz="split-r">下图</hk:a></c:if>
		<c:if test="${hasfav}"><hk:a href="/op/product_delfavwap.do?pid=${pid}" clazz="split-r">取消收藏</hk:a></c:if>
		<c:if test="${hasfav==null || !hasfav}"><hk:a href="/op/product_favwap.do?pid=${pid}" clazz="split-r">收藏</hk:a></c:if>
		<c:if test="${addtocard}">已下单，<hk:a href="/shoppingcard_wap.do">马上确认</hk:a></c:if>
		<c:if test="${addtocard==null || !addtocard }"><hk:a href="/product_addtocardwap.do?pid=${pid}">下单</hk:a></c:if>
	</div>
	<div class="hang">
	<hk:a href="/product_listwap.do?companyId=${cmpProduct.companyId}">返回</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>