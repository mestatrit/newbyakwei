<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><hk:wap title="登录" rm="false">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/epp/login_validate.do" needreturnurl="true">
			<hk:hide name="companyId" value="${companyId}"/>
			<div class="ha">
				Email,手机号或昵称:<br/>
				<hk:text name="input" value="${input}"/>
			</div>
			<div class="ha">
				密码:<br/>
				<hk:pwd name="password" value="${password}"/>
			</div>
			<div class="ha"><hk:submit value="登录"/></div>
			<div class="hang"><hk:checkbox name="autologin" value="true" data="下次自动登录" checkedvalue="true"/></div>
		</hk:form>
	</div>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>