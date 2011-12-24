<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div class="rcon">
	<div class="inner">
		<%JspDataUtil.loadBoxFeed(request); %>
		<div class="mod">
			<div class="mod_content">
				<c:forEach var="fvo" items="${boxfeedvolist}">
					<div class="divrow bdtm">
					<a href="/user/${fvo.first.userId }"><img src="${fvo.first.user.head32Pic }"/></a>
					<span>${fvo.content }</span></div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>