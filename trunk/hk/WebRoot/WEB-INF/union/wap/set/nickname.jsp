<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpUnion.name}</c:set>
<c:set var="html_main_content" scope="request">
<div class="nav2">修改昵称</div>
<div class="row">
	<hk:form action="/union/op/userset_updatenickname.do">
		<hk:hide name="uid" value="${uid}"/>
		昵称：<br/>
		<hk:text name="nickName" value="${user.nickName}"/><br/>
		<hk:submit value="提交"/>
	</hk:form>
</div>
<div class="row">
<hk:a href="/union/home.do?uid=${uid}&userId=${cmpUnion_loginUser.userId }">返回</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>