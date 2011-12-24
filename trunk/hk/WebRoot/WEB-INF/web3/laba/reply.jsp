<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">
	喇叭的回复 - 火酷网
</c:set>
<c:set var="meta_value" scope="request">
	<meta name="keywords" content="${user.nickName}" />
	<meta name="description" content="${user.nickName}" />
</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<h3 class="title">喇叭的回复</h3>
						<c:set var="page_url" scope="request"><%=path%>/laba_reply.do?labaId=${labaId}</c:set>
						<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>