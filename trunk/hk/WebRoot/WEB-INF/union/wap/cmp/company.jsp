<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="row">
${company.name }<br/>
<c:if test="${not empty company.headPath}">
	<img src="${company.head240 }"/>
</c:if>
<c:if test="${not empty company.tel}">
	<div>${company.tel }</div>
</c:if>
<c:if test="${not empty company.traffic}">
	<div>${company.traffic }</div>
</c:if>
</div>
<div class="row">
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>