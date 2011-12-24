<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="企业活动分类 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		企业活动分类
	</div>
	<div class="hang even">
		<hk:form action="/e/admin/cmpact_createkind.do">
			分类名称：<br/>
			<hk:text name="name"/><br/>
			<hk:submit value="创建"/>
		</hk:form>
	</div>
	<c:forEach var="u" items="${kindlist}"  varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			${u.name} 
			<hk:a href="/e/admin/cmpact_toupdatekind.do?kindId=${u.kindId}">修改</hk:a> 
			<hk:a href="/e/admin/cmpact_cfmdelkind.do?kindId=${u.kindId}">删除</hk:a> 
		</div>
	</c:forEach>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>