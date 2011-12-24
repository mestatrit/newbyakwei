<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">${cmpProduct.name }</div>
<div class="row">
<c:if test="${not empty cmpProduct.headPath}">
<img src="${cmpProduct.head240 }"/><br/>
</c:if>
价格：￥${cmpProduct.money }<br/>
<c:if test="${not empty cmpProduct.intro}">${cmpProduct.intro }</c:if><br/>
<c:if test="${!fav}">
<hk:form action="/union/op/product_fav.do?uid=${uid}&pid=${pid}">
	<hk:submit value="收藏"/>
</hk:form>
</c:if>
<c:if test="${fav}">
<hk:form action="/union/op/product_delfav.do?uid=${uid}&pid=${pid}">
	<hk:hide name="uid" value="${uid}"/>
	<hk:hide name="pid" value="${pid}"/>
	<hk:submit value="取消收藏"/>
</hk:form>
</c:if>
</div>
<div class="row">
	<c:if test="${fn:length(list2)>0}">所有分类&gt;<hk:rmstr value="&gt;"><c:forEach var="k" items="${list2}"><hk:a href="/union/kind.do?uid=${uid}&kindId=${k.kindId }">${k.name }</hk:a>&gt;</c:forEach></hk:rmstr><br/></c:if>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>