<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="联盟管理 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		联盟管理<br/>
		<hk:a href="/admin/union_tocreate.do">创建联盟</hk:a>
	</div>
	<c:forEach var="u" items="${list}"  varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<a href="http://mall.huoku.com/u/${u.uid }" target="_blank">${u.name}</a> 
			<a href="http://mall.huoku.com/u/${u.uid }/admin" target="_blank">后台</a> 
			<hk:a href="/admin/union_toedit.do?uid=${u.uid}">修改</hk:a> 
			<hk:a href="/admin/union_toselopuser.do?uid=${u.uid}">添加管理员</hk:a> 
		</div>
	</c:forEach>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>