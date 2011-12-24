<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="inc/top.jsp"></jsp:include>
		<hk:form action="/epp/reg_reg.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<div class="hang">Email:<br/>
			<hk:text name="input" value="${input}" maxlength="50"/></div>
			<div class="hang s">用于登录或找回密码,完全保密</div>
			<div class="hang">密码:<br/>
			<hk:pwd name="password" maxlength="16" /></div>
			<div class="hang s">4-16位以上数字或字母</div>
			<div class="hang">重复输入密码:<br/>
			<hk:pwd name="password1" maxlength="16"/></div>
			<div class="hang">验证码：<br/>
				<img src="<%=request.getContextPath() %>/index_showImg.do?v=<%=Math.random() %>"/>
				<hk:a clazz="s" href="/epp/reg.do?companyId=${companyId}">刷新</hk:a>
				<br/>
				<hk:text name="code" maxlength="4"/><br/>
			</div>
			<div class="hang"><hk:submit name="n" value="注册"/></div>
		</hk:form>
		<div class="hang even"><hk:a href="/epp/index_wap.do?companyId=${companyId }"><hk:data key="view.returhome"/></hk:a></div>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>