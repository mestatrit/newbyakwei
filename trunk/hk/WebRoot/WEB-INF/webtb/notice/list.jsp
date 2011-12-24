<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">个人通知
</c:set><c:set var="html_body_content" scope="request">
<div class="mod">
<div class="mod_title">个人通知</div>
<div class="mod_content">
<c:if test="${fn:length(list)==0}"><div class="nodata">本页没用通知</div></c:if>
<c:if test="${fn:length(list)>0}">
<ul class="rowlist">
<c:forEach var="notice" items="${list}"><c:set var="current_notice" value="${notice}" scope="request"/>
<li<c:if test="${!notice.readed}"> class="b"</c:if>><jsp:include page="notice_inc_${notice.ntype}.jsp"></jsp:include></li>
</c:forEach>
</ul>
</c:if>
<div>
<c:set var="page_url" scope="request">${ctx_path}/tb/op/notice</c:set>
<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
</div>
</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>