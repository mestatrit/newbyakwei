<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${product.cmpProduct.name}" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${product.cmpProduct.name}</div>
	<div class="hang">
	<hk:form action="/shoppingcard_update.do">
		<hk:hide name="pid" value="${pid}"/>
		数量：<br/>
		<hk:text name="count" value="${product.count}"/><br/>
		单价：￥${product.cmpProduct.money }<br/>
		小计：￥${product.cmpProduct.money*product.count }<br/>
		<hk:submit value="提交"/>
	</hk:form>
	</div>
	<div class="hang">
	<hk:a href="/shoppingcard_wap.do">回到购物车</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>