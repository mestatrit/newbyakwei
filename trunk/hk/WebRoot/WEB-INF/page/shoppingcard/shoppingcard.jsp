<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="购物车" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">购物车 金额总计：￥${totalPrice }</div>
	<c:forEach var="vo" items="${productvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
	<div class="hang ${clazz_var }">
	<hk:a href="/product_wap.do?pid=${vo.cmpProduct.productId}">${vo.cmpProduct.name }</hk:a> ${vo.count }份
	<hk:a href="/shoppingcard_toupdate.do?pid=${vo.cmpProduct.productId}" clazz="s">修改</hk:a>
	<br/>
	单价：￥${vo.cmpProduct.money } 小计：￥${vo.cmpProduct.money*vo.count }
	</div>
	</c:forEach>
	<div class="hang">金额总计：￥${totalPrice }</div>
	<div class="hang">
	<hk:a href="/shoppingcard_cleanwap.do">清空购物车</hk:a><br/>
	<c:if test="${companyId==null || companyId==0}"><hk:a href="/e/cmp_nearby.do">继续购物</hk:a></c:if>
	<c:if test="${companyId>0}"><hk:a href="/product_listwap.do?companyId=${companyId}">继续购物</hk:a></c:if>
	</div>
	<div class="hang">
	<hk:form method="get" action="/op/orderform_cfmwap.do"><hk:submit value="去结算"/></hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>