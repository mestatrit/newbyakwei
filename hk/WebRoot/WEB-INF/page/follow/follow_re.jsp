<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="谁在关注${user.nickName} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang"><hk:a href="/follow/follow.do?userId=${userId}">关注</hk:a>|<hk:a clazz="nn" href="/follow/follow_re.do?userId=${userId}">粉丝</hk:a></div>
	<c:set var="addStr" value="&from=follow_re&ouserId=${userId }&nickName=${enc_nickName }" scope="request"/>
	<jsp:include page="../inc/uservo.jsp"></jsp:include>
	<c:if test="${fn:length(uservolist)>=0}">
	<hk:simplepage href="/follow/follow_re.do?userId=${userId}&nickName=${enc_nickName }" returnhref="/home.do?userId=${user.userId}" returndata="返回"/>
	</c:if>
	<c:if test="${loginUser.userId==userId}">
	<div class="hang">
		<hk:form method="get" action="/follow/follow_re.do">
			昵称:<hk:text name="nickName" value="${nickName}"/>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>