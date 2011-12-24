<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="对${user.nickName }使用道具 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		对<hk:a href="/home.do?userId=${userId}">${user.nickName }</hk:a>使用道具
	</div>
	<c:if test="${fn:length(list)==0}">
		<div class="hang even">你还没有任何可以对${user.nickName }使用的道具</div>
	</c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="ueq" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
			<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<span class="split-r">${ueq.equipment.name }</span><hk:a href="/op/award_selequ.do?oid=${ueq.oid}&userId=${userId }&ch=1">使用</hk:a><br/>
				<span class="s">${ueq.equipment.intro }</span>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang">
	<hk:simplepage2 href="/op/award_selcmpequ.do?companyId=${companyId}"/>
	</div>
	<div class="hang">
	<hk:a href="/home.do?userId=${userId}"><hk:data key="view2.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>