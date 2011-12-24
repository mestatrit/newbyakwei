<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.cmpunion.createcmp.tocreate1.infol"/></c:set>
<c:set var="html_main_content" scope="request">
	<div class="nav2">创建商户第一步</div>
	<div class="row">
		<hk:form method="get" action="/union/createcmp_tocreate1.do">
			<hk:hide name="s" value="1"/>
			<hk:hide name="uid" value="${uid}"/>
			<hk:data key="view.cmpunion.createcmp.tocreate1.infol"/>：<br/>
			<hk:text name="name" value="${name}"/><br/>
			<hk:submit name="n" value="搜索"/>
		</hk:form><br/>
	</div>
	<c:if test="${pcity==null && s==1}">
		<div class="row"><hk:data key="view.cmpunion.createcmp.tocreate1.nosearch"/></div>
	</c:if>
	<c:if test="${fn:length(cmpzoneinfoList)>0}">
		<div class="row">
			<c:forEach var="z" items="${cmpzoneinfoList}">
				<hk:a href="/union/createcmp_tocreate2.do?uid=${uid }&pcityId=${z.pcityId }">${z.name}</hk:a> 
			</c:forEach>
		</div>
	</c:if>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>