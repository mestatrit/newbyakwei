<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="${user.nickName }的首页 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:set var="arg0"><hk:a href="/home.do?userId=${userId}">${user.nickName }</hk:a></c:set>
	<div class="hang even"><hk:data key="view2.user.badge" arg0="${arg0}"/></div>
	<c:forEach var="b" items="${list}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<img src="${b.pic57}">
			${b.name }：${b.intro }
		</div>
	</c:forEach>
	<div class="hang">
	<hk:simplepage2 href="/home_userbadge.do?userId=${userId}"/>
	</div>
	<div class="hang">
	<hk:a href="/home.do?userId=${userId}"><hk:data key="view2.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>