<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改密码 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">修改密码</div>
	<div class="hang">
		<hk:form action="/bomb/bomb_chgpwd.do">
			旧密码:<br/>
			<hk:pwd name="oldPwd" maxlength="10"/><br/>
			新密码:<br/>
			<hk:pwd name="newPwd" maxlength="10"/><br/>
			<hk:submit value="提交"/>
		</hk:form>
	</div>
	<c:if test="${admin || superAdmin}">
	<div class="hang"><hk:a href="/adminbomb/bomb.do">返回</hk:a></div>
	</c:if>
	<c:if test="${!admin && !superAdmin}">
	<div class="hang"><hk:a href="/bomb/bomb_list.do">返回</hk:a></div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>