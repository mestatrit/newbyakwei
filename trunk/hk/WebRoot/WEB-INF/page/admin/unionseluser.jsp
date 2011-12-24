<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${cmpUnion.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">${cmpUnion.name} - 管理员设置</div>
	<div class="hang even">
		<hk:form action="/admin/union_selopuser.do">
			<hk:hide name="uid" value="${uid}"/>
			昵称:<hk:text name="nickName"/>
			<hk:submit value="添加"/>
		</hk:form>
	</div>
	<c:forEach var="u" items="${opuserlist}"  varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/home.do?userId=${u.userId}">${u.user.nickName}</hk:a> 
			<hk:a href="/admin/union_delopuser.do?uid=${uid}&userId=${u.userId }">删除</hk:a>
		</div>
	</c:forEach>
	<div class="hang">
		<hk:a href="/admin/union.do">返回</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>