<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="爆破手登录 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">爆破手登录</div>
	<div class="hang">
		<hk:form action="/loginbomb/bomb_login.do">
			密码:<br/>
			<hk:pwd name="pwd" maxlength="10"/><br/>
			<hk:submit value="登录"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>