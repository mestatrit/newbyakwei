<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false">
	<jsp:include page="inc/top.jsp"></jsp:include>
		<hk:form method="post" action="/login_wap.do" needreturnurl="true">
			<hk:hide name="wapflg" value="${wapflg}"/>
			<div class="hang">Email或手机号:</div>
			<div class="hang"><hk:text name="input" value="${input}" maxlength="50"/></div>
			<div class="hang">密码:</div>
			<div class="hang"><hk:pwd name="password" maxlength="16" value="${password}"/></div>
			<div class="hang"><hk:submit name="" value="登录火酷" /></div>
			<div class="hang"><hk:checkbox name="autologin" value="1" data="下次自动登录" checkedvalue="1"/></div>
			<div class="hang"><hk:a href="/pub/fgtpwd/fgtpwd.do">忘记密码？</hk:a></div>
			<div class="hang">还没有火酷,马上<hk:a href="/reg_toreg.do">免费注册</hk:a> 或<hk:a href="/index_h.do">浏览进入</hk:a></div>
		</hk:form>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>