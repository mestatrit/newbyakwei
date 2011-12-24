<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="html_body_content" scope="request">
<div>
	<div class="lcon">
		<div class="inner">
			<div class="mod">
				<h1 class="bdtm"><a href="/venue/${companyId }/">${company.name }</a></h1>
				<div class="mod_content">
					<jsp:include page="../inc/cmptipvolist_inc.jsp"></jsp:include>
					<c:set var="url_rewrite" value="true" scope="request"/>
					<c:set var="page_url" scope="request">/venue/${companyId}/todo</c:set>
					<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="rcon">
		<div class="inner">
			<jsp:include page="../inc/rcon_usertip_inc.jsp"></jsp:include>
		</div>
	</div>
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>