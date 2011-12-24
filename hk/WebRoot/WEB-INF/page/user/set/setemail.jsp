<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="修改E-mail - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_setEmail.do">
		<c:if test="${info.authedMail}">
			<hk:data key="view.inputnewemail"/>:<br/>
			<hk:text name="email" value="" maxlength="50"/><br/>
		</c:if>
		<c:if test="${!info.authedMail}">
			<hk:data key="view.inputemail"/>:<br/>
			<hk:text name="email" value="${info.email}" maxlength="50"/><br/>
		</c:if>
		<hk:submit value="提交并发送认证链接"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>