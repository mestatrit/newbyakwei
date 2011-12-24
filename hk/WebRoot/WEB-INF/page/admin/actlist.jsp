<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="活动列表 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">活动列表
	<hk:form method="get" action="/e/admin/cmpact.do">
		名称:<br/>
		<hk:text name="name" value="${name}"/><br/>
		<hk:submit value="提交"/>
	</hk:form>
	</div>
	<c:forEach var="act" items="${list}"  varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a href="/e/admin/cmpact_act.do?actId=${act.actId}">${act.name }</hk:a> 
			<c:if test="${!act.adminPause}">
			<hk:a href="/e/admin/cmpact_setactadminpause.do?actId=${act.actId}">暂停</hk:a> 
			</c:if>
			<c:if test="${act.adminPause}">
			<hk:a href="/e/admin/cmpact_setactrun.do?actId=${act.actId}">运行</hk:a> 
			</c:if>
		</div>
	</c:forEach>
	<hk:simplepage2 href="/e/admin/cmpact.do?name=${enc_name}"/>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>