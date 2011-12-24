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
					<jsp:include page="labainput.jsp"></jsp:include>
					<div class="mod">
						<ul class="labanav">
						<c:if test="${w=='all'}"><c:set var="all_class" value="activebegin"></c:set></c:if>
						<c:if test="${w!='all'}"><c:set var="all_class" value="begin"></c:set></c:if>
						<c:if test="${w=='city'}"><c:set var="city_class" value="active"></c:set></c:if>
						<c:if test="${w=='range'}"><c:set var="range_class" value="active"></c:set></c:if>
						<c:if test="${w=='ip'}"><c:set var="ip_class" value="active"></c:set></c:if>
						<c:if test="${w==null}"><c:set var="follow_class" value="active"></c:set></c:if>
							<li class="${all_class }"><a href="<%=path %>/laba_list.do?w=all"><strong>全部</strong></a></li>
							<li class="${city_class }"><a href="<%=path %>/laba_list.do?w=city"><strong>本市</strong></a></li>
							<li class="${range_class }"><a href="<%=path %>/laba_list.do?w=range"><strong>周边</strong></a></li>
							<li class="${ip_class }"><a href="<%=path %>/laba_list.do?w=ip"><strong>旁边</strong></a></li>
							<li class="${follow_class }"><a href="<%=path %>/laba_list.do"><strong>关注</strong></a></li>
							<li><a href="<%=path %>/laba_fav.do?userId=${userId}"><strong>收藏</strong></a></li>
						</ul>
						<div class="clr"></div>
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