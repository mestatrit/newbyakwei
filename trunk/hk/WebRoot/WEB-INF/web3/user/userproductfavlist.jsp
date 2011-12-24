<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view.user_fav_product" arg0="${user.nickName}" /></c:set>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${user.nickName}"/>
<meta name="description" content="${user.nickName}"/>
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
						<c:set var="nav_2_short_content" scope="request"><a href="<%=path %>/userproductfav.do?userId=${userId }" class="nav-a">${html_title }</a></c:set>
						<jsp:include page="../inc/nav-2-short.jsp"></jsp:include>
					</div>
					<div class="mod">
						<c:set var="page_url" scope="request"><%=path %>/userproductfav.do?userId=${userId}</c:set>
						<jsp:include page="../inc/productlist_inc.jsp"></jsp:include>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r">
				</div>
			</td>
		</tr>
	</table>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>