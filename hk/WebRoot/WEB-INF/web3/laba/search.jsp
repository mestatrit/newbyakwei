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
					<div class="search_3">
					<hk:form oid="s_frm" method="get" action="/laba_s.do">
						<input name="sw" type="text" class="text f_l" value="${sw }"/>
						<a class="btn_s_3" onclick="subsearch()">搜索</a>
						<div class="clr"></div>
					</hk:form>
				</div>
					<div class="mod">
						<h2 class="line"><hk:data key="view.searchlaba.result" arg0="${sw}"/></h2><br/>
						<c:set var="page_url" scope="request"><%=path%>/laba_s.do?sw=${enc_sw}</c:set>
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