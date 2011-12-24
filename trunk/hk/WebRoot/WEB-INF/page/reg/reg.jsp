<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
		<hk:form method="post" action="/reg.do">
			<div class="hang">Email：<br/>
			<hk:text name="input" value="${input}"/></div>
			<div class="hang s">用于登录火酷或找回密码,完全保密</div>
			<div class="hang">密码：<br/>
			<hk:pwd name="password" maxlength="16" /></div>
			<div class="hang s">4-16位以上数字或字母</div>
			<div class="hang">重复输入密码:<br/>
			<hk:pwd name="password1" maxlength="16"/></div>
			<div class="hang">邀请码:<br/>
			<hk:text name="inviteCode" value="${inviteCode}"/></div>
			<!-- 
			<div class="hang">验证码：<br/>
				<img src="<%=request.getContextPath() %>/index_showImg.do?v=<%=Math.random() %>"/>
				<hk:a clazz="s" href="/reg_toreg.do?inviteCode=${inviteCode}">刷新</hk:a>
				<br/>
				<hk:text name="code" maxlength="4"/><br/>
			</div>
			 -->
			<div class="hang">
			<hk:checkbox name="agree" value="1" checkedvalue="${agree}"/>
			同意<hk:a href="/pub/xieyi.do?inviteCode=${inviteCode}">火酷协议</hk:a></div>
			<div class="hang"><hk:submit name="n" value="普通用户注册"/></div>
			<div class="hang s">贴士:博色帐号可以直接登录</div>
		</hk:form>
		<div class="hang"><hk:a href="/tologin.do">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>