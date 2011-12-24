<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:forEach var="pvo" items="${productvolist}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="even" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="odd" /></c:if>
<div class="hang ${clazz_var }">
<hk:a href="/product_wap.do?pid=${pvo.cmpProduct.productId}">${pvo.cmpProduct.name }</hk:a>
<c:if test="${pvo.cmpProduct.money>0}">￥<fmt:formatNumber value="${pvo.cmpProduct.money}" pattern="####.#"></fmt:formatNumber></c:if>
</div>
</c:forEach>