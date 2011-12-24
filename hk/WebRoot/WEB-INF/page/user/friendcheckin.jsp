<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">大家在哪</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="log" items="${list}" varStatus="idx"><c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/home.do?userId=${log.userId}">${log.user.nickName }</hk:a>
				在
				<hk:a href="/e/cmp.do?companyId=${log.companyId}">${log.company.name}</hk:a>
				<c:set var="createTime" value="${log.createTime}" scope="request"/>
				<%=JspDataUtil.outStyleTime(request) %>
			</div>
		</c:forEach>
	</c:if>
	<div class="hang">
	<hk:a href="/home.do?userId=${loginUser.userId}"><hk:data key="view2.return"/></hk:a>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>