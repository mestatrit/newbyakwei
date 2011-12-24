<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.Information"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${info.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:set var="var_stop" value="<%=Information.USESTATUS_STOP %>"/>
	<c:set var="var_run" value="<%=Information.USESTATUS_RUN %>"/>
	<div class="hang even">${info.name}</div>
	<c:if test="${not empty info.intro}">
	<div class="hang odd">${info.intro }</div>
	</c:if>
	
	<div class="hang">
		<c:if test="${loginUser.userId==info.userId &&!info.run}">
			<hk:form method="get" action="/info/op/info_chgstatus.do">
			<hk:hide name="infoId" value="${infoId}"/>
			<hk:hide name="stat" value="${var_run}"/>
			<hk:hide name="r_url" value="/info/info_view.do?infoId=${infoId}"/>
			<hk:submit value="info.view.run_submit" res="true"/>
			</hk:form>
		</c:if>
		<c:if test="${info.run}">
				<hk:form action="/info/op/info_createlaba.do">
					<hk:hide name="infoId" value="${infoId}"/>
					<c:if test="${loginUser.userId!=info.userId}">
						<hk:text name="content" clazz="ipt" maxlength="140" value="@${user.nickName} "/>
					</c:if>
					<c:if test="${loginUser.userId==info.userId}">
						<hk:text name="content" clazz="ipt" maxlength="140"/>
					</c:if>
					<br/>
					<hk:submit value="info.view.submit" res="true"/> <span><hk:data key="inf.view.smsportview" arg0="${smsNumber}"/> </span>
				</hk:form>
		</c:if>
	</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=info&infoId=${info.infoId }" scope="request"/>
		<jsp:include page="../inc/labavo2.jsp"></jsp:include>
		<hk:simplepage href="/info/info_view.do?infoId=${infoId}"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0}"><hk:data key="nodataview"/></c:if>
	<div class="hang"><hk:a href="/info/info.do?userId=${user.userId}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>