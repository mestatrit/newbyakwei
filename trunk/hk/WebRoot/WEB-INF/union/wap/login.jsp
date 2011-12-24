<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">${cmpUnion.name}</c:set>
<c:set var="html_main_content" scope="request">
	<div class="row">
		<hk:form action="/union/login.do" needreturnurl="true">
			<hk:hide name="uid" value="${uid}"/>
			<div class="row">Email,手机号或昵称:</div>
			<div class="row"><hk:text name="input" value="${cookieInfo.input}" maxlength="50"/></div>
			<div class="row">密码:</div>
			<div class="row"><hk:pwd name="password" maxlength="16" /></div>
			<div class="row"><hk:submit value="登录" /></div>
			<div class="row"><hk:checkbox name="autologin" value="1" data="下次自动登录" checkedvalue="1"/></div>
			<div class="row">还没有账号,马上<hk:a href="/union/reg_toreg.do?uid=${uid}">免费注册</hk:a> 或<hk:a href="/union/union.do?uid=${uid}">浏览进入</hk:a></div>
		</hk:form>
	</div>
</c:set>
<jsp:include page="inc/frame.jsp"></jsp:include>