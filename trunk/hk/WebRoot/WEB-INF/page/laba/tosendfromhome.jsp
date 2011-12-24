<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="对${nickName}吹喇叭 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
		<div class="hang">正在交互<hk:a href="/home.do?userId=${userId}">@${nickName}</hk:a></div>
		<div class="iptlabadiv">
			<hk:form action="/laba/op/op_create.do">
				<div>
					<hk:hide name="from" value="tosendfromhome"/>
					<hk:hide name="userId" value="${userId}"/>
					<hk:hide name="lastUrl" value="/laba/op/op_tosendfromhome.do?userId=${userId }"/>
					<hk:textarea name="content" clazz="ipt2" rows="2" value="@${nickName} "/><br />
					<hk:submit value="吹喇叭" /> 
				</div>
			</hk:form>
		</div>
		<c:if test="${not empty from}">
		<div class="hang"><hk:a href="/laba/back.do?${queryString}">返回</hk:a></div>
		</c:if>
		<c:if test="${empty from}">
		<div class="hang"><hk:a href="/home.do?userId=${userId}">返回</hk:a></div>
		</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>