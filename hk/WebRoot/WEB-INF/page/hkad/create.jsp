<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.HkAd"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<hk:wap title="发布广告" rm="false">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">发布广告</div>
	<div class="hang">
		<c:set var="adform_action" scope="request">/op/gg_create.do</c:set>
		<jsp:include page="adform.jsp"></jsp:include>
	</div>
	<div class="hang even"><hk:a href="/op/gg_list.do">返回广告管理</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>