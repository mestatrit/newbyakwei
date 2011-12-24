<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<div class="lcon2">
	<div class="inner">
		<%JspDataUtil.loadEquipmentFeed(request); %>
		<div class="mod">
			<div class="mod_content">
				<c:forEach var="fvo" items="${equfeedvolist}">
					<div class="divrow bdtm"><a href="/user/${fvo.first.userId }"><img src="${fvo.first.user.head32Pic }"/></a>${fvo.content }</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>