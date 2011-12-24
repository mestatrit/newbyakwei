<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="绑定手机号 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:a href="/user/set/set_toSetMobile1.do">通过用户名和密码认证</hk:a><br />(推荐手机上网用户使用)
	</div>
	<div class="hang">
		<hk:a href="/user/set/set_toSetMobile2.do">通过验证码认证</hk:a><br />(推荐pc用户使用)
	</div>
	<div class="hang">
		<hk:a href="/user/set/set.do">回到设置</hk:a>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>