<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${tag.name }频道 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${tag.name }频道</div>
	<div class="hang even" onkeydown="submitLaba(event)">
		<hk:form name="labafrm" onsubmit="return confirmCreate();" action="/laba/op/op_create.do">
			<hk:hide name="tagId" value="${tag.tagId}"/><hk:hide name="channel" value="1"/>
			<hk:textarea oid="status" name="content" clazz="ipt2" rows="2"/><br />
			<hk:submit value="提交" /> <span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
		</hk:form>
		<jsp:include page="../inc/labainputjs.jsp"></jsp:include>
	</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=taglabalist&tagId=${tagId }" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/laba/taglabalist.do?tagId=${tag.tagId}&fromhelp=${fromhelp }&helpUserId=${helpUserId }"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0}">没有数据显示</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>