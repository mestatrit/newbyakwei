<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="绑定手机号 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<c:if test="${userOtherInfo.mobileBind==1}">
			您已经成功绑定了手机号${userOtherInfo.mobile}
		</c:if>
		<c:if test="${userOtherInfo.mobileBind!=1}">
			请耐心等待1分钟,1分钟后再次<hk:a href="/user/set/set_sendyzok.do">刷新</hk:a>本页面<br/>
			如果绑定还不成功,请<hk:a href="/user/set/set_toSetMobile2.do">重新获得验证码</hk:a>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>