<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<c:if test="${followed && cmpUnion_loginUser.userId!=userId}">
<div class="row">
<hk:form action="/union/op/msg/send.do">
<hk:hide name="uid" value="${uid}"/>
<hk:hide name="receiverId" value="${userId}"/>
<div><hk:text name="msg" clazz="rowipt"/></div>
<hk:submit value="发送私信"/>
</hk:form>
</div>
</c:if>
<table class="list">
	<tr>
		<td class="userimg">
			<img src="${user.head48Pic}" alt="${user.nickName}" />
		</td>
		<td>
			${user.nickName }
		</td>
	</tr>
</table>
<c:if test="${fn:length(favproductlist)>0}">
<div class="nav2">我收藏的产品</div>
<div class="row">
	<c:forEach var="p" items="${favproductlist}">
		<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/product.do?uid=${uid }&pid=${p.productId}">${p.name}</hk:a></p>
	</c:forEach>
	<c:if test="${morefav}"><hk:a href="/union/product_userfav.do?uid=${uid}&userId=${userId }">更多</hk:a></c:if>
</div>
</c:if>
<c:if test="${fn:length(userproductlist)>0}">
<div class="nav2">我买过的产品</div>
<div class="row">
	<c:forEach var="p" items="${userproductlist}">
		<p><img class="img-icon" src="<%=path %>/unionst/wap/img/arrow_grey.gif"/><hk:a href="/union/product.do?uid=${uid }&pid=${p.productId}">${p.name}</hk:a></p>
	</c:forEach>
	<c:if test="${morebuy}"><hk:a href="/union/product_userbuy.do?uid=${uid}&userId=${userId }">更多</hk:a></c:if>
</div>
</c:if>
<c:if test="${cmpUnion_loginUser.userId==userId}">
<div class="nav2"><hk:a href="/union/op/userset_toupdatenickname.do?uid=${uid}">修改昵称</hk:a></div>
<div class="nav2"><hk:a href="/union/op/userset_toupdatepwd.do?uid=${uid}">修改密码</hk:a></div>
</c:if>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>