<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">喇叭</c:set>
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
						<c:set var="nav_2_short_content" scope="request">
							<a href="<%=path%>/laba_userlaba.do?userId=${userId }" class="nav-a"><hk:data key="view.user.favlaba" arg0="${user.nickName}" /></a>
						</c:set>
						<jsp:include page="../inc/nav-2-short.jsp"></jsp:include>
					</div>
					<div class="mod">
						<c:set var="page_url" scope="request"><%=path%>/laba_list.do?userId=${userId}&w=${w}</c:set>
						<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
				<jsp:include page="../inc/laba_right_inc.jsp"></jsp:include>
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>