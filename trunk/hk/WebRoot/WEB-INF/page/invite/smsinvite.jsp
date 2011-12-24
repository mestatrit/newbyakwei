<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="邀请记录 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/invite/invite.do">成功的邀请</hk:a>|<hk:a href="/invite/invite_all.do" clazz="n">邀请记录</hk:a></div>
	<div class="hang">
		<hk:form action="/invite/invite_sendInviteSms.do">
			<hk:submit value="发送短信到手机"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/invite/invite_toinvite.do">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>