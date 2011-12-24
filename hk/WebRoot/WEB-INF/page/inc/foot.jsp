<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.PubDate"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${httpshoppingcard.totalCount>0}">
<div class="hang">
	<hk:a href="/shoppingcard_wap.do">购物车内有${httpshoppingcard.totalCount}件商品，马上确认</hk:a>
</div>
</c:if>
<c:if test="${!showMode.touchMode}"><jsp:include page="menu.jsp"></jsp:include></c:if>
<div class="hang s"><%=PubDate.getNowTime() %></div>
<div class="hang s">CopyRight 2009 huoku.com <a target="_blank" href="http://www.miibeian.gov.cn/">京ICP备09054036号</a></div>