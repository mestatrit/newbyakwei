<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${coupon.name}</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="mod">
				<div class="mod_title">
					${coupon.name }
				</div>
				<div class="mod_content">
					${coupon.content }<br/>
					<c:if test="${not empty coupon.remark}">
						<strong>备注：</strong> ${coupon.remark }
					</c:if>
				</div>
			</div>
		</div>
	</div>
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
	<div class="clr"></div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>