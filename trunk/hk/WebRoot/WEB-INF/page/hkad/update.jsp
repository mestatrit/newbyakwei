<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.HkAd"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="修改广告" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">修改广告</div>
	<div class="hang">
		<c:set var="adform_action" scope="request">/op/gg_update.do</c:set>
		<jsp:include page="adform.jsp"></jsp:include>
	</div>
	<div class="hang even">
		<hk:a href="/op/gg_list.do">返回</hk:a><br/>
		<c:if test="${o.pause}">
			<hk:a href="/op/gg_dorun.do?oid=${oid}">运行</hk:a><br/>
		</c:if>
		<c:if test="${!o.pause}">
			<hk:a href="/op/gg_pause.do?oid=${oid}">暂停</hk:a><br/>
		</c:if>
		<hk:a href="/op/gg_del.do?oid=${oid}">删除</hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>