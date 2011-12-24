<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_main_content" scope="request">
<div class="nav2">${cmpUnionBoard.title }</div>
<div class="nav2"><fmt:formatDate value="${cmpUnionBoard.createTime }" pattern="yy-MM-dd HH:mm"/></div>
<div class="row">${cmpUnionBoard.content }</div>
<div class="row">
	<hk:a href="/union/board_list.do?uid=${uid}">返回</hk:a><br/>
	<hk:a href="/union/union.do?uid=${uid}">返回首页</hk:a>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>